package com.exam.ts.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.exam.ex.pojo.TeacherDO;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 考试-监考教师表
 * </p>
 *
 * @author 杨德石
 * @since 2019-05-24
 */
@TableName("te_exam_teacher")
@Data
public class ExamTeacherDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "tt_id", type = IdType.INPUT)
    private String ttId;

    /**
     * 考试id
     */
    private String ttExam;

    /**
     * 监考教师id
     */
    private String ttTeacher;

    /**
     * 监考教师
     */
    @TableField(exist = false)
    private TeacherDO teacher;

    @Override
    public String toString() {
        return "ExamTeacherDO{" +
        "ttId=" + ttId +
        ", ttExam=" + ttExam +
        ", ttTeacher=" + ttTeacher +
        "}";
    }
}
