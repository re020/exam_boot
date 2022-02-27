package com.exam.ts.service.impl;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exam.core.constant.QuestionEnum;
import com.exam.core.exception.ExamException;
import com.exam.core.utils.IdWorker;
import com.exam.ts.mapper.StudentPaperConfigQuestionMapper;
import com.exam.ts.mapper.StudentPaperConfigScoreMapper;
import com.exam.ts.pojo.StudentPaperConfigQuestionDO;
import com.exam.ts.pojo.StudentPaperConfigScoreDO;
import com.exam.ts.pojo.StudentPaperConfigSubScoreDO;
import com.exam.ts.mapper.StudentPaperConfigSubScoreMapper;
import com.exam.ts.pojo.DTO.QuestionDTO;
import com.exam.ts.service.StudentPaperConfigSubScoreService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 学生-试卷-每个题型-主观题得分表（一题一分） 服务实现类
 * </p>
 *
 * @author 杨德石
 * @since 2019-05-24
 */
@Service
@Slf4j
public class StudentPaperConfigSubScoreServiceImpl extends ServiceImpl<StudentPaperConfigSubScoreMapper, StudentPaperConfigSubScoreDO> implements StudentPaperConfigSubScoreService {

    @Autowired
    private StudentPaperConfigQuestionMapper studentPaperConfigQuestionMapper;
    @Autowired
    private StudentPaperConfigSubScoreMapper studentPaperConfigSubScoreMapper;
    @Autowired
    private IdWorker idWorker;
    @Override
    public void correctQuestion(QuestionDTO questionDTO) throws ExamException {
        // 根据配置和题目Id，进行更新状态
        QueryWrapper<StudentPaperConfigQuestionDO> wrapper =
                new QueryWrapper<>();
        wrapper.eq("question_config",questionDTO.getConfigId())
                .eq("question_id",questionDTO.getQuestionId());
        StudentPaperConfigQuestionDO questionDO = studentPaperConfigQuestionMapper.selectOne(wrapper);
        if(questionDO == null){
            throw new ExamException("查无此题目");
        }
        questionDO.setQuestionState(QuestionEnum.CORRECTED.getCode());
        studentPaperConfigQuestionMapper.updateById(questionDO);

        // 插入学生-试卷-主观题得分表
        log.info("插入学生-试卷-主观题得分表信息:{}",questionDTO);
        QueryWrapper<StudentPaperConfigSubScoreDO> scoreWrapper =
                new QueryWrapper<>();
        scoreWrapper.eq("qs_question",questionDTO.getQuestionId())
                .eq("qs_config",questionDTO.getConfigId())
                .eq("qs_student",questionDTO.getStuId());
        StudentPaperConfigSubScoreDO score = studentPaperConfigSubScoreMapper.selectOne(scoreWrapper);

        if(score == null){
            StudentPaperConfigSubScoreDO newScore = new StudentPaperConfigSubScoreDO();
            newScore.setQsId(idWorker.nextId() + "");
            newScore.setQsConfig(questionDTO.getConfigId());
            newScore.setQsQuestion(questionDTO.getQuestionId());
            newScore.setQsScore(new BigDecimal(questionDTO.getGrade()));
            newScore.setQsStudent(questionDTO.getStuId());
            studentPaperConfigSubScoreMapper.insert(newScore);
        }else{
            score.setQsScore(new BigDecimal(questionDTO.getGrade()));
            studentPaperConfigSubScoreMapper.updateById(score);
        }
        log.info("更新题目分数完毕");
    }
}
