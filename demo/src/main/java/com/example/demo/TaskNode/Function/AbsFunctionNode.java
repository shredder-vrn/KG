package com.example.demo.TaskNode.Function;

import com.example.demo.TaskNode.ExprNode;

public class AbsFunctionNode extends FunctionNode {

    public AbsFunctionNode(ExprNode argument) {
        super(argument);
    }

    @Override
    public double evaluate(double xValue) {
        return Math.abs(argument.evaluate(xValue));
    }
}