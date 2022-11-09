package org.calculator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.StatusAssertions;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CalculatorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testMainPage() throws Exception {
        this.mockMvc.perform(get("/calculator?result=123*8"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    //false-test на переход на другую страницу
    @Test
    public void testRedirect_False() throws Exception {
        this.mockMvc.perform(get("/calculator"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }

    //тест на дефолтный вывод сообщения, при переходе на сайт
    @Test
    public void testMessage_Greeting() throws Exception {
        this.mockMvc.perform(get("/calculator"))
                .andDo(print())
                .andExpect(content().string(containsString("Enter your expression!")));
    }


    /******************************************_Errors_messages_test_**********************************************/
    //проверка на пустой ввод(ни одного пробела)
    @Test
    public void testMessage_empty() throws Exception {
        this.mockMvc.perform(get("/calculator?result="))
                .andDo(print())
                .andExpect(xpath("/html/body/form/div/span")
                        .string("Expression cannot be empty!"));
    }
    //проверка на пустой ввод(один пробела)
    @Test
    public void testMessage_oneSpace() throws Exception {
        this.mockMvc.perform(get("/calculator?result= "))
                .andDo(print())
                .andExpect(xpath("/html/body/form/div/span")
                        .string("Expression cannot be empty!"));
    }
    //проверка на пустой ввод(множество пробелов)
    @Test
    public void testMessage_manySpace() throws Exception {
        this.mockMvc.perform(get("/calculator?result=   "))
                .andDo(print())
                .andExpect(xpath("/html/body/form/div/span")
                        .string("Expression cannot be empty!"));
    }


    @Test
    public void wrongRegexTest_symbols() throws Exception{
        this.mockMvc.perform(get("/calculator?result='aaa'"))
                .andDo(print())
                .andExpect(xpath("/html/body/form/div/span")
                        .string("Your expression is wrong!"));
    }

    @Test
    public void wrongRegexTest_number() throws Exception{
        this.mockMvc.perform(get("/calculator?result=123"))
                .andDo(print())
                .andExpect(xpath("/html/body/form/div/span")
                        .string("Your expression is wrong!"));
    }

    @Test
    public void wrongRegexTest_numberAction() throws Exception{
        this.mockMvc.perform(get("/calculator?result=123+"))
                .andDo(print())
                .andExpect(xpath("/html/body/form/div/span")
                        .string("Your expression is wrong!"));
    }

    //корректное возвращение view
    @Test
    public void trueGetViewTest() throws Exception{
        this.mockMvc.perform(get("/calculator"))
                .andDo(print())
                .andExpect(view().name("calculator"));
    }


    /******************************************_Errors_expression_test_**********************************************/
    //проверка того, что на выходе получим целочисленное число
    @Test
    public void testIntegerValueResult_MultiplyDoubleInteger() throws Exception {
        this.mockMvc.perform(get("/calculator?result=5.4*5"))
                .andDo(print())
                .andExpect(content().string(containsString("27")));
    }
    //на получении ответа не отразится множество пробелов
    @Test
    public void testIntegerValueResult_MultiplyDoubleInteger_ManySpaces() throws Exception {
        this.mockMvc.perform(get("/calculator?result=5.4 *   5"))
                .andDo(print())
                .andExpect(content().string(containsString("27")));
    }

    //проверка того, что на выходе получим double
    @Test
    public void testDoubleValueResult_SubtractionDoubleDouble() throws Exception {
        this.mockMvc.perform(get("/calculator?result=32.5-2.2"))
                .andDo(print())
                .andExpect(content().string(containsString("30.3")));
    }

    //верный результат деления двух чисел
    @Test
    public void testDoubleValueResult_DivideDoubleInteger() throws Exception {
        this.mockMvc.perform(get("/calculator?result=5.5/2"))
                .andDo(print())
                .andExpect(content().string(containsString("2.75")));
    }

    //верный рез-т сложения двух чисел
    @Test
    public void testDoubleValueResult_addIntegerInteger() throws Exception {
        this.mockMvc.perform(get("/calculator?result=5 + 2.3"))
                .andDo(print())
                .andExpect(content().string(containsString("7.3")));
    }

    //верный рез-т умножения на ноль
    @Test
    public void testZeroMultiply() throws Exception {
        this.mockMvc.perform(get("/calculator?result=0*0.0"))
                .andDo(print())
                .andExpect(content().string(containsString("0")));
    }

    //проверка деления на ноль
    @Test
    public void testZeroDivide() throws Exception {
        this.mockMvc.perform(get("/calculator?result=4 / 0.0"))
                .andDo(print())
                .andExpect(content().string(containsString("NaN")));
    }





}
