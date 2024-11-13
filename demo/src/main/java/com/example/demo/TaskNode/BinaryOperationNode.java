package com.example.demo.TaskNode;

public class BinaryOperationNode extends ExprNode {
    private final ExprNode left;
    private final ExprNode right;
    private final String operator;

    public BinaryOperationNode(ExprNode left, ExprNode right, String operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public double evaluate(double x) {
        return switch (operator) {
            case "+" -> left.evaluate(x) + right.evaluate(x);
            case "-" -> left.evaluate(x) - right.evaluate(x);
            case "*" -> left.evaluate(x) * right.evaluate(x);
            case "/" -> left.evaluate(x) / right.evaluate(x);
            case "^" -> Math.pow(left.evaluate(x), right.evaluate(x));
            default -> throw new IllegalArgumentException("Unknown operator: " + operator);
        };
    }
}