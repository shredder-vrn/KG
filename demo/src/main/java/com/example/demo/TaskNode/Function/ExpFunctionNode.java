package com.example.demo.TaskNode.Function;

import com.example.demo.TaskNode.ExprNode;

public class ExpFunctionNode extends FunctionNode {

    public ExpFunctionNode(ExprNode argument) {
        super(argument);
    }

    @Override
    public double evaluate(double xValue) {
        return Math.exp(argument.evaluate(xValue));
    }
}