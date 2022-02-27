package com.exam.core.constant;

import lombok.Getter;
import org.apache.poi.ss.formula.functions.T;
import org.apache.xmlbeans.SchemaType;

/**
 * @version 1.0
 * @author: 杨德石
 * @date: 2019/3/30 0030 下午 2:11
 */
@Getter
public enum TypeEnum {

    /**
     * 题型状态值
     */
    ONE_CHOICE(1, "单项选择题"),
    MANY_CHOICE(2, "多项选择题"),
    JUDGEMENT(3, "判断题"),
    COMPLETION(4, "填空题"),
    PROGRAMMING(5, "编程题"),
    OTHER(6, "其他题"),

    ;

    /**
     * 用户类型
     */

    private Integer code;
    private String msg;

    TypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public static TypeEnum match(Integer type) {
        for (TypeEnum item : TypeEnum.values()) {
            if (item.code.equals(type)) {
                return item;
            }
        }
        return null;
    }
}
