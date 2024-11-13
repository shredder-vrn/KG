package com.example.demo.TaskNode.Function;

import com.example.demo.TaskNode.ExprNode;

public class SinFunctionNode extends FunctionNode {

    public SinFunctionNode(ExprNode argument) {
        super(argument);
    }

    @Override
    public double evaluate(double xValue) {
        return Math.sin(argument.evaluate(xValue));
    }
}