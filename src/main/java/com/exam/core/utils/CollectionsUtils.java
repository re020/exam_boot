package com.exam.core.utils;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * 自己封装的集合工具类
 * @Author: 杨德石
 * @Date: 2019/5/7 0007 下午 6:00
 * @Version 1.0
 */
public class CollectionsUtils {

    private CollectionsUtils() {}

    public static void duplication(List list) {
        List<Object> newList = Lists.newArrayList();
        for (Object o : list) {
            if(!newList.contains(o)) {
                newList.add(o);
            }
        }

        list.clear();
        list.addAll(newList);
    }

}
