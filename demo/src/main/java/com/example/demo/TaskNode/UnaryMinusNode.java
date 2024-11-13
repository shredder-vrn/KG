package com.example.demo.TaskNode;

public class UnaryMinusNode extends ExprNode {
    private final ExprNode operand;

    public UnaryMinusNode(ExprNode operand) {
        this.operand = operand;
    }

    @Override
    public double evaluate(double x) {
        return -operand.evaluate(x);
    }
}
