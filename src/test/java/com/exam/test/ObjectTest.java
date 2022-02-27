package com.exam.test;

import com.exam.ex.pojo.QuestionDO;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

/**
 * @Author: 杨德石
 * @Date: 2019/5/3 0003 下午 5:01
 * @Version 1.0
 */
public class ObjectTest {

    @Test
    public void testInstanceof() {

        List list = Lists.newArrayList();

        QuestionDO questionDO1 = new QuestionDO();
        questionDO1.setQuestionId("123");
        questionDO1.setQuestionTitle("42131231");
        list.add(questionDO1);
        Object o1 = questionDO1;

        QuestionDO questionDO2 = new QuestionDO();
        questionDO2.setQuestionId("123");
        questionDO2.setQuestionTitle("534243");
        list.add(questionDO2);
        Object o2 = questionDO2;
        System.out.println(list.contains(o2));

    }

}
