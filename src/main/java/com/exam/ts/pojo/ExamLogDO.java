package com.exam.ts.pojo;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 考试日志表
 * </p>
 *
 * @author 杨德石
 * @since 2019-05-24
 */
@TableName("te_exam_log")
public class ExamLogDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "log_id", type = IdType.INPUT)
    private String logId;

    /**
     * 考试时间
     */
    private String logDate;

    /**
     * 考试时长分钟
     */
    private Integer logTime;

    /**
     * 考场，直接考场名，不用id
     */
    private String logRoom;

    /**
     * 考生人数
     */
    private Integer logStudentNum;

    /**
     * 试卷名
     */
    private String logPaperTitle;

    /**
     * 及格率
     */
    private BigDecimal logPass;

    /**
     * 创建人
     */
    private String logCreateBy;

    /**
     * 乐观锁
     */
    private Integer logVersion;

    /**
     * 0删除1正常
     */
    private Integer logDelete;


    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getLogDate() {
        return logDate;
    }

    public void setLogDate(String logDate) {
        this.logDate = logDate;
    }

    public Integer getLogTime() {
        return logTime;
    }

    public void setLogTime(Integer logTime) {
        this.logTime = logTime;
    }

    public String getLogRoom() {
        return logRoom;
    }

    public void setLogRoom(String logRoom) {
        this.logRoom = logRoom;
    }

    public Integer getLogStudentNum() {
        return logStudentNum;
    }

    public void setLogStudentNum(Integer logStudentNum) {
        this.logStudentNum = logStudentNum;
    }

    public String getLogPaperTitle() {
        return logPaperTitle;
    }

    public void setLogPaperTitle(String logPaperTitle) {
        this.logPaperTitle = logPaperTitle;
    }

    public BigDecimal getLogPass() {
        return logPass;
    }

    public void setLogPass(BigDecimal logPass) {
        this.logPass = logPass;
    }

    public String getLogCreateBy() {
        return logCreateBy;
    }

    public void setLogCreateBy(String logCreateBy) {
        this.logCreateBy = logCreateBy;
    }

    public Integer getLogVersion() {
        return logVersion;
    }

    public void setLogVersion(Integer logVersion) {
        this.logVersion = logVersion;
    }

    public Integer getLogDelete() {
        return logDelete;
    }

    public void setLogDelete(Integer logDelete) {
        this.logDelete = logDelete;
    }

    @Override
    public String toString() {
        return "ExamLogDO{" +
        "logId=" + logId +
        ", logDate=" + logDate +
        ", logTime=" + logTime +
        ", logRoom=" + logRoom +
        ", logStudentNum=" + logStudentNum +
        ", logPaperTitle=" + logPaperTitle +
        ", logPass=" + logPass +
        ", logCreateBy=" + logCreateBy +
        ", logVersion=" + logVersion +
        ", logDelete=" + logDelete +
        "}";
    }
}
