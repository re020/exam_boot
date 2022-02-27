package com.exam.core.realm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exam.ex.pojo.StudentDO;
import com.exam.ex.pojo.TeacherDO;
import com.exam.ex.service.StudentService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lth
 * @version 1.0.0
 * @date
 */

public class StudentRealm extends AuthorizingRealm {

    @Autowired
    private StudentService studentService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermission("paper:list");
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
        CustomLoginToken passwordToken = (CustomLoginToken) token;
        String username = passwordToken.getUsername();

        QueryWrapper<StudentDO> wrapper = new QueryWrapper<>();
        wrapper.eq("stu_number", username);
        StudentDO studentDO = studentService.getOne(wrapper);
        if(studentDO == null) {
            // 用户名不存在
            throw new UnknownAccountException();
        }

        // 框架负责比对数据库中的密码和页面输入的密码是否一致
        AuthenticationInfo info = new SimpleAuthenticationInfo(studentDO, studentDO.getStuPassword(), this.getName());
        return info;
    }
}
