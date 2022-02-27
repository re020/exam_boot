package com.exam.ts.service.impl;

import com.exam.ts.pojo.StudentPaperConfigObjScoreDO;
import com.exam.ts.mapper.StudentPaperConfigObjScoreMapper;
import com.exam.ts.service.StudentPaperConfigObjScoreService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 学生-试卷-每个题型-客观题得分表（一题一分） 服务实现类
 * </p>
 *
 * @author 杨德石
 * @since 2019-05-24
 */
@Service
public class StudentPaperConfigObjScoreServiceImpl extends ServiceImpl<StudentPaperConfigObjScoreMapper, StudentPaperConfigObjScoreDO> implements StudentPaperConfigObjScoreService {

}
