package com.exam.ex.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.ex.pojo.AuthDO;
import com.exam.ex.pojo.RoleAuthDO;

import java.util.List;

/**
 * <p>
 * 角色-权限表 服务类
 * </p>
 *
 * @author 杨德石
 * @since 2019-04-01
 */
public interface RoleAuthService extends IService<RoleAuthDO> {

    /**
     * 给角色添加权限
     * @param list
     */
    void addAuth(List<RoleAuthDO> list);

    /**
     * 根据角色id列表查询出所有权限（包括父权限）
     * @param roleIds
     * @return
     */
    List<AuthDO> getByRoleIds(List<String> roleIds);
}
