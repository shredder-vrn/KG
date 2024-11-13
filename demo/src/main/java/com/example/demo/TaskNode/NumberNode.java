package com.example.demo.TaskNode;

/**
 * Класс для представления числового узла выражения
 */
public class NumberNode extends ExprNode {
    private final double value; // Хранит числовое значение узла

    /**
     * Конструктор для инициализации значения
     * @param value
     */
    public NumberNode(double value) {
        this.value = value;
    }

    /**
     * Возвращает значение узла
     * @param x
     * @return
     */

    @Override
    public double evaluate(double x) {
        return value;
    }
}