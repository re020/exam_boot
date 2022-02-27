package com.exam.ts.pojo.DTO;

import lombok.Data;

/**
 * 答题Dto
 * @author lth
 * @version 1.0.0
 * @date
 */
@Data
public class AnswerDTO {

    /**
     * ID
     */
    private String id;

    /**
     * 题目ID
     */
    private String questionId;

    /**
     * 答案内容
     */
    private String answerContent;

    /**
     * 题目序号
     */
    private String number;

    /**
     * 学生id
     */
    private String answerStudent;

    /**
     * 哪个配置
     */
    private String answerConf;

    /**
     * 试卷id
     */
    private String answerPaper;

}
