package com.example.demo.TaskNode.Function;

import com.example.demo.TaskNode.ExprNode;

public class SqrtFunctionNode extends FunctionNode {

    public SqrtFunctionNode(ExprNode argument) {
        super(argument);
    }

    @Override
    public double evaluate(double xValue) {
        return Math.sqrt(argument.evaluate(xValue));
    }
}