package com.exam.ex.service.impl;

import com.exam.ex.pojo.ChoiceAnswerDO;
import com.exam.ex.mapper.ChoiceAnswerMapper;
import com.exam.ex.pojo.ChoiceDO;
import com.exam.ex.service.ChoiceAnswerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 选项表 服务实现类
 * </p>
 *
 * @author 杨德石
 * @since 2019-03-28
 */
@Service
public class ChoiceAnswerServiceImpl extends ServiceImpl<ChoiceAnswerMapper, ChoiceAnswerDO> implements ChoiceAnswerService {

    @Autowired
    private ChoiceAnswerMapper choiceAnswerMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOldAnswer(ChoiceDO choice) {
        choiceAnswerMapper.deleteOldAnswer(choice);
    }

    @Override
    public List<ChoiceAnswerDO> getListByIds(String[] ids) {
        return choiceAnswerMapper.getListByIds(ids);
    }
}
