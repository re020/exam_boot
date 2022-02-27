package com.exam.ex.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.ex.mapper.AuthMapper;
import com.exam.ex.mapper.RoleAuthMapper;
import com.exam.ex.mapper.RoleMapper;
import com.exam.ex.pojo.AuthDO;
import com.exam.ex.pojo.RoleAuthDO;
import com.exam.ex.pojo.RoleDO;
import com.exam.ex.service.RoleAuthService;
import com.exam.core.utils.CollectionsUtils;
import com.exam.core.utils.IdWorker;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色-权限表 服务实现类
 * </p>
 *
 * @author 杨德石
 * @since 2019-04-01
 */
@Service
public class RoleAuthServiceImpl extends ServiceImpl<RoleAuthMapper, RoleAuthDO> implements RoleAuthService {

    @Autowired
    private IdWorker idWorker;
    @Autowired
    private RoleAuthMapper roleAuthMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private AuthMapper authMapper;

    /**
     * 授权
     *
     * @param list
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addAuth(List<RoleAuthDO> list) {
        if (list.size() > 0) {
            String roleId = list.get(0).getRaRole();
            QueryWrapper<RoleAuthDO> wrapper = new QueryWrapper<RoleAuthDO>()
                    .eq("ra_role", roleId);
            roleAuthMapper.delete(wrapper);

            // 设置id
            list = list.stream().map(e -> {
                RoleAuthDO ra = new RoleAuthDO();
                ra.setRaId(idWorker.nextId() + "");
                ra.setRaAuth(e.getRaAuth());
                ra.setRaRole(e.getRaRole());
                return ra;
            }).collect(Collectors.toList());
            CollectionsUtils.duplication(list);
            roleAuthMapper.saveBatch(list);
        }
    }

    /**
     * 根据角色id查询权限（父角色权限也要查）
     *
     * @param roleIds
     * @return
     */
    @Override
    public List<AuthDO> getByRoleIds(List<String> roleIds) {

        if (roleIds == null || roleIds.isEmpty()) {
            return Lists.newArrayList();
        } else {
            // 查询这些角色的权限对应关系
            List<RoleAuthDO> roleAuthDOList = roleAuthMapper.selectList(new QueryWrapper<RoleAuthDO>().in("ra_role", roleIds));
            if (!roleAuthDOList.isEmpty()) {
                List<String> authIds = roleAuthDOList.stream().map(RoleAuthDO::getRaAuth).collect(Collectors.toList());
                List<AuthDO> authList = authMapper.selectBatchIds(authIds);

                // 查询父id，根据父id查询
                List<RoleDO> roleList = roleMapper.selectBatchIds(roleIds);

                List<String> fatherIds = roleList.stream().map(RoleDO::getRoleFather).collect(Collectors.toList());

                List<AuthDO> fatherAuth = getByRoleIds(fatherIds);

                authList.addAll(fatherAuth);

                List<AuthDO> list = Lists.newArrayList();

                for (AuthDO authDO : authList) {
                    if (!list.contains(authDO)) {
                        list.add(authDO);
                    }
                }
                return list;

            } else {
                return Lists.newArrayList();
            }
        }

    }
}
