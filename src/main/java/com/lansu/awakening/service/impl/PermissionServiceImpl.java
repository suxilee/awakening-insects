package com.lansu.awakening.service.impl;

import com.lansu.awakening.entity.Permission;
import com.lansu.awakening.mapper.PermissionMapper;
import com.lansu.awakening.service.PermissionService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 许可服务实现
 *
 * @author sulan
 * @date 2023/08/03
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

}
