package com.exam.core.realm;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exam.core.utils.ShiroUtils;
import com.exam.ex.pojo.AuthDO;
import com.exam.ex.pojo.RoleDO;
import com.exam.ex.pojo.TeacherDO;
import com.exam.ex.pojo.TeacherRoleDO;
import com.exam.ex.service.RoleAuthService;
import com.exam.ex.service.RoleService;
import com.exam.ex.service.TeacherRoleService;
import com.exam.ex.service.TeacherService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限控制的Realm
 * @author
 */
public class ExamRealm extends AuthorizingRealm {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private TeacherRoleService teacherRoleService;
    @Autowired
    private RoleAuthService roleAuthService;
    /**
     * 授权方法
     * @param principalCollection
     * @author 杨德石
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // TODO 这里转化异常 更改的方案是存string
        // 获取登录中的用户
        TeacherDO teacherDO = (TeacherDO) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        // 查询角色， 封装成集合
        List<TeacherRoleDO> roleList = teacherRoleService.getByTeacher(teacherDO);
        // Lambda表达式取出集合中指定元素封装成另一个集合
        List<String> roleIds = roleList.stream().map(TeacherRoleDO::getTrRole).collect(Collectors.toList());
        // 使用roleIds查询所有的角色，将角色名封装成集合
        List<String> roleNames = roleService.listByIds(roleIds).stream().map(RoleDO::getRoleName).collect(Collectors.toList());
        info.addRoles(roleNames);

        // 根据roles查询权限
        List<AuthDO> authList = roleAuthService.getByRoleIds(roleIds);
        List<String> authCodes = authList.stream().map(AuthDO::getAuthCode).collect(Collectors.toList());
        info.addStringPermissions(authCodes);
        return info;
    }

    /**
     * 认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 根据用户名查询数据库中的密码
        CustomLoginToken passwordToken = (CustomLoginToken) token;
        String username = passwordToken.getUsername();

        QueryWrapper<TeacherDO> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_username", username);
        TeacherDO teacherDO = teacherService.getOne(wrapper);
        if(teacherDO == null) {
            // 用户名不存在
            return null;
        }

        // 框架负责比对数据库中的密码和页面输入的密码是否一致
        AuthenticationInfo info = new SimpleAuthenticationInfo(teacherDO, teacherDO.getTeacherPassword(), this.getName());
        return info;
    }
}
