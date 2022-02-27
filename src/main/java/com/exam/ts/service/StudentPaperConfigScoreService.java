package com.exam.ts.service;

import com.exam.ts.pojo.StudentPaperConfigScoreDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;

/**
 * <p>
 * 学生试卷配置-总得分表 服务类
 * </p>
 *
 * @author 杨德石
 * @since 2019-05-24
 */
public interface StudentPaperConfigScoreService extends IService<StudentPaperConfigScoreDO> {

    /**
     * 查询试卷中最终的成绩
     * @param answerPaper answerStudent
     * @return
     */
    BigDecimal getGradesByPaperAndStu(String answerPaper, String answerStudent);
}
