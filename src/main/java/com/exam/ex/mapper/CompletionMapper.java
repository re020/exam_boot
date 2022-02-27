package com.exam.ex.mapper;

import com.exam.ex.dto.GaConfigDTO;
import com.exam.ex.pojo.CompletionDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.core.pojo.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 填空题表 Mapper 接口
 * </p>
 *
 * @author 杨德石
 * @since 2019-03-28
 */
public interface CompletionMapper extends BaseMapper<CompletionDO> {

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    List<CompletionDO> getListByPage(Page<CompletionDO> page);

    /**
     * 查询总数
     *
     * @param page
     * @return
     */
    Integer getCountByPage(Page<CompletionDO> page);

    /**
     * 根据id列表查询
     *
     * @param ids
     * @return
     */
    List<CompletionDO> getByIds(List<String> ids);

    /**
     * 遗传算法专用查询列表
     *
     * @param configDTO
     * @return
     */
    List<CompletionDO> getGaList(GaConfigDTO configDTO);

    /**
     * 遗传算法专用变异查询
     * @param completionDO
     * @param configDTO
     * @return
     */
    List<CompletionDO> getMutateList(@Param("comp") CompletionDO completionDO, @Param("config") GaConfigDTO configDTO);

    /**
     * 根据map中的参数查询全部 携带学生自己做的答案
     *
     * @param
     * @return
     */
    List<CompletionDO> getListByMapStuAnswer(Map<String, Object> paramsMap);

    /**
     * 根据map中的参数查询全部(含答案)
     *
     * @param
     * @return
     */
    List<CompletionDO> getListByMapAnswer(Map<String, Object> paramsMap);
}
