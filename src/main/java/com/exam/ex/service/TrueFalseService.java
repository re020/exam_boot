package com.exam.ex.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.ex.dto.GaConfigDTO;
import com.exam.core.pojo.Page;
import com.exam.ex.pojo.TrueFalseDO;

import java.util.List;

/**
 * <p>
 * 判断题表 服务类
 * </p>
 *
 * @author 杨德石
 * @since 2019-04-12
 */
public interface TrueFalseService extends IService<TrueFalseDO> {

    /**
     * 分页查询
     * @param page
     * @return
     */
    Page<TrueFalseDO> getByPage(Page<TrueFalseDO> page);

    /**
     * 随机查询列表（遗传算法专用）
     * @param configDTO
     * @return
     */
    List<TrueFalseDO> getGaList(GaConfigDTO configDTO);

    /**
     * 遗传算法专用变异查询
     * @param trueFalseDO
     * @param configDTO
     * @return
     */
    List<TrueFalseDO> getMutateList(TrueFalseDO trueFalseDO, GaConfigDTO configDTO);
}
