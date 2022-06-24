package com.pjy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pjy.model.entity.User;
import com.pjy.service.UserService;
import com.pjy.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author admin
* @description 针对表【t_user】的数据库操作Service实现
* @createDate 2022-06-23 01:10:05
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




