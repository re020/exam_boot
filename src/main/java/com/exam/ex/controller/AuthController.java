package com.exam.ex.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exam.core.constant.ResultEnum;
import com.exam.ex.pojo.AuthDO;
import com.exam.ex.service.AuthService;
import com.exam.core.utils.Result;
import com.exam.core.utils.TreeUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 权限表 前端控制器
 * </p>
 *
 * @author 杨德石
 * @since 2019-04-01
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @RequestMapping(value = "/treeList", method = RequestMethod.GET)
    @RequiresPermissions("ar:auth:list")
    public Result treeList() {
        try {
            QueryWrapper<AuthDO> wrapper = new QueryWrapper<AuthDO>().orderByAsc("auth_index");
            List<AuthDO> list = authService.list(wrapper);
            List<AuthDO> authList = TreeUtils.getAuthList(list);
            return Result.ok(authList);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "查询失败");
        }
    }

}

