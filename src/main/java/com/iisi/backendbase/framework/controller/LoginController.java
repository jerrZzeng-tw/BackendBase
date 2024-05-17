package com.iisi.backendbase.framework.controller;

import com.iisi.backendbase.framework.dto.LoginDTO;
import com.iisi.backendbase.framework.services.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(value = {"/"}, produces = "application/json;charset=UTF-8")
@Slf4j
public class LoginController {
    @Autowired
    private LoginService loginService;

    @RequestMapping(value = {"/login"})
    public LoginDTO login(@RequestBody LoginDTO loginDTO) {
        loginDTO.setJwtToken("jwtToken");
        return loginService.login(loginDTO);
    }
}