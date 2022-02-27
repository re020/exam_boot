package com.exam.ts.service;

import com.exam.core.exception.ExamException;
import com.exam.core.pojo.Page;
import com.exam.ts.pojo.ExamLogDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 考试日志表 服务类
 * </p>
 *
 * @author 杨德石
 * @since 2019-05-24
 */
public interface ExamLogService extends IService<ExamLogDO> {

    /**
     * 生成考试日志
     */
    void addExamLog(String examId) throws ExamException;
    /**
     * 分页查询
     * @param page
     * @return
     */
    Page<ExamLogDO> getListByPage(Page<ExamLogDO> page);
}
