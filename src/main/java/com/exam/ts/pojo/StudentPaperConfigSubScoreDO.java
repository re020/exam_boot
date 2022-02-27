package com.exam.ts.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 学生-试卷-每个题型-主观题得分表（一题一分）
 * </p>
 *
 * @author 杨德石
 * @since 2019-05-24
 */
@TableName("te_student_paper_config_sub_score")
public class StudentPaperConfigSubScoreDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "qs_id", type = IdType.INPUT)
    private String qsId;

    /**
     * 题目id
     */
    private String qsQuestion;

    /**
     * 所属配置
     */
    private String qsConfig;

    /**
     * 所属学生
     */
    private String qsStudent;

    /**
     * 得分
     */
    private BigDecimal qsScore;


    public String getQsId() {
        return qsId;
    }

    public void setQsId(String qsId) {
        this.qsId = qsId;
    }

    public String getQsQuestion() {
        return qsQuestion;
    }

    public void setQsQuestion(String qsQuestion) {
        this.qsQuestion = qsQuestion;
    }

    public String getQsConfig() {
        return qsConfig;
    }

    public void setQsConfig(String qsConfig) {
        this.qsConfig = qsConfig;
    }

    public String getQsStudent() {
        return qsStudent;
    }

    public void setQsStudent(String qsStudent) {
        this.qsStudent = qsStudent;
    }

    public BigDecimal getQsScore() {
        return qsScore;
    }

    public void setQsScore(BigDecimal qsScore) {
        this.qsScore = qsScore;
    }

    @Override
    public String toString() {
        return "StudentPaperConfigSubScoreDO{" +
        "qsId=" + qsId +
        ", qsQuestion=" + qsQuestion +
        ", qsConfig=" + qsConfig +
        ", qsStudent=" + qsStudent +
        ", qsScore=" + qsScore +
        "}";
    }
}
