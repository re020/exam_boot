package com.exam.test;


import com.exam.core.constant.PatternConstant;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringTest {

    private final Pattern pattern = PatternConstant.THREE_UNDER_LINE_PATTERN;

    @Test
    public void testSubString() {
        String s = "abcde";
        System.out.println(s.substring(2));
    }

    @Test
    public void testEquals() {
        String str = "1";
        Integer i = 1;
        System.out.println(str.equals(i));
    }

    @Test
    public void testRegex() {
        String str = "package com.day3;\n" +
                "import java.util.*;\n" +
                "public class test12 {\n" +
                "    public static void main(String[] args) {\n" +
                "        double x = 0,y = 0;\n" +
                "        System.out.print(\"输入当月利润（万） ：\");\n" +
                "        Scanner s = new Scanner(System.in);\n" +
                "        x = s.nextInt();\n" +
                "        if(x > 0 && x <= 10) {\n" +
                "            y = x * 0.1;\n" +
                "        } else if(x > 10 && x <= 20) {\n" +
                "            y = 10 * 0.1 + (x - 10) * 0.075;\n" +
                "        } else if(x > 20 && x <= 40) {\n" +
                "            y = 10 * 0.1 + 10 * 0.075 + (x - 20) * 0.05;\n" +
                "        } else if(x > 40 && x <= 60) {\n" +
                "            y = 10 * 0.1 + 10 * 0.075 + 20 * 0.05 + (x - 40) * 0.03;\n" +
                "        } else if(x > 60 && x <= 100) {\n" +
                "            y = 20 * 0.175 + 20 * 0.05 + 20 * 0.03 + (x - 60) * 0.015;\n" +
                "        } else if(x > 100) {\n" +
                "            y = 20 * 0.175 + 40 * 0.08 + 40 * 0.015 + (x - 100) * 0.01;\n" +
                "        }\n" +
                "        System.out.println(\"应该提取的奖金是 \" + y + \"万\");\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "test12";
        Matcher matcher = pattern.matcher(str);
        String s = matcher.replaceAll("");
        System.out.println(s);
    }

    @Test
    public void testChar() {
        System.out.println("1".equals('1'));
    }

}
