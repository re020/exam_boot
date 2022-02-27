package com.exam.ex.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.core.pojo.Page;
import com.exam.ex.pojo.TypeDO;

import java.util.List;

/**
 * <p>
 * 题型表 服务类
 * </p>
 *
 * @author 杨德石
 * @since 2019-03-28
 */
public interface TypeService extends IService<TypeDO> {

    /**
     * 分页查询
     * @param page
     * @return
     */
    Page<TypeDO> getListByPage(Page<TypeDO> page);

    /**
     * 根据知识点id查询所有
     * @param knowId
     * @return
     */
    List<TypeDO> getByKnowId(String knowId);

    /**
     * 根据知识点id数组查询所有
     * @param knowIds
     * @return
     */
    List<TypeDO> getByKnowIds(List<String> knowIds);
}
