package com.iisi.backendbase.controller;

import com.iisi.backendbase.entity.Item;
import com.iisi.backendbase.entity.ItemUrl;
import com.iisi.backendbase.entity.Role;
import com.iisi.backendbase.entity.User;
import com.iisi.backendbase.repo.ItemRepository;
import com.iisi.backendbase.repo.ItemUrlRepository;
import com.iisi.backendbase.repo.RoleRepository;
import com.iisi.backendbase.repo.UserRepository;
import com.iisi.backendbase.repo.UserRoleRepository;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping(value = {"/test"}, produces = "application/json;charset=UTF-8")
@Slf4j
public class TestController {
    @Resource
    private UserRepository userRepository;
    @Resource
    private RoleRepository roleRepository;
    @Resource
    private UserRoleRepository userRoleRepository;
    @Resource
    private ItemRepository itemRepository;
    @Resource
    private ItemUrlRepository itemUrlRepository;

    @RequestMapping(value = {"/user"})
    public List<User> users() {
        return userRepository.findAll();
    }

    @RequestMapping(value = {"/role"})
    public List<Role> roles() {
        return roleRepository.findAll();
    }

    @RequestMapping(value = {"/item"})
    public List<Item> items() {
        return itemRepository.findAll();
    }

    @RequestMapping(value = {"/itemUrl"})
    public List<ItemUrl> itemUrls() {
        return itemUrlRepository.findAll();
    }

    @RequestMapping(value = {"/userRoleInfo"})
    public List<UserRepository.UserRoleDTO> userRole() {
        return userRepository.findAllUserRoleNative();
    }

    @CacheEvict(value = "authCache", allEntries = true)
    @RequestMapping(value = {"/cleanCache"})
    public void cleanCache() {
        log.info("cleanCache");
    }
}