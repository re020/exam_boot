package com.exam.ts.pojo.DTO;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class StudentPaperDTO {
    private String paperId;
    private String paperTitle;
    private String paperScore;
    private String paperStudent;
    private String stuName;
    private Integer paperFlag;
    private BigDecimal paperDifficulty;
}
