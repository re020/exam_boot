package com.exam.ts.service;

import com.exam.core.exception.ExamException;
import com.exam.ts.pojo.DTO.CommitDTO;
import com.exam.ts.pojo.StudentAnswerDO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.ts.pojo.DTO.AnswerDTO;
import com.exam.ts.pojo.DTO.TopicDTO;

/**
 * <p>
 * 学生做题答案表 服务类
 * </p>
 *
 * @author 杨德石
 * @since 2019-05-24
 */
public interface StudentAnswerService extends IService<StudentAnswerDO> {


    /**
     *功能描述  考试提交
     * @author lth
     * @param studentAnswerDO
     */
    boolean submit(StudentAnswerDO studentAnswerDO);

    /**
     *功能描述 异步消息处理
     * @author lth
     * @param stuAnswerDO
     * @return void
     */

    void asyncHandler(StudentAnswerDO stuAnswerDO);

    /**
     * 保存做题
     */
    void saveIssue(StudentAnswerDO answerDO);
}
