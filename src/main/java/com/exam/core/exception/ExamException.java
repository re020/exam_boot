package com.exam.core.exception;

import com.exam.core.constant.ResultEnum;
import lombok.Data;

/**
 * 统一异常处理信息
 * @version 1.0
 * @author: 杨德石
 * @date: 2019/3/28 0028 上午 10:42
 */
@Data
public class ExamException extends Exception {

    private Integer code;

    public ExamException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public ExamException(Integer code, String defaultMessage) {
        super(defaultMessage);
        this.code = code;
    }

    public ExamException(String message) {
        super(message);
        this.code = ResultEnum.ERROR.getCode();
    }

}
