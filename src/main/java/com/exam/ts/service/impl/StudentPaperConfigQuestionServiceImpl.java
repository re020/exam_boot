package com.exam.ts.service.impl;

import com.exam.ex.mapper.PaperMapper;
import com.exam.ex.pojo.PaperDO;
import com.exam.ts.pojo.StudentPaperConfigQuestionDO;
import com.exam.ts.mapper.StudentPaperConfigQuestionMapper;
import com.exam.ts.service.StudentPaperConfigQuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 学生试卷配置-题目表 服务实现类
 * </p>
 *
 * @author 杨德石
 * @since 2019-05-24
 */
@Service
public class StudentPaperConfigQuestionServiceImpl extends ServiceImpl<StudentPaperConfigQuestionMapper, StudentPaperConfigQuestionDO> implements StudentPaperConfigQuestionService {

    @Autowired
    private PaperMapper paperMapper;

}
