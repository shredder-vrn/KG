package com.example.demo.TaskToken;

import com.example.demo.TaskNode.ExprNode;
import javafx.scene.paint.Color;

public class GraphTask {
    private final ExprNode expression;
    private final Color color;
    private final double strokeWidth;
    private final String name;
    private boolean visible;

    public GraphTask(ExprNode expression, Color color, double strokeWidth, String name) {
        this.expression = expression;
        this.color = color;
        this.strokeWidth = strokeWidth;
        this.name = name;
        this.visible = true; // по умолчанию график видим
    }

    public ExprNode getExpression() {
        return expression;
    }

    public Color getColor() {
        return color;
    }

    public double getStrokeWidth() {
        return strokeWidth;
    }

    public String getName() {
        return name;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
