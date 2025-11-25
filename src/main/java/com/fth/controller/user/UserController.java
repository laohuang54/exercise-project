package com.fth.controller;

import com.fth.dto.LoginDTO;
import com.fth.dto.Result;
import com.fth.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("/login")
    public Result login(@RequestParam LoginDTO loginDTO) {
        return userService.login(loginDTO);
    }

}
