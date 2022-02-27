package com.exam.ex.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.ex.dto.GaConfigDTO;
import com.exam.ex.pojo.CodeDO;
import com.exam.core.pojo.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 编程题 Mapper 接口
 * </p>
 *
 * @author 杨德石
 * @since 2019-04-17
 */
@Mapper
public interface CodeMapper extends BaseMapper<CodeDO> {

    /**
     * 分页查询
     * @param page
     * @return
     */
    List<CodeDO> getListByPage(Page<CodeDO> page);

    /**
     * 分页查询
     * @param page
     * @return
     */
    Integer getCountByPage(Page<CodeDO> page);

    /**
     * 根据id列表查
     * @param ids
     * @return
     */
    List<CodeDO> getByIds(List<String> ids);

    /**
     * 遗传算法专用查询列表
     * @param configDTO
     * @return
     */
    List<CodeDO> getGaList(GaConfigDTO configDTO);

    /**
     * 遗传算法专用变异查询
     * @param codeDO
     * @param configDTO
     * @return
     */
    List<CodeDO> getMutateList(@Param("code") CodeDO codeDO, @Param("config") GaConfigDTO configDTO);


    List<CodeDO> getListByMapWithAnswer(Map<String, Object> paramsMap);
}
