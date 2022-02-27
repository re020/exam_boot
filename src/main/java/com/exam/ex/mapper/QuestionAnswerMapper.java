package com.exam.ex.mapper;

import com.exam.ex.pojo.QuestionAnswerDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.ex.pojo.QuestionDO;

/**
 * <p>
 * 其他题目答案 Mapper 接口
 * </p>
 *
 * @author 杨德石
 * @since 2019-04-18
 */
public interface QuestionAnswerMapper extends BaseMapper<QuestionAnswerDO> {

    /**
     * 删除旧小题
     * @param question
     */
    void deleteOldAnswer(QuestionDO question);
}
