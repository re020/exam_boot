package com.exam.ex.mapper;

import com.exam.ex.pojo.RoleAuthDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 角色-权限表 Mapper 接口
 * </p>
 *
 * @author 杨德石
 * @since 2019-04-01
 */
public interface RoleAuthMapper extends BaseMapper<RoleAuthDO> {

    /**
     * 批量添加
     * @param list
     */
    void saveBatch(List<RoleAuthDO> list);
}
