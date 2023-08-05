package com.lansu.awakening.service.impl;

import com.lansu.awakening.entity.User;
import com.lansu.awakening.mapper.UserMapper;
import com.lansu.awakening.service.UserService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现
 *
 * @author sulan
 * @date 2023/08/03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

}
