package com.haoshi;

import com.haoshi.androidtest.Calculator;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Created by yugu on 2017/2/22.
 */
public class CalculatorTest {

    private Calculator calculator;

    @Before
    public void setUp() throws Exception {
        calculator = new Calculator();
    }

    @Test
    public void sum() throws Exception {
        assertEquals(6d, calculator.sum(1d, 5d), 0);
    }

    @Test
    public void subtraction() throws Exception {
        assertEquals(1d, calculator.subtraction(5d, 4d), 0);
    }

    @Test
    public void multiply() throws Exception {
        assertEquals(10d, calculator.multiply(2d, 5d), 0);
    }

    @Test
    public void divide() throws Exception {
        assertEquals(4d, calculator.divide(20d, 5d), 0);
    }
}