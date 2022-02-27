package com.exam.ex.service;

import com.exam.ex.pojo.ChoiceAnswerDO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.ex.pojo.ChoiceDO;

import java.util.List;

/**
 * <p>
 * 选项表 服务类
 * </p>
 *
 * @author 杨德石
 * @since 2019-03-28
 */
public interface ChoiceAnswerService extends IService<ChoiceAnswerDO> {

    /**
     * 删除旧答案
     * @param choice
     */
    void deleteOldAnswer(ChoiceDO choice);

    List<ChoiceAnswerDO> getListByIds(String[] objects);
}
