package com.exam.ts.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.exam.ex.pojo.TypeDO;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 学生试卷配置-题目表
 * </p>
 *
 * @author 杨德石
 * @since 2019-05-24
 */
@TableName("te_student_paper_config_question")
@Data
public class StudentPaperConfigQuestionDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * 配置id
     */
    private String questionConfig;

    /**
     * 题目id
     */
    private String questionId;


    /**
     * 是否批改，0未批改，1已批改
     */
    private Integer questionState;

    @TableField(exist = false)
    private StudentAnswerDO answerContent;


    @Override
    public String toString() {
        return "StudentPaperConfigQuestionDO{" +
        "id=" + id +
        ", questionConfig=" + questionConfig +
        ", questionId=" + questionId +
        ", questionState=" + questionState +
        "}";
    }
}
