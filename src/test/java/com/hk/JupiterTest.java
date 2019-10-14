package com.hk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.hk.junit.Calculator;
import org.junit.jupiter.api.Test;

public class JupiterTest {

    private final Calculator calculator = new Calculator();

    //JUnit5编写单元测试
    @Test
    void addTest() {

        assertEquals(2, calculator.add(1, 1));
    }
}
