package com.exam.core.utils;

import com.exam.ex.pojo.StudentDO;
import com.exam.ex.pojo.TeacherDO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.stereotype.Component;

/**
 * Shiro相关工具类
 *
 * @Author: 杨德石
 * @Date: 2019/5/9 0009 下午 8:43
 * @Version 1.0
 */

public class ShiroUtils {

    private ShiroUtils() {
    }

    /**
     * 获取登录中的用户
     *
     * @return
     */
    public static TeacherDO getLoginTeacher() {
        Session session = SecurityUtils.getSubject().getSession();
        SimplePrincipalCollection principalCollection = (SimplePrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
        if (principalCollection == null) {
            return null;
        }
        return (TeacherDO) principalCollection.getPrimaryPrincipal();
    }

    /**
     * 获取登录中的用户
     *
     * @return
     */
    public static StudentDO getLoginStudent() {
        Session session = SecurityUtils.getSubject().getSession();
        SimplePrincipalCollection principalCollection = (SimplePrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
        if (principalCollection == null) {
            return null;
        }
        return (StudentDO) principalCollection.getPrimaryPrincipal();
    }

}
