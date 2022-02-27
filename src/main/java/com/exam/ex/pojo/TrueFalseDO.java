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
import java.util.Objects;

/**
 * <p>
 * 判断题表
 * </p>
 *
 * @author 杨德石
 * @since 2019-04-12
 */
@TableName("ex_true_false")
@Data
public class TrueFalseDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "tf_id", type = IdType.INPUT)
    private String tfId;

    /**
     * 题目内容
     */
    private String tfTitle;

    /**
     * 分值
     */
    private BigDecimal tfScore;

    /**
     * 难度系数
     */
    private Integer tfDifficulty;

    /**
     * 所属题库
     */
    private String tfBank;

    /**
     * 解析
     */
    private String tfResolve;

    /**
     * 是否正确，1正确0错误
     */
    private Integer tfTrue;

    /**
     * 知识点id
     */
    private String tfKnow;

    /**
     * 乐观锁
     */
    @Version
    private Integer tfVersion;

    /**
     * 1正常0删除
     */
    @TableLogic
    private Integer tfDelete;
    /**
     * 学生作答
     */
    @TableField(exist = false)
    private StudentAnswerDO answerContent;

    @Override
    public String toString() {
        return "TrueFalseDO{" +
                "tfId=" + tfId +
                ", tfTitle=" + tfTitle +
                ", tfScore=" + tfScore +
                ", tfDifficulty=" + tfDifficulty +
                ", tfBank=" + tfBank +
                ", tfResolve=" + tfResolve +
                ", tfTrue=" + tfTrue +
                ", tfKnow=" + tfKnow +
                ", tfVersion=" + tfVersion +
                ", tfDelete=" + tfDelete +
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
        TrueFalseDO that = (TrueFalseDO) o;
        return tfId.equals(that.tfId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tfId);
    }
}
