package com.exam.core.realm;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 自定义的token,用来判断当前登录人的角色
 * @author lth
 * @version 1.0.0
 * @date
 */

public class CustomLoginToken extends UsernamePasswordToken {

    private static final long serialVersionUID = 2020457391511655213L;

    private String loginType;

    public CustomLoginToken() {}

    public CustomLoginToken(final String username, final String password,
                            final String loginType) {
        super(username, password);
        this.loginType = loginType;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }


}
