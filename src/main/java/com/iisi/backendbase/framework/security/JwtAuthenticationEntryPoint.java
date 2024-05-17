package com.iisi.backendbase.framework.security;

import com.iisi.backendbase.framework.ResponseData;
import com.iisi.backendbase.framework.StatusCode;
import com.iisi.backendbase.utils.WebUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        try {
            WebUtils.renderString(response, new ResponseData("N", StatusCode.AUTH_ERROR, null));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}