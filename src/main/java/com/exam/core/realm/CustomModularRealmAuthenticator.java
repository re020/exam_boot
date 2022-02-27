package com.exam.core.realm;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.exam.ex.pojo.StudentDO;
import com.exam.ex.pojo.TeacherDO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * 全局shiro拦截分发realm
 * @author lth
 * @version 1.0.0
 * @date
 */
@Data
@Slf4j
public class CustomModularRealmAuthenticator extends ModularRealmAuthenticator{

    private Map<String, Object> definedRealms;

    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 判断getRealams是否返回空
        assertRealmsConfigured();
        // 转回为自定义的token
        CustomLoginToken token = (CustomLoginToken) authenticationToken;
        // 找到当前的登录人登录类型
        String loginType = token.getLoginType();
        // 所有Realm
        Collection<Realm> realms = getRealms();
        // 找到指定的realm
        Collection<Realm> typeRealms = new ArrayList<Realm>();
        for (Realm realm : realms) {
            if (realm.getName().toLowerCase().contains(loginType))
            log.debug("current realm:[{}],my realm:[{}]",realm.getName(),realm);
                typeRealms.add(realm);
        }
        // 判断是单Realm还是多Realm
        if (typeRealms.size() == 1)
            return doSingleRealmAuthentication(typeRealms.iterator().next(), token);
        else
            return doMultiRealmAuthentication(typeRealms, token);

    }

    @Override
    protected void assertRealmsConfigured() throws IllegalStateException {
        definedRealms = getDefinedRealms();
        if (CollectionUtils.isEmpty(this.definedRealms)) {
            throw new ShiroException("值传递错误!");
        }
    }



}
