package org.calculator.service;

import org.mariuszgromada.math.mxparser.Expression;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class CalculatorMath {
    /**
     * @param expression -- строковое выражение, введенное пользователем
     * @return переменная типа Double -- вычисленное значение
     */
    public static String parseStringExpression(String expression) {
        //Блок кода стал не нужным, благодаря классу EXPRESSION

        /*//действие в выражении
        String action = findAction(expression);

        StringBuilder stringBuilderExpression = new StringBuilder();
        for (var ch: expression.toCharArray()) {
            //если символ -- цифра, действие или '.'(для double)
            if (Character.isDigit(ch) || ch == action.charAt(0) || ch == '.' || ch == '-') {
                stringBuilderExpression.append(ch);
            }
        }
        System.out.println("Полученный StringBuilder: " + stringBuilderExpression.toString());*/

        return solve(expression);

    }

    private static String findAction(String expression) {
        Pattern pattern = Pattern.compile("[-+*/]");
        Matcher matcher = pattern.matcher(expression);
        String action = "";
        if (matcher.find()) {
            action = expression.substring(matcher.start(), matcher.end());
        }
        System.out.println("action in Math.solve: " + action);

        return action;
    }

    private static String solve(String expression) {
        Expression expr = new Expression(expression);
        System.out.println("EXPRESSION: " + expr.calculate());

        double value = expr.calculate();
        int valueInt = (int)value;

        //то есть, проверка double-числа на *.0
        if (value == (double)valueInt) {
            return String.valueOf(valueInt);
        } else {
            return String.valueOf(value);
        }
    }
}
