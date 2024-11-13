package com.example.demo;


import com.example.demo.TaskNode.ExprNode;

public class DomainCheckable {

    // Проверка допустимости значений на всём интервале
    public static boolean hasValidDomain(ExprNode expression, double minX, double maxX, double step) {
        boolean foundValidPoint = false;

        for (double x = minX; x <= maxX; x += step) {
            try {
                double result = expression.evaluate(x);

                // Если хотя бы одно значение не NaN и не бесконечность, считаем, что функция валидна
                if (!Double.isNaN(result) && !Double.isInfinite(result)) {
                    foundValidPoint = true;
                    break;  // Прерываем проверку, если нашли валидное значение
                }
            } catch (Exception e) {
                System.out.println(":)");
            }
        }

        return foundValidPoint;  // Вернём true, если есть хотя бы одно допустимое значение
    }
}
