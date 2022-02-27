package com.exam.ex.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.ex.dto.GaConfigDTO;
import com.exam.core.pojo.Page;
import com.exam.ex.pojo.QuestionDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 其他题型 Mapper 接口
 * </p>
 *
 * @author 杨德石
 * @since 2019-04-12
 */
public interface QuestionMapper extends BaseMapper<QuestionDO> {

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    List<QuestionDO> getListByPage(Page<QuestionDO> page);

    /**
     * 查询总数
     *
     * @param page
     * @return
     */
    Integer getCountByPage(Page<QuestionDO> page);

    /**
     * 根据id列表查询
     *
     * @param ids
     * @return
     */
    List<QuestionDO> getByIds(List<String> ids);

    /**
     * 遗传算法专用查询列表
     *
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
    List<QuestionDO> getMutateList(@Param("question") QuestionDO questionDO, @Param("config") GaConfigDTO configDTO);

    /**
     * 返回题目(含学生做题答案)
     */
    List<QuestionDO> getListByMapWithAnswer(Map<String, Object> paramsMap);
}
