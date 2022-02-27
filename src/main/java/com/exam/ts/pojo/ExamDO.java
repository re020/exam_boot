package com.exam.ts.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.exam.ex.pojo.PaperDO;
import com.exam.ex.pojo.TeacherDO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 考试表
 * </p>
 *
 * @author 杨德石
 * @since 2019-05-24
 */
@TableName("te_exam")
@Data
public class ExamDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "exam_id", type = IdType.INPUT)
    private String examId;

    /**
     * 考试日期
     */
    private String examDate;

    /**
     * 考场id
     */
    private String examRoom;

    @TableField(exist = false)
    private RoomDO room;

    /**
     * 考试时间
     */
    private Integer examTime;

    /**
     * 考试所用试卷id
     */
    private String examPaper;

    @TableField(exist = false)
    private PaperDO paper;

    /**
     * 创建人id
     */
    private String examCreateBy;

    @TableField(exist = false)
    private TeacherDO teacher;

    /**
     * 创建时间
     */
    private String examCreateTime;

    /**
     * 考试类型，0平常测试，1普通考试，2补考
     */
    private Integer examType;

    /**
     * 0未开始，1已开始，2已结束
     */
    private Integer examState;

    /**
     * 备注
     */
    private String examComment;

    /**
     * 乐观锁
     */
    @Version
    private Integer examVersion;

    /**
     * 1正常0删除
     */
    @TableLogic
    private Integer examDelete;

    /**
     * 监考教师
     */
    @TableField(exist = false)
    private List<ExamTeacherDO> teacherList;

    /**
     * 考试学生
     */
    @TableField(exist = false)
    private List<ExamStudentDO> studentList;

    @Override
    public String toString() {
        return "ExamDO{" +
                "examId='" + examId + '\'' +
                ", examDate='" + examDate + '\'' +
                ", examRoom='" + examRoom + '\'' +
                ", examTime=" + examTime +
                ", examPaper='" + examPaper + '\'' +
                ", paper=" + paper +
                ", examCreateBy='" + examCreateBy + '\'' +
                ", teacher=" + teacher +
                ", examType=" + examType +
                ", examState=" + examState +
                ", examComment='" + examComment + '\'' +
                ", examVersion=" + examVersion +
                ", examDelete=" + examDelete +
                ", teacherList=" + teacherList +
                ", studentList=" + studentList +
                '}';
    }
}
