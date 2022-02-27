package com.exam.ex.mapper;

import com.exam.ex.dto.GaConfigDTO;
import com.exam.core.pojo.Page;
import com.exam.ex.pojo.ChoiceDO;
import com.exam.ex.pojo.TrueFalseDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 判断题表 Mapper 接口
 * </p>
 *
 * @author 杨德石
 * @since 2019-04-12
 */
public interface TrueFalseMapper extends BaseMapper<TrueFalseDO> {

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    List<TrueFalseDO> getListByPage(Page<TrueFalseDO> page);

    /**
     * 查询总数
     *
     * @param page
     * @return
     */
    Integer getCountByPage(Page<TrueFalseDO> page);

    /**
     * 遗传算法专用查询列表
     *
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
    List<TrueFalseDO> getMutateList(@Param("tf") TrueFalseDO trueFalseDO, @Param("config") GaConfigDTO configDTO);

    /**
     * 根据map中的参数查询全部 携带学生自己做的答案
     *
     * @param paramsMap
     * @return
     */
    List<TrueFalseDO> getListByMapStuAnswer(Map<String, Object> paramsMap);

    /**
     * 根据map参数查询全部(含答案)
     */
    List<TrueFalseDO> getListByMapAnswer(Map<String, Object> paramsMap);
}
