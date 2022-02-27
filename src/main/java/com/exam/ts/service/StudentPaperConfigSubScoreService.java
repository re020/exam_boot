package com.exam.ts.service;

import com.exam.core.exception.ExamException;
import com.exam.ts.pojo.StudentPaperConfigSubScoreDO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.ts.pojo.DTO.QuestionDTO;

/**
 * <p>
 * 学生-试卷-每个题型-主观题得分表（一题一分） 服务类
 * </p>
 *
 * @author 杨德石
 * @since 2019-05-24
 */
public interface StudentPaperConfigSubScoreService extends IService<StudentPaperConfigSubScoreDO> {
    /**
     * 老师进行批改
     */
    void correctQuestion(QuestionDTO questionDTO) throws ExamException;
}
