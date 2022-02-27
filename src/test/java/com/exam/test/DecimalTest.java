package com.exam.test;

import org.junit.Test;

import java.math.BigDecimal;

/**
 * @Author: 杨德石
 * @Date: 2019/5/4 0004 下午 4:18
 * @Version 1.0
 */
public class DecimalTest {

    @Test
    public void testDivide() {
        System.out.println(new BigDecimal(56.0).divide(new BigDecimal(3.0), 1, BigDecimal.ROUND_HALF_DOWN));
    }

}
