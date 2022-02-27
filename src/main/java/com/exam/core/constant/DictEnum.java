package com.exam.core.constant;

import lombok.Getter;

/**
 * 数据字典枚举
 * @Author: 杨德石
 * @Date: 2019/5/10 0010 上午 11:21
 * @Version 1.0
 */
@Getter
public enum DictEnum {
    /**
     * 数据字典枚举
     */
    COLLEGE("college", "学院"),
    MAJOR("major", "专业"),
    JOB("job", "职务"),
    TITLE("title", "职称"),
    SUBJECT("subject", "科目")
    ;


    private String code;
    private String msg;

    DictEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }}
