package com.exam.core.utils;

import com.alibaba.druid.sql.visitor.functions.Char;
import com.google.common.collect.Maps;

import java.math.BigDecimal;
import java.util.HashMap;

public class MathUtils {

    private static HashMap<Character, Integer> map = Maps.newHashMap();

    static {
        map.put('A', 8);
        map.put('B', 4);
        map.put('C', 2);
        map.put('D', 1);
    }

    /**
    ** 计算多选题的得分，
     * 1. 全部选对得满分;
     * 2. 少选并且没有选错误选项得一半分;
     * 3. 多选错选(只要选择了错误选项)不得分
    * */
    public static BigDecimal getGrade(String studentAnswer, String trueAnswer) {
        int stuNum = StringtranInteger(studentAnswer);
        int treNum = StringtranInteger(trueAnswer);

        if (stuNum == 0) {
            return new BigDecimal(0);
        }
        int val = treNum ^ stuNum;
        if (val == 0) {
            return new BigDecimal(1);
        } else {
            if ((val | treNum) == treNum) {
                return new BigDecimal(0.5);
            } else {
                return new BigDecimal(0);

            }
        }
    }

    public static int StringtranInteger(String str) {
        char[] strBytes = str.toCharArray();
        int stuNum = 0;
        for (Character b : strBytes) {
            stuNum += map.get(b);
        }
        return stuNum;
    }
}
