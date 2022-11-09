package org.calculator.service;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CalculatorService {
    public boolean allSymbolsAreDigits(String expression) {
        for (var ch: expression.toCharArray()) {
            if (!Character.isDigit(ch)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return true -- если строка не пустая,
     *         false -- если строка пустая или в ней любое кол-во пробелов
     */
    public boolean emptyString(String expression) {
        Pattern patternSpace = Pattern.compile("^\\s++$");
        Pattern patternEmpty = Pattern.compile("^$");
        Matcher matcher = patternSpace.matcher(expression);
        Matcher matcherEmpty = patternEmpty.matcher(expression);

        if(matcher.find() || matcherEmpty.find()) {
            System.out.println("Строка пустая(Empty String)");
            return true;
        } else {
            System.out.println("Строка не пустая(Not empty String)");
            return false;
        }
    }

    /**
     * Проверка на то, что введенное пользователем выражение соотвествует "выражению"
     */
    public boolean correctRegex(String expression) {
        Pattern pattern = Pattern.compile("^\\s*([-+]?)(\\d+)(\\.\\d+)?(?:\\s*([-+*/])\\s*((?:\\s[-+])?\\d+(\\.\\d+)?)\\s*)+$");
        Matcher matcher = pattern.matcher(expression);
        if(matcher.find()) {
            System.out.println("matcher: " + expression.substring(matcher.start(), matcher.end()));
            return true;
        } else {
            System.out.println("Несоответствие regex");
            return false;
        }
    }
}
