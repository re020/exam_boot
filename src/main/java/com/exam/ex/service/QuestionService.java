package com.exam.ex.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.ex.dto.GaConfigDTO;
import com.exam.core.pojo.Page;
import com.exam.ex.pojo.QuestionDO;
import com.exam.ex.vo.QuestionVO;

import java.util.List;

/**
 * <p>
 * 其他题型 服务类
 * </p>
 *
 * @author 杨德石
 * @since 2019-04-12
 */
public interface QuestionService extends IService<QuestionDO> {

    /**
     * 修改题目
     * @param question
     * @return
     */
    void updateQuestion(QuestionDO question);

    /**
     * 添加题目
     * @param question
     */
    void saveQuestion(QuestionDO question);

    /**
     * 分页查询
     * @param page
     * @return
     */
    Page<QuestionDO> getByPage(Page<QuestionDO> page);

    /**
     * 分页查找所有类型题目
     * @param voPage
     * @return
     */
    Page<QuestionVO> getVoByPage(Page<QuestionVO> voPage);

    /**
     * 随机查询列表（遗传算法专用）
     * @param configDTO
     * @return
     */
    List<QuestionDO> getGaList(GaConfigDTO configDTO);

    /**
     * 遗传算法专用变异查询
     * @param questionDO
     * @param configDTO
     * @return
     */
    List<QuestionDO> getMutateList(QuestionDO questionDO, GaConfigDTO configDTO);
}
