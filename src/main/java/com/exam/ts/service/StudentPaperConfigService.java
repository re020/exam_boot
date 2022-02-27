package com.exam.ts.service;

import com.exam.ts.pojo.StudentPaperConfigDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 学生试卷配置表 服务类
 * </p>
 *
 * @author 杨德石
 * @since 2019-05-24
 */
public interface StudentPaperConfigService extends IService<StudentPaperConfigDO> {
    /**
     * 查询试卷中每个题型的数量
     * @param examId 考试id stuId 学生id
     * @return
     */
    List<StudentPaperConfigDO> getQuestionNum(String examId,String stuId);

}
