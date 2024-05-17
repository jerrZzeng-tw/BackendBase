package com.iisi.backendbase.framework.services;

import com.iisi.backendbase.framework.BaseRuntimeException;
import com.iisi.backendbase.framework.dto.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;

    public LoginDTO login(LoginDTO loginDTO) {
        //创建一个UsernamePasswordAuthenticationToken对象，将用户的用户名和密码作为参数传入。
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
        //调用authenticationManager.authenticate()方法对用户进行身份验证，返回一个Authentication对象。
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        //如果authenticate对象为空，表示用户名或密码错误，抛出一个运行时异常。
        if (Objects.isNull(authenticate)) {
            throw new BaseRuntimeException("用户名或者密码错误");
        }
        return loginDTO;
    }
}