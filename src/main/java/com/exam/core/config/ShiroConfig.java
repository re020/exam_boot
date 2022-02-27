package com.exam.core.config;

import com.exam.core.constant.CoreConstant;
import com.exam.core.constant.UserType;
import com.exam.core.filter.OptionsAuthenticationFilter;
import com.exam.core.manager.MySessionManager;
import com.exam.core.realm.CustomModularRealmAuthenticator;
import com.exam.core.realm.CustomerAutheizer;
import com.exam.core.realm.ExamRealm;
import com.exam.core.realm.StudentRealm;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.management.MXBean;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * shiro配置类
 *
 * @author
 */
@Configuration
public class ShiroConfig {


    /**
     * 创建ShiroFilterFactoryBean
     */
    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") SecurityManager securityManager) {
        System.out.println("==================ShiroFilterFactoryBean====================");
        // 设置安全管理器
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 配置自定义shiro过滤器
        Map<String, Filter> optionsFilter = Maps.newHashMap();
        OptionsAuthenticationFilter authenticationFilter = new OptionsAuthenticationFilter();
        optionsFilter.put("authc", authenticationFilter);
        shiroFilterFactoryBean.setFilters(optionsFilter);


        /**
         * 常用过滤器
         *  anon：无需认证可以访问
         *  authc：必须认证才能访问
         *  user：如果使用rememberMe的功能可以直接访问
         *  perms：该资源必须得到权限才可以访问
         *  role：该资源必须得到角色权限才可以访问
         */
        Map<String, String> filterMap = Maps.newHashMap();
        filterMap.put("/teacher/login", "anon");
//        学生的接口<---------
        filterMap.put("/student/login", "anon");
        filterMap.put("/exam/getList","anon");

//        ---------->
        filterMap.put("/logout", "logout");
        filterMap.put("/upload/**", "anon");
        filterMap.put("/upload", "anon");
        filterMap.put("/file/**", "anon");
        filterMap.put("/export/paper", "anon");
        filterMap.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 创建DefaultSecurityManager
     */
    @Bean("securityManager")
    public SecurityManager securityManager(@Qualifier("examReam") ExamRealm examRealm, @Qualifier("studentReam") StudentRealm studentRealm
            , @Qualifier("modularRealmAuthenticator") CustomModularRealmAuthenticator modularRealmAuthenticator,
                                           @Qualifier("autheizer") CustomerAutheizer customerAutheizer) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 自定义session管理 使用redis
        securityManager.setSessionManager(sessionManager());
        // 加入认证器
        securityManager.setAuthenticator(modularRealmAuthenticator);
        // 加入鉴权器
        securityManager.setAuthorizer(customerAutheizer);
        List<Realm> list = Lists.newArrayList();
        list.add(studentRealm);
        list.add(examRealm);
        // 关联realm
        securityManager.setRealms(list);
        return securityManager;
    }

    /**
     * 自定义modularRealmAuthenticator
     */
    @Bean("modularRealmAuthenticator")
    public CustomModularRealmAuthenticator modularRealmAuthenticator(@Qualifier("examReam") ExamRealm examRealm, @Qualifier("studentReam") StudentRealm studentRealm) {
        CustomModularRealmAuthenticator authenticator = new CustomModularRealmAuthenticator();
        HashMap<String, Object> hashMap = new HashMap<>(16);
        hashMap.put(UserType.TEACHER.getCode(), examRealm);
        hashMap.put(UserType.STUDENT.getCode(), studentRealm);
        authenticator.setDefinedRealms(hashMap);
        // 只要有一个Realam认证成功即可 并且返回认证信息
        FirstSuccessfulStrategy strategy = new FirstSuccessfulStrategy();
        authenticator.setAuthenticationStrategy(strategy);
        return authenticator;
    }

    @Bean("autheizer")
    public CustomerAutheizer autheizer(){
        return new CustomerAutheizer();
    }
    /**
     * 自定义sessionManager
     */
    @Bean("sessionManager")
    public SessionManager sessionManager() {
        MySessionManager mySessionManager = new MySessionManager();
        mySessionManager.setGlobalSessionTimeout(CoreConstant.REDIS_TIMEOUT);
        return mySessionManager;
    }


    /**
     * 创建Realm
     */
    @Bean("examReam")
    public ExamRealm examRealm() {
        return new ExamRealm();
    }

    @Bean("studentReam")
    public StudentRealm studentRealm() {
        return new StudentRealm();
    }

    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public static DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public MethodInvokingFactoryBean methodInvokingFactoryBean(@Qualifier("securityManager") SecurityManager securityManager) {
        MethodInvokingFactoryBean bean = new MethodInvokingFactoryBean();
        bean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        bean.setArguments(securityManager);
        return bean;
    }

}