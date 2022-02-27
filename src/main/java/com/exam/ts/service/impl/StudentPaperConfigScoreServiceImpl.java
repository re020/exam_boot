package com.exam.ts.service.impl;

import com.exam.ts.pojo.StudentPaperConfigScoreDO;
import com.exam.ts.mapper.StudentPaperConfigScoreMapper;
import com.exam.ts.service.StudentPaperConfigScoreService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * <p>
 * 学生试卷配置-总得分表 服务实现类
 * </p>
 *
 * @author 杨德石
 * @since 2019-05-24
 */
@Service
public class StudentPaperConfigScoreServiceImpl extends ServiceImpl<StudentPaperConfigScoreMapper, StudentPaperConfigScoreDO> implements StudentPaperConfigScoreService {

    @Autowired
    private StudentPaperConfigScoreMapper studentPaperConfigScoreMapper;

    @Override
    public BigDecimal getGradesByPaperAndStu(String answerPaper, String answerStudent) {
        return studentPaperConfigScoreMapper.getGradesByPaperAndStu(answerPaper,answerStudent);
    }
}
