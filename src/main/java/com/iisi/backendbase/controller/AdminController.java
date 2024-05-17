package com.iisi.backendbase.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(value = {"/admin"}, produces = "application/json;charset=UTF-8")
@Slf4j
public class AdminController {
}