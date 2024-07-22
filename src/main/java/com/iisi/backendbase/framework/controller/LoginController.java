package com.iisi.backendbase.framework.controller;

import com.iisi.backendbase.framework.BaseRuntimeException;
import com.iisi.backendbase.framework.dto.LoginDTO;
import com.iisi.backendbase.framework.security.JwtTokenProvider;
import com.iisi.backendbase.framework.services.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController()
@RequestMapping(value = {"/"}, produces = "application/json;charset=UTF-8")
@Slf4j
public class LoginController {
    @Autowired
    private AuthService authService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @RequestMapping(value = {"/login"})
    public LoginDTO login(@RequestBody LoginDTO loginDTO) {
        //创建一个UsernamePasswordAuthenticationToken对象，将用户的用户名和密码作为参数传入。
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
        //调用authenticationManager.authenticate()方法对用户进行身份验证，返回一个Authentication对象。
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //如果authenticate对象为空，表示用户名或密码错误，抛出一个运行时异常。
        if (Objects.isNull(authenticate)) {
            throw new BaseRuntimeException("用户名或者密码错误");
        }
        loginDTO.setPassword("");
        loginDTO.setJwtToken(jwtTokenProvider.generateToken(loginDTO.getUsername()));
        loginDTO.setRoles(authenticate.getAuthorities().stream().map(Object::toString).toList());
        return loginDTO;
    }
}