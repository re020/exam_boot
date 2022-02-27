package com.exam.ts.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exam.ts.mapper.ExamMapper;
import com.exam.ts.mapper.StudentPaperMapper;
import com.exam.ts.pojo.ExamDO;
import com.exam.ts.pojo.StudentPaperConfigDO;
import com.exam.ts.mapper.StudentPaperConfigMapper;
import com.exam.ts.pojo.StudentPaperDO;
import com.exam.ts.service.StudentPaperConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 学生试卷配置表 服务实现类
 * </p>
 *
 * @author 杨德石
 * @since 2019-05-24
 */
@Service
public class StudentPaperConfigServiceImpl extends ServiceImpl<StudentPaperConfigMapper, StudentPaperConfigDO> implements StudentPaperConfigService {

    @Autowired
    private StudentPaperMapper studentPaperMapper;
    @Autowired
    private StudentPaperConfigMapper studentPaperConfigMapper;


    /**
     * 查询试卷中每个题型的数量
     *
     * @param stuId
     * @return
     */
    @Override
    public List<StudentPaperConfigDO> getQuestionNum(String examId, String stuId) {
        QueryWrapper<StudentPaperDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("paper_exam", examId)
                .eq("paper_student", stuId);
        StudentPaperDO examDO = studentPaperMapper.selectOne(queryWrapper);
        if (examDO != null) {
            List<StudentPaperConfigDO> list = studentPaperConfigMapper.getQuestionNum(examDO.getPaperId());
            return list;
        }
        return null;
    }
}
