package com.exam.core.constant;

import lombok.Getter;

/**
 * 返回状态码
 *
 * @author 杨德石
 */
@Getter
public enum ResultEnum {

    /**
     * 返回值状态码
     */
    SUCCESS(200, "操作成功"),
    ERROR(400, "操作失败"),
    LOGOUT(50008, "被挤下线"),
    NO_QUESTION(10004, "没有题目"),
    LOADED(10005, "试卷处理中，请勿重新提交"),
    COMMITED(10006, "试卷已提交，请勿重复提交"),
    FINISHED(10007, "试卷已经统计完成,请勿重复提交"),
    NO_FINISH(10009, "存在试卷未批阅完成，请重新批阅"),
    ENDING(10010,"考试已结束"),
    NO_USE(10008,"试卷未启用");

    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
