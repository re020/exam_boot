package com.exam.ts.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 学生试卷配置-总得分表
 * </p>
 *
 * @author 杨德石
 * @since 2019-05-24
 */
@TableName("te_student_paper_config_score")
public class StudentPaperConfigScoreDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "sc_id", type = IdType.INPUT)
    private String scId;

    /**
     * 试卷id
     */
    private String scPaper;

    /**
     * 配置id
     */
    private String scConfig;

    /**
     * 得分
     */
    private BigDecimal scScore;

    /**
     * 所属学生id
     */
    private String scStudent;


    public String getScId() {
        return scId;
    }

    public void setScId(String scId) {
        this.scId = scId;
    }

    public String getScPaper() {
        return scPaper;
    }

    public void setScPaper(String scPaper) {
        this.scPaper = scPaper;
    }

    public String getScConfig() {
        return scConfig;
    }

    public void setScConfig(String scConfig) {
        this.scConfig = scConfig;
    }

    public BigDecimal getScScore() {
        return scScore;
    }

    public void setScScore(BigDecimal scScore) {
        this.scScore = scScore;
    }

    public String getScStudent() {
        return scStudent;
    }

    public void setScStudent(String scStudent) {
        this.scStudent = scStudent;
    }

    @Override
    public String toString() {
        return "StudentPaperConfigScoreDO{" +
        "scId=" + scId +
        ", scPaper=" + scPaper +
        ", scConfig=" + scConfig +
        ", scScore=" + scScore +
        ", scStudent=" + scStudent +
        "}";
    }
}
