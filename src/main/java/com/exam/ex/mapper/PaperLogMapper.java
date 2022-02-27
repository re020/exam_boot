package com.exam.ex.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.core.pojo.Page;
import com.exam.ex.pojo.PaperLogDO;

import java.util.List;

/**
 * <p>
 * 组卷日志表 Mapper 接口
 * </p>
 *
 * @author 杨德石
 * @since 2019-05-01
 */
public interface PaperLogMapper extends BaseMapper<PaperLogDO> {

    /**
     * 分页查询
     * @param page
     * @return
     */
    List<PaperLogDO> getListByPage(Page<PaperLogDO> page);

    /**
     * 查询总数
     * @param page
     * @return
     */
    Integer getCountByPage(Page<PaperLogDO> page);
}
