package com.exam.ts.pojo.DTO;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author lth
 * 试卷提交DTO
 */

@Data
public class CommitDTO {

    /**
     * 学生试卷id
     */
    private String paperId;

    private String stuId;

    private BigDecimal grade;

}
