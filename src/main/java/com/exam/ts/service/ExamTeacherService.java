package com.exam.ts.service;

import com.exam.core.utils.Result;
import com.exam.ts.pojo.ExamTeacherDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 考试-监考教师表 服务类
 * </p>
 *
 * @author 杨德石
 * @since 2019-05-24
 */
public interface ExamTeacherService extends IService<ExamTeacherDO> {

    /**
     * 检查后进行保存
     * @param examTeacherDO
     * @return
     */
    Result checkAndSave(ExamTeacherDO examTeacherDO);

    /**
     * 根据考试id查询
     * @param examId
     * @return
     */
    List<ExamTeacherDO> getByExamId(String examId);
}
