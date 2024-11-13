package com.example.demo.TaskNode;

public class VariableNode extends ExprNode {
    private final String variableName;

    public VariableNode(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public double evaluate(double xValue) {
        if (variableName.equals("x")) {
            return xValue;
        } else {
            throw new RuntimeException("Unknown variable: " + variableName);
        }
    }
}