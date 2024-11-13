package com.example.demo.TaskToken;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {
    private final String expression;
    private final List<Token> tokens;

    public Tokenizer(String expression) {
        this.expression = expression.replaceAll("\\s+", "");
        this.tokens = new ArrayList<>();
        tokenize();
    }

    private void tokenize() {
        if (!expression.matches("[\\d+a-zA-Z+\\-*/^()\\s]+")) {
            throw new IllegalArgumentException("Ошибка: выражение содержит недопустимые символы.");
        }

        String tokenPattern = "\\d+\\.?\\d*|[a-zA-Z]+|[+\\-*/^()]";
        Pattern pattern = Pattern.compile(tokenPattern);
        Matcher matcher = pattern.matcher(expression);
        Token previousToken = null;

        while (matcher.find()) {
            String tokenValue = matcher.group();
            Token currentToken;

            if (tokenValue.matches("\\d+\\.?\\d*")) {
                // Если токен - число
                currentToken = new Token(TokenType.NUMBER, tokenValue);
                if (previousToken != null && (previousToken.type() == TokenType.VARIABLE || previousToken.type() == TokenType.FUNCTION)) {
                    throw new IllegalArgumentException("Ошибка: отсутствует оператор между числом и переменной/функцией.");
                }
            } else if (tokenValue.matches("[a-zA-Z]+")) {
                // Проверяем, что это либо допустимая функция, либо одиночная переменная
                if (isFunction(tokenValue)) {
                    currentToken = new Token(TokenType.FUNCTION, tokenValue);
                } else if (isValidSingleVariable(tokenValue)) {
                    currentToken = new Token(TokenType.VARIABLE, tokenValue);
                } else {
                    throw new IllegalArgumentException("Ошибка: недопустимая последовательность символов: " + tokenValue);
                }
                // Проверка, чтобы перед функцией или переменной не стояло число
                if (previousToken != null && previousToken.type() == TokenType.NUMBER) {
                    throw new IllegalArgumentException("Ошибка: отсутствует оператор между числом и функцией/переменной.");
                }
            } else {
                // Оператор или скобка
                currentToken = new Token(TokenType.OPERATOR, tokenValue);
            }

            tokens.add(currentToken);
            previousToken = currentToken;
        }
    }


    private boolean isFunction(String token) {
        return token.equals("sin") || token.equals("cos") || token.equals("log")
                || token.equals("exp") || token.equals("tan") || token.equals("sqrt")
                || token.equals("abs") || token.equals("asin") || token.equals("acos")
                || token.equals("atan") || token.equals("ceil")
                || token.equals("floor") || token.equals("round") || token.equals("my");
    }

    private boolean isValidSingleVariable(String token) {
        return token.equals("x");
    }

    public List<Token> getTokens() {
        return tokens;
    }
}
