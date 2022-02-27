package com.exam.ex.service.impl;

import com.exam.ex.pojo.AuthDO;
import com.exam.ex.mapper.AuthMapper;
import com.exam.ex.service.AuthService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author 杨德石
 * @since 2019-04-01
 */
@Service
public class AuthServiceImpl extends ServiceImpl<AuthMapper, AuthDO> implements AuthService {

}
