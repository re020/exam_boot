package com.exam.core.constant;

import lombok.Getter;

/**
 * 考试相关枚举
 * @Author: 杨德石
 * @Date: 2019/5/24 0024 下午 1:23
 * @Version 1.0
 */
@Getter
public enum ExamEnum {

    /**
     * 考试类型枚举
     */
    TEST(0, "测试"),
    EXAM(1, "考试"),
    REPAIR(2, "补考"),

    /**
     * 考试状态枚举
     */
    NOT_STARTED(0, "未开始"),
    STARTED(1, "一开始"),
    ENDED(2, "已结束")
    ;
    private Integer code;
    private String msg;

    ExamEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
