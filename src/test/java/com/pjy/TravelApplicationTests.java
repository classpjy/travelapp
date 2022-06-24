package com.pjy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pjy.mapper.UserMapper;
import com.pjy.model.entity.User;
import com.pjy.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class TravelApplicationTests {
    @Resource
    UserService userService;
    @Resource
    UserMapper userMapper;
    @Test
    void contextLoads() throws JsonProcessingException {
        User user = new User(1, "pjy", "123", "123@qq.com");
        userService.getById(1);
        userService.getById(1);

    }

}
