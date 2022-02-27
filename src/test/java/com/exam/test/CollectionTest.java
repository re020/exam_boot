package com.exam.test;


import com.exam.ex.pojo.QuestionDO;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class CollectionTest {

    @Test
    public void testGoogle() {
        Map<String, Object> map = Maps.newHashMap();
        List<Object> list = Lists.newArrayList();
    }

    @Test
    public void testEmpty() {
        List<QuestionDO> list = Lists.newArrayList();
        System.out.println(list.isEmpty());
    }

}
