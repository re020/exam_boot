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
 * 填空题表
 * </p>
 *
 * @author 杨德石
 * @since 2019-03-28
 */
@Data
@TableName("ex_completion")
public class CompletionDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "comp_id", type = IdType.INPUT)
    private String compId;

    /**
     * 内容
     */
    private String compTitle;

    /**
     * 分值
     */
    private BigDecimal compScore;

    /**
     * 难度系数
     */
    private Integer compDifficulty;

    /**
     * 所属题库
     */
    private String compBank;

    /**
     * 知识点id
     */
    private String compKnow;

    /**
     * 解析
     */
    private String compResolve;

    /**
     * 乐观锁
     */
    @Version
    private Integer compVersion;

    /**
     * 0删除1正常
     */
    @TableLogic
    private Integer compDelete;

    /**
     * 答案
     *
     * @return
     */
    @TableField(exist = false)
    private List<CompletionAnswerDO> answerList;

    /**
     * 选择的答案
     *
     * @return
     */
    @TableField(exist = false)
    private StudentAnswerDO AnswerContent;

    @Override
    public String toString() {
        return "CompletionDO{" +
                "compId=" + compId +
                ", compTitle=" + compTitle +
                ", compScore=" + compScore +
                ", compDifficulty=" + compDifficulty +
                ", compBank=" + compBank +
                ", compVersion=" + compVersion +
                ", compDelete=" + compDelete +
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
        CompletionDO that = (CompletionDO) o;
        return compId.equals(that.compId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(compId);
    }
}
