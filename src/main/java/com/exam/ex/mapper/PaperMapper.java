package com.exam.ex.mapper;

import com.exam.core.pojo.Page;
import com.exam.ex.pojo.PaperDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 试卷表 Mapper 接口
 * </p>
 *
 * @author 杨德石
 * @since 2019-04-20
 */
@Mapper
public interface PaperMapper extends BaseMapper<PaperDO> {

    /**
     * 分页查询
     * @param page
     * @return
     */
    List<PaperDO> getListByPage(Page<PaperDO> page);

    /**
     * 查询总数
     * @param page
     * @return
     */
    Integer getCountByPage(Page<PaperDO> page);

    /**
     * 查询试卷中的题目
     * @param paperId
     * @return
     */
    PaperDO getPaperQuestion(String paperId);

}
