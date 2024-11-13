package com.example.demo.TaskNode;

/**
 * Абстрактный класс для узла выражения
 */
public abstract class ExprNode {

    /**
     * Абстрактный метод для вычисления значения выражения при заданном x
     * @param x
     * @return
     */
    public abstract double evaluate(double x);
}

/**
 * Делаем всё абстрактным
 * чтобы наделать наследников
 * чтобы предотвратить создание экземпляров ExprNode напрямую
 */