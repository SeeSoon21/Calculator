package org.calculator.controller;

import org.calculator.service.CalculatorMath;
import org.calculator.service.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/calculator")
public class CalculatorController {
    @Autowired
    private CalculatorService calculatorService;

    @GetMapping
    public String getCalculator(@RequestParam(required = false) String result,
                                Model model) {
        System.out.println("handledResult = " + result);
        StringBuilder error;
        /*if (calculatorService.allSymbolsAreDigits(handledResult)) {
            error = new StringBuilder("All symbols must be numeric!");
            model.addAttribute("error", error.toString());
            return "calculator";
        }*/
        if (result == null) {
            System.out.println("null result = " + result);
            model.addAttribute("result", "Enter your expression!");
            return "calculator";
        }
        else if (calculatorService.emptyString(result)) {
            error = new StringBuilder("Expression cannot be empty!");
            model.addAttribute("error", error.toString());
            return "calculator";
        }
        else if (!calculatorService.correctRegex(result)) {
            error = new StringBuilder("Your expression is wrong!");
            model.addAttribute("error", error.toString());
            return "calculator";
        }

        else {
            System.out.println("ноль ошибок: " );
            String completeResult = CalculatorMath.parseStringExpression(result);
            System.out.println("complete Result: " + completeResult);
            model.addAttribute("result", completeResult);
            System.out.println("result in get: " + result);


            //редирект, чтобы сбить приколоченные к странице параметры-ошибки
            return "calculator";
        }


    }



    /*@PostMapping
    public String postCalculator(@RequestParam("result") String result,
                                 Model model) {
        System.out.println("result: " + result);
        model.addAttribute("result", result);
        model.addAttribute("pog", "shvebra");

        return "redirect:/calculator";
    }*/
}
