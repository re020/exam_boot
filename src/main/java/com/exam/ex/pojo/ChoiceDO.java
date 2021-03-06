package com.exam.ex.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.exam.ts.pojo.StudentAnswerDO;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 选择题表
 * </p>
 *
 * @author 杨德石
 * @since 2019-03-28
 */
@TableName("ex_choice")
@Data
public class ChoiceDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 题目id
     */
    @TableId(value = "choice_id", type = IdType.INPUT)
    private String choiceId;

    /**
     * 题干
     */
    private String choiceTitle;

    /**
     * 1单选2多选3判断
     */
    private Integer choiceType;

    /**
     * 分值
     */
    private BigDecimal choiceScore;

    /**
     * 难度系数
     */
    private Integer choiceDifficulty;

    /**
     * 所属题库
     */
    private String choiceBank;

    /**
     * 所属知识点
     */
    private String choiceKnow;

    /**
     * 解析
     */
    private String choiceResolve;

    /**
     * 乐观锁
     */
    @Version
    private Integer choiceVersion;

    /**
     * 0删除1正常
     */
    @TableLogic
    private Integer choiceDelete;

    /**
     * 选项
     *
     * @return
     */
    @TableField(exist = false)
    private List<ChoiceAnswerDO> choiceAnswer;

    /**
     * 正确答案
     *
     * @return
     */
    @TableField(exist = false)
    private String choiceTrue;

    /**
     * 选择的答案
     *
     * @return
     */
    @TableField(exist = false)
    private StudentAnswerDO answerContent;


    @Override
    public String toString() {
        return "ChoiceDO{" +
                "choiceId=" + choiceId +
                ", choiceTitle=" + choiceTitle +
                ", choiceType=" + choiceType +
                ", choiceScore=" + choiceScore +
                ", choiceDifficulty=" + choiceDifficulty +
                ", choiceBank=" + choiceBank +
                ", choiceVersion=" + choiceVersion +
                ", choiceDelete=" + choiceDelete +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChoiceDO choiceDO = (ChoiceDO) o;
        return choiceId.equals(choiceDO.choiceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(choiceId);
    }
}
