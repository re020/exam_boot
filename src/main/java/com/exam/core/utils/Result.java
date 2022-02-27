package com.exam.core.utils;

import com.exam.core.constant.ResultEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 返回对象
 *
 * @author 杨德石
 */
@Data
public class Result implements Serializable {

    /**
     * 响应业务状态，默认200，错误400
     */
    private Integer code;

    /**
     * 响应消息
     */
    private String msg;

    /**
     * 响应数据
     */
    private Object data;

    public static Result build(Integer code, String msg, Object data) {
        return new Result(code, msg, data);
    }

    public static Result ok(Object data) {
        return new Result(data);
    }

    public static Result ok(String msg, Object data) {
        return Result.build(ResultEnum.SUCCESS.getCode(), msg, data);
    }

    public static Result ok(String msg) {
        return Result.build(ResultEnum.SUCCESS.getCode(), msg);
    }

    public static Result ok() {
        return new Result(null);
    }

    public Result() {

    }

    public static Result build(Integer code, String msg) {
        return new Result(code, msg, null);
    }

    public Result(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(Object data) {
        this.code = ResultEnum.SUCCESS.getCode();
        this.msg = "OK";
        this.data = data;
    }

}
