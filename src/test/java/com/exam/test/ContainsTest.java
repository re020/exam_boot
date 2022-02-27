package com.exam.test;

import com.exam.ex.pojo.QuestionDO;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

/**
 * @Author: 杨德石
 * @Date: 2019/5/2 0002 下午 9:17
 * @Version 1.0
 */
public class ContainsTest {

    @Test
    public void testContains() throws Exception {
        QuestionDO questionDO = new QuestionDO();
        questionDO.setQuestionId("1");
        List list = Lists.newArrayList();
        list.add(questionDO);
        QuestionDO questionDO1 = new QuestionDO();
        questionDO1.setQuestionId("2");
        Object o = questionDO1;
        System.out.println(list.contains(o));
    }

}
