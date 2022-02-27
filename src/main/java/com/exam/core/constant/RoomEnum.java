package com.exam.core.constant;

import lombok.Getter;

/**
 * @Author: 杨德石
 * @Date: 2019/5/24 0024 下午 1:19
 * @Version 1.0
 */
@Getter
public enum RoomEnum {

    /**
     * 教室状态枚举
     */
    FREE(1, "空闲"),
    APPLY(2, "占用")
    ;
    private Integer code;
    private String msg;

    RoomEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
