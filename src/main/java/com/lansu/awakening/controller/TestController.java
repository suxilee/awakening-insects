package com.lansu.awakening.controller;

import com.lansu.awakening.annotation.Log4ai;
import com.lansu.awakening.entity.User;
import com.lansu.awakening.mapper.UserMapper;
import com.lansu.awakening.result.R;
import com.lansu.awakening.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 测试控制器
 *
 * @author sulan
 * @date 2023/08/05
 */
@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class TestController {


    private final UserService userService;

    @GetMapping("/admin")
    @Log4ai
    public String test(String data) {
        return "admin";
    }

    @GetMapping("/user")
    @Log4ai
    public String user(String data){
        return "user";
    }

    @GetMapping("/test_long")
    @Log4ai
    public Long testLong(){
        return Long.MAX_VALUE;
    }

    @GetMapping("/test_r")
    @Log4ai
    public R testR(){
        return R.ok();
    }

    @GetMapping("/all_user")
    @Log4ai
    public List<User> allUser(){
        return userService.list();
    }
}
