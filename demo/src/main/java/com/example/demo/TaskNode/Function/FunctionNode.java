package com.example.demo.TaskNode.Function;

import com.example.demo.TaskNode.ExprNode;

public abstract class FunctionNode extends ExprNode {
    protected final ExprNode argument;

    public FunctionNode(ExprNode argument) {
        this.argument = argument;
    }

    @Override
    public abstract double evaluate(double xValue);
}