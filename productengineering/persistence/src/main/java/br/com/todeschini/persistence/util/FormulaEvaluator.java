package br.com.todeschini.persistence.util;

import java.util.List;
import java.util.Stack;

public class FormulaEvaluator {

    public double evaluateFormula(List<String> formula, double x, double y, double z) {
        Stack<Double> operandStack = new Stack<>();
        Stack<String> operatorStack = new Stack<>();

        for (String token : formula) {
            if (isVariable(token)) {
                switch (token) {
                    case "x" -> operandStack.push(x);
                    case "y" -> operandStack.push(y);
                    case "z" -> operandStack.push(z);
                    default -> throw new IllegalStateException("Variável desconhecida: " + token);
                }
            } else if (isNumeric(token)) {
                operandStack.push(Double.parseDouble(token));
            } else if ("+".equals(token) || "-".equals(token) || "*".equals(token) || "/".equals(token)) {
                while (!operatorStack.isEmpty() && hasHigherPrecedence(operatorStack.peek(), token)) {
                    applyOperator(operandStack, operatorStack.pop());
                }
                operatorStack.push(token);
            } else if ("(".equals(token)) {
                operatorStack.push(token);
            } else if (")".equals(token)) {
                while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")) {
                    applyOperator(operandStack, operatorStack.pop());
                }
                if (!operatorStack.isEmpty() && operatorStack.peek().equals("(")) {
                    operatorStack.pop(); // Remove o "(" correspondente
                } else {
                    throw new IllegalStateException("Parênteses desbalanceados");
                }
            }
        }

        while (!operatorStack.isEmpty()) {
            applyOperator(operandStack, operatorStack.pop());
        }

        if (operandStack.size() != 1 || !operatorStack.isEmpty()) {
            throw new IllegalStateException("Fórmula inválida");
        }

        return operandStack.pop();
    }

    private static boolean isVariable(String str) {
        return "xyz".contains(str);
    }

    private static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    private static boolean hasHigherPrecedence(String op1, String op2) {
        return (("*/".contains(op1) && "+-".contains(op2)) || ("/".equals(op1) && "*".equals(op2)));
    }

    private static void applyOperator(Stack<Double> operandStack, String operator) {
        if (operandStack.size() < 2) {
            throw new IllegalStateException("Fórmula inválida");
        }

        double operand2 = operandStack.pop();
        double operand1 = operandStack.pop();

        switch (operator) {
            case "+" -> operandStack.push(operand1 + operand2);
            case "-" -> operandStack.push(operand1 - operand2);
            case "*" -> operandStack.push(operand1 * operand2);
            case "/" -> operandStack.push(operand1 / operand2);

            default -> throw new IllegalStateException("Operador desconhecido: " + operator);
        }
    }
}
