package com.exam.ex.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.ex.dto.GaConfigDTO;
import com.exam.ex.pojo.CompletionDO;
import com.exam.core.pojo.Page;
import com.exam.core.utils.Result;

import java.util.List;

/**
 * <p>
 * 填空题表 服务类
 * </p>
 *
 * @author 杨德石
 * @since 2019-03-28
 */
public interface CompletionService extends IService<CompletionDO> {

    /**
     * 分页查询
     * @param page
     * @return
     */
    Page<CompletionDO> getByPage(Page<CompletionDO> page);

    /**
     * 添加或修改填空题
     * @param completion
     * @return
     */
    Result saveOrUpdateCompletion(CompletionDO completion);

    /**
     * 随机查询列表（遗传算法专用）
     * @param configDTO
     * @return
     */
    List<CompletionDO> getGaList(GaConfigDTO configDTO);

    /**
     * 遗传算法专用 变异查询
     * @param completionDO
     * @param configDTO
     * @return
     */
    List<CompletionDO> getMutateList(CompletionDO completionDO, GaConfigDTO configDTO);
}
