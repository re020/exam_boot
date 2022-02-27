package com.exam.core.constant;

import lombok.Getter;

/**
 * 判断登录身份
 * @author lth
 * @version 1.0.0
 * @date
 */
@Getter
public enum UserType {
    /**
     * 学生
     */
    STUDENT("student","学生"),
    TEACHER("teacher","老师");

    private String code;
    private String msg;

    UserType(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
