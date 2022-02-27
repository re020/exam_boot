package com.exam.ex.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.ex.pojo.PaperConfigDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 试卷配置表 Mapper 接口
 * </p>
 *
 * @author 杨德石
 * @since 2019-04-12
 */
public interface PaperConfigMapper extends BaseMapper<PaperConfigDO> {

    /**
     * 根据试卷、题型id、知识点id查询试卷配置
     * @param config
     * @return
     */
    PaperConfigDO getByConfig(PaperConfigDO config);

    /**
     * 查询试卷中每个题型的数量
     * @param paperId
     * @return
     */
    List<PaperConfigDO> getQuestionNum(String paperId);

    /**
     * 根据试卷id和题目id查询配置
     * @param paperId
     * @param questionId
     * @return
     */
    PaperConfigDO getByPaperAndQuestion(@Param("paperId") String paperId, @Param("questionId") String questionId);

    /**
     * 批量插入
     * @param configList
     */
    void saveBatch(List<PaperConfigDO> configList);

    /**
     * 获取试卷每个题型的题量
     * @param id
     * @return
     */
    List<PaperConfigDO> getTypeNum(String id);
}
