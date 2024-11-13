package com.example.demo.TaskNode.Function;

import com.example.demo.TaskNode.ExprNode;

public class LogFunctionNode extends FunctionNode {

    public LogFunctionNode(ExprNode argument) {
        super(argument);
    }

    @Override
    public double evaluate(double xValue) {
        return Math.log(argument.evaluate(xValue));
    }
}