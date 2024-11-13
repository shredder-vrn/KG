package com.example.demo.TaskToken;

public record Token(TokenType type, String value) {

    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                ", value='" + value + '\'' +
                '}';
    }
}

