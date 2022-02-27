package com.exam.ts.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 学生做题答案表
 * </p>
 *
 * @author 杨德石
 * @since 2019-05-24
 */
@Data
@TableName("te_student_answer")
public class StudentAnswerDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "answer_id", type = IdType.INPUT)
    private String answerId;

    /**
     * 答案内容
     */
    private String answerContent;

    /**
     * 学生id
     */
    private String answerStudent;

    /**
     * 哪个配置
     */
    private String answerConf;

    @TableField(exist = false)
    private Integer type;


    @TableField(exist = false)
    private StudentPaperConfigDO config;

    /**
     * 试卷id
     */
    private String answerPaper;

    /**
     * 问题id
     */
    private String answerQuestion;

    public Integer getConfigType() {
        if (config == null) return 0;
        else {
            String configType = config.getConfigType();
            return Integer.valueOf(configType);
        }
    }


}
