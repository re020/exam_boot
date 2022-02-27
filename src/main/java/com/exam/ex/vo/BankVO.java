package com.exam.ex.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: 杨德石
 * @Date: 2019/5/9 0009 下午 6:30
 * @Version 1.0
 */
@Data
public class BankVO implements Serializable {

    /**
     * 题库id
     */
    private String bankId;

    /**
     * 题库名
     */
    private String bankName;

    /**
     * 试卷数
     */
    private Integer paperNum;

    /**
     * 题目数
     */
    private Integer questionNum;

}
