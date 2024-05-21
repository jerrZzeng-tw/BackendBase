package com.iisi.backendbase.controller;

import com.iisi.backendbase.entity.User;
import com.iisi.backendbase.repo.UserRepository;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(value = {"/user"}, produces = "application/json;charset=UTF-8")
@Slf4j
public class UserController {
    @Resource
    private UserRepository userRepository;

    @RequestMapping(value = {"/myInfo"})
    public User user() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(username).orElse(null);
    }

}