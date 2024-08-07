package com.iisi.backendbase.controller;

import com.iisi.backendbase.entity.User;
import com.iisi.backendbase.framework.annotation.ApLog;
import com.iisi.backendbase.framework.dto.UserDTO;
import com.iisi.backendbase.repo.ItemRepository;
import com.iisi.backendbase.repo.RoleRepository;
import com.iisi.backendbase.repo.UserRepository;
import com.iisi.backendbase.repo.UserRoleRepository;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping(value = {"/admin"}, produces = "application/json;charset=UTF-8")
@Slf4j
public class AdminController {
    @Resource
    private UserRepository userRepository;
    @Resource
    private RoleRepository roleRepository;
    @Resource
    private UserRoleRepository userRoleRepository;
    @Resource
    private ItemRepository itemRepository;

    @ApLog
    @RequestMapping(value = {"/users"})
    public List<User> users() {
        return userRepository.findAll();
    }

    @ApLog
    @RequestMapping(value = {"/user"})
    public User user(@RequestBody UserDTO user) {
        return userRepository.findByUsername(user.getUsername()).orElse(null);
    }

    @ApLog
    @RequestMapping(value = {"/roles"})
    public List<User> roles() {
        return userRepository.findAll();
    }
}