package com.exam.core.constant;

import lombok.Getter;

/**
 * @Author: 杨德石
 * @Date: 2019/5/10 0010 上午 11:03
 * @Version 1.0
 */
@Getter
public enum SelectEnum {
    /**
     * 查询权限枚举
     */
    SELECT_SELF(1, "查询自己"),
    SELECT_COLLEGE(2, "查询学院"),
    SELECT_ALL(3, "查询所有")
    ;
    private Integer code;
    private String msg;

    SelectEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }}
