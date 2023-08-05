package com.lansu.awakening.service.impl;

import com.lansu.awakening.entity.Role;
import com.lansu.awakening.mapper.RoleMapper;
import com.lansu.awakening.service.RoleService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 角色服务实现
 *
 * @author sulan
 * @date 2023/08/03
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
