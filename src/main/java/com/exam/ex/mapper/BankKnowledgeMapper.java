package com.exam.ex.mapper;

import com.exam.ex.pojo.BankKnowledgeDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.core.pojo.Page;

import java.util.List;

/**
 * <p>
 * 知识点表 Mapper 接口
 * </p>
 *
 * @author 杨德石
 * @since 2019-04-11
 */
public interface BankKnowledgeMapper extends BaseMapper<BankKnowledgeDO> {

    /**
     * 查询总数
     * @param page
     * @return
     */
    Integer getCountByPage(Page<BankKnowledgeDO> page);

    /**
     * 分页查询
     * @param page
     * @return
     */
    List<BankKnowledgeDO> getListByPage(Page<BankKnowledgeDO> page);
}
