package com.lansu.awakening.controller;

import com.lansu.awakening.annotation.Log4ai;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
