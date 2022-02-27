package com.exam.ex;

import com.exam.core.constant.ExamEnum;
import org.junit.Test;

/**
 * 测试枚举
 * @Author: 杨德石
 * @Date: 2019/5/24 0024 下午 1:31
 * @Version 1.0
 */
public class EnumTest {

    @Test
    public void testExamEnum() {
        System.out.println(ExamEnum.NOT_STARTED.getCode());
        System.out.println(ExamEnum.TEST.getCode());
    }

}
