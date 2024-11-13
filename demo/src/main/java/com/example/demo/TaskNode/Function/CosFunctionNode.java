package com.example.demo.TaskNode.Function;

import com.example.demo.TaskNode.ExprNode;

public class CosFunctionNode extends FunctionNode {

    public CosFunctionNode(ExprNode argument) {
        super(argument);
    }

    @Override
    public double evaluate(double xValue) {
        return Math.cos(argument.evaluate(xValue));
    }
}