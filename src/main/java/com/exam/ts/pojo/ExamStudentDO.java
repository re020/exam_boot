package com.exam.ts.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.exam.ex.pojo.StudentDO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * <p>
 * 考试-学生对应表
 * </p>
 *
 * @author 杨德石
 * @since 2019-05-24
 */
@TableName("te_exam_student")
@Data
public class ExamStudentDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "st_id", type = IdType.INPUT)
    private String stId;

    /**
     * 考试id
     */
    private String stExam;

    /**
     * 学生id
     */
    private String stStu;

    /**
     * 学生
     */
    @TableField(exist = false)
    private StudentDO student;

    /**
     * 创建时间
     */
    private String stCreateTime;

    @Override
    public String toString() {
        return "ExamStudentDO{" +
                "stId=" + stId +
                ", stExam=" + stExam +
                ", stStu=" + stStu +
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
        ExamStudentDO that = (ExamStudentDO) o;
        return stId.equals(that.stId) ||
                (stExam.equals(that.stExam) &&
                        stStu.equals(that.stStu));
    }

    @Override
    public int hashCode() {
        return Objects.hash(stId, stExam, stStu);
    }
}
