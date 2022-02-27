package com.exam.ts.pojo.DTO;

import lombok.Data;

/**
 * 传递批改题目的信息
 * @author lth
 */

@Data
public class QuestionDTO {

    /**
     * 试卷Id
     */
    private String paperId;

    /**
     * 学生id
     */
    private String stuId;

    /**
     * 配置id
     */
    private String configId;

    /**
     * 题目id
     */
    private String questionId;
    /**
     * 分值
     */
    private Integer grade;


}
