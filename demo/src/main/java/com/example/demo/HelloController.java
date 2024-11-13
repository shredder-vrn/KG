package com.example.demo;

import com.example.demo.TaskNode.ExprNode;
import com.example.demo.TaskParser.Parser;
import com.example.demo.TaskToken.GraphTask;
import com.example.demo.TaskToken.Token;
import com.example.demo.TaskToken.Tokenizer;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class HelloController {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Canvas canvas;
    @FXML
    private TextField inputField;
    @FXML
    private Button submitButton;
    @FXML
    private Button zoomInButton;
    @FXML
    private Button zoomOutButton;
    @FXML
    private VBox functionList;

    private List<GraphTask> graphTasks = new ArrayList<>();
    private GraphicsContext gc;

    private double scaleX = 50;
    private double scaleY = 50;
    private double offsetX;
    private double offsetY;
    private final double MIN_SCALE = 5;   // минимальный масштаб
    private final double MAX_SCALE = 200; // максимальный масштаб


    @FXML
    private void initialize() {
        gc = canvas.getGraphicsContext2D();
        offsetX = canvas.getWidth() / 2;
        offsetY = canvas.getHeight() / 2;

        anchorPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            canvas.setWidth(newVal.doubleValue());
            redraw();
        });
        anchorPane.heightProperty().addListener((obs, oldVal, newVal) -> {
            canvas.setHeight(newVal.doubleValue());
            redraw();
        });

        submitButton.setOnAction(event -> addFunction());
        zoomInButton.setOnAction(event -> zoom(1.2));
        zoomOutButton.setOnAction(event -> zoom(0.8));

        canvas.setOnMousePressed(this::onMousePressed);
        canvas.setOnMouseDragged(this::onMouseDragged);

        redraw();
    }

    private void addFunction() {
        String input = inputField.getText();
        if (!input.isEmpty()) {
            try {
                Tokenizer tokenizer = new Tokenizer(input);
                List<Token> tokens = tokenizer.getTokens();
                Parser parser = new Parser(tokens);
                ExprNode expression = parser.parseExpression();

                GraphTask task = new GraphTask(expression, Color.BLUE, 2, input);
                graphTasks.add(task);
                inputField.clear();

                HBox functionBox = new HBox(5);
                CheckBox functionCheckBox = new CheckBox(input);
                functionCheckBox.setSelected(true);
                functionCheckBox.setOnAction(e -> {
                    task.setVisible(functionCheckBox.isSelected());
                    redraw();
                });

                Button deleteButton = new Button("Удалить");
                deleteButton.setOnAction(e -> {
                    graphTasks.remove(task);
                    functionList.getChildren().remove(functionBox);
                    redraw();
                });

                functionBox.getChildren().addAll(functionCheckBox, deleteButton);
                functionList.getChildren().add(functionBox);

                redraw();
            } catch (Exception e) {
                showAlert("Ошибка", "Неверный формат функции: " + input);
            }
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void zoom(double factor) {
        scaleX = Math.max(MIN_SCALE, Math.min(MAX_SCALE, scaleX * factor));
        scaleY = Math.max(MIN_SCALE, Math.min(MAX_SCALE, scaleY * factor));
        redraw();
    }



    private void onMousePressed(MouseEvent event) {
        canvas.setUserData(new Point2D(event.getX(), event.getY()));
    }

    private void onMouseDragged(MouseEvent event) {
        Point2D start = (Point2D) canvas.getUserData();
        offsetX += event.getX() - start.getX();
        offsetY += event.getY() - start.getY();
        canvas.setUserData(new Point2D(event.getX(), event.getY()));
        redraw();
    }

    private void redraw() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        drawAxesAndGrid();
        drawGraphs();
    }

    private void drawAxesAndGrid() {
        gc.setLineWidth(1);
        gc.setStroke(Color.LIGHTGRAY);

        double canvasWidth = canvas.getWidth();
        double canvasHeight = canvas.getHeight();

        // Изменяем шаг шкалы в зависимости от текущего масштаба
        double stepX = scaleX > 50 ? scaleX : 10 * Math.ceil(scaleX / 10);
        double stepY = scaleY > 50 ? scaleY : 10 * Math.ceil(scaleY / 10);

        for (double x = offsetX % stepX; x <= canvasWidth; x += stepX) {
            gc.strokeLine(x, 0, x, canvasHeight);
            drawScaleLabel(x, offsetY, (x - offsetX) / scaleX);
        }
        for (double y = offsetY % stepY; y <= canvasHeight; y += stepY) {
            gc.strokeLine(0, y, canvasWidth, y);
            drawScaleLabel(offsetX, y, (offsetY - y) / scaleY);
        }

        gc.setStroke(Color.RED);
        gc.setLineWidth(2);
        gc.strokeLine(offsetX, 0, offsetX, canvasHeight);
        gc.strokeLine(0, offsetY, canvasWidth, offsetY);
    }

    private void drawScaleLabel(double x, double y, double value) {
        gc.setFill(Color.BLACK);
        gc.fillText(String.format("%.1f", value), x + 2, y - 2);
    }

    private void drawGraphs() {
        gc.setLineWidth(2);

        for (GraphTask task : graphTasks) {
            if (task.isVisible()) {
                gc.setStroke(task.getColor());
                drawGraph(task);
            }
        }
    }
    private void drawGraph(GraphTask task) {
        Point2D prevPoint = null;

        // Вычисляем видимые границы по оси X
        double minVisibleX = -(offsetX / scaleX);
        double maxVisibleX = (canvas.getWidth() - offsetX) / scaleX;

        for (double x = minVisibleX; x <= maxVisibleX; x += 0.01) {
            try {
                double y = task.getExpression().evaluate(x);

                // Проверка на бесконечные или неопределенные значения
                if (Double.isInfinite(y) || Double.isNaN(y)) {
                    if (prevPoint != null) {
                        // Если есть предыдущая точка, рисуем линию к нижней границе
                        gc.strokeLine(prevPoint.getX(), prevPoint.getY(), offsetX + x * scaleX, canvas.getHeight());
                    }
                    prevPoint = null; // Сбрасываем предыдущую точку
                    continue; // Переход к следующему значению x
                }

                double canvasX = offsetX + x * scaleX;
                double canvasY = offsetY - y * scaleY;

                // Проверка границ canvasY
                if (canvasY < 0 || canvasY > canvas.getHeight()) {
                    if (prevPoint != null) {
                        gc.strokeLine(prevPoint.getX(), prevPoint.getY(), canvasX, canvas.getHeight());
                    }
                    prevPoint = null; // Сбрасываем предыдущую точку
                    continue; // Переход к следующему значению x
                }


                prevPoint = new Point2D(canvasX, canvasY); // Обновляем предыдущую точку
            } catch (Exception e) {
                if (prevPoint != null) {
                    // Рисуем линию при ошибке
                    gc.strokeLine(prevPoint.getX(), prevPoint.getY(), offsetX + x * scaleX, canvas.getHeight());
                }
                prevPoint = null; // Сбрасываем предыдущую точку
            }
        }
    }


}