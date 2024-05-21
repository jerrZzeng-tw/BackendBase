package com.iisi.backendbase.framework.security;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class OpenPolicyAgentAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {
    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        //表示请求的 URL 地址和数据库的地址是否匹配上了
        boolean isMatch = false;
        //获取当前请求的 URL 地址
        String requestURI = context.getRequest().getRequestURI();
        return new AuthorizationDecision(true);
    }
}