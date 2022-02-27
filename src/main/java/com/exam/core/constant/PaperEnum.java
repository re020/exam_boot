package com.exam.core.constant;

import lombok.Getter;

/**
 * 是否启用枚举
 * @version 1.0
 * @author: 杨德石
 * @date: 2019/3/28 0028 上午 10:53
 */
@Getter
public enum PaperEnum {

    /**
     * 试卷是否被启用状态值
     */
    USE(1, "已启用"),
    NOT_USE(0, "未启用"),
    COMMIT(2,"已提交"),
    LOADING(3,"统计中"),
    FINISH(4,"统计完成"),
    ;
    private Integer code;
    private String msg;

    PaperEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
