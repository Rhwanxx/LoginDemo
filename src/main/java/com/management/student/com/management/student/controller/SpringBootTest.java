package com.management.student.com.management.student.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Springboot框架测试类（可以删除）
 * @author wanrh@jurassic.com.cn
 * @date 2018/12/21 13:26
 */
@RestController
public class SpringBootTest {

    /**
     * springboot框架测试
     */
    @RequestMapping("/hello")
    public String HelloWorld(){
        return "SpringBoot 运行成功";
    }
}
