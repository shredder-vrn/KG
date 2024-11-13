package com.example.demo.TaskParser;

import com.example.demo.TaskNode.*;
import com.example.demo.TaskNode.Function.*;
import com.example.demo.TaskToken.Token;
import com.example.demo.TaskToken.TokenType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Parser {
    private final List<Token> tokens;
    private int currentTokenIndex = 0;

    private final Map<String, Function<ExprNode, FunctionNode>> functionMap = new HashMap<>();

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        initializeFunctionMap();
    }

    private void initializeFunctionMap() {
        functionMap.put("sin", SinFunctionNode::new);
        functionMap.put("cos", CosFunctionNode::new);
        functionMap.put("sqrt", SqrtFunctionNode::new);
        functionMap.put("log", LogFunctionNode::new);
        functionMap.put("abs", AbsFunctionNode::new);
        functionMap.put("exp", ExpFunctionNode::new);
        functionMap.put("tan", TanFunctionNode::new);
        functionMap.put("my", MyFunctionNode::new);
    }

    private Token currentToken() {
        return tokens.get(currentTokenIndex);
    }

    private void nextToken() {
        currentTokenIndex++;
    }

    private ExprNode parsePrimary() {
        Token current = currentToken();

        // Обработка отрицательных чисел
        if (current.value().equals("-")) {
            nextToken();
            ExprNode primary = parsePrimary();
            return new UnaryMinusNode(primary);
        }

        // Обработка чисел
        if (current.type() == TokenType.NUMBER) {
            nextToken();
            return new NumberNode(Double.parseDouble(current.value()));
        }

        // Обработка переменных
        if (current.type() == TokenType.VARIABLE) {
            nextToken();
            return new VariableNode(current.value());
        }

        // Обработка функций
        if (current.type() == TokenType.FUNCTION) {
            String functionName = current.value();
            nextToken();

            if (currentToken().value().equals("(")) {
                nextToken();
                ExprNode argument = parseExpression(); // Парсинг аргумента функции
                if (currentToken().value().equals(")")) {
                    nextToken();

                    if (functionMap.containsKey(functionName)) {
                        return functionMap.get(functionName).apply(argument);
                    } else {
                        throw new RuntimeException("Unknown function: " + functionName);
                    }
                } else {
                    throw new RuntimeException("Expected closing parenthesis");
                }
            } else {
                throw new RuntimeException("Expected opening parenthesis");
            }
        }

        if (current.value().equals("(")) {
            nextToken();
            ExprNode expression = parseExpression(); // Парсинг вложенного выражения
            if (currentToken().value().equals(")")) {
                nextToken();
                return expression;
            } else {
                throw new RuntimeException("Expected closing parenthesis");
            }
        }

        throw new RuntimeException("Unexpected token: " + current);
    }

    public ExprNode parseExpression() {
        return parseAdditionAndSubtraction();
    }

    private ExprNode parseAdditionAndSubtraction() {
        ExprNode left = parseMultiplicationAndDivision();

        while (currentTokenIndex < tokens.size() &&
                (currentToken().value().equals("+") || currentToken().value().equals("-"))) {
            String operator = currentToken().value();
            nextToken();
            ExprNode right = parseMultiplicationAndDivision();
            left = new BinaryOperationNode(left, right, operator);
        }

        return left;
    }

    private ExprNode parseMultiplicationAndDivision() {
        ExprNode left = parsePower();

        while (currentTokenIndex < tokens.size() &&
                (currentToken().value().equals("*") || currentToken().value().equals("/"))) {
            String operator = currentToken().value();
            nextToken();
            ExprNode right = parsePower();
            left = new BinaryOperationNode(left, right, operator);
        }

        return left;
    }

    private ExprNode parsePower() {
        ExprNode left = parsePrimary();

        while (currentTokenIndex < tokens.size() && currentToken().value().equals("^")) {
            String operator = currentToken().value();
            nextToken();
            ExprNode right = parsePower();
            left = new BinaryOperationNode(left, right, operator);
        }

        return left;
    }
}

