package com.exam.ex.mapper;

import com.exam.ex.pojo.CodeAnswerDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.ex.pojo.CodeDO;

/**
 * <p>
 * 编程题-答案 Mapper 接口
 * </p>
 *
 * @author 杨德石
 * @since 2019-04-19
 */
public interface CodeAnswerMapper extends BaseMapper<CodeAnswerDO> {

    /**
     * 删除旧答案
     * @param codeDO
     */
    void deleteOldAnswer(CodeDO codeDO);
}
