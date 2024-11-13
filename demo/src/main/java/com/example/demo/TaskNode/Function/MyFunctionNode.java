package com.example.demo.TaskNode.Function;

import com.example.demo.TaskNode.ExprNode;

public class MyFunctionNode extends FunctionNode {

    public MyFunctionNode(ExprNode argument) {
        super(argument);
    }

    @Override
    public double evaluate(double xValue) {
       // return (Math.sin(xValue)*(Math.pow(Math.exp(1), Math.cos(xValue)) - 2*Math.cos(4*xValue) - Math.pow(Math.sin(xValue/12), 5)));
        return Math.sin(xValue)+Math.sin(xValue*2) + Math.sin(xValue*3)+Math.sin(xValue*4)+Math.sin(xValue*5)+0.5*Math.tan(xValue);
    }
}