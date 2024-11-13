package com.example.demo.TaskNode.Function;

import com.example.demo.TaskNode.ExprNode;

public class TanFunctionNode extends FunctionNode {

    public TanFunctionNode(ExprNode argument) {
        super(argument);
    }

    @Override
    public double evaluate(double xValue) {
        return Math.tan(argument.evaluate(xValue));
    }
}