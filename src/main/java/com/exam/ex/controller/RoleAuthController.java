package com.exam.ex.controller;

import com.exam.core.constant.ResultEnum;
import com.exam.ex.pojo.AuthDO;
import com.exam.ex.pojo.RoleAuthDO;
import com.exam.ex.service.RoleAuthService;
import com.exam.core.utils.Result;
import com.google.common.collect.Lists;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色-权限表 前端控制器
 * </p>
 *
 * @author 杨德石
 * @since 2019-04-01
 */
@RestController
@RequestMapping("/roleAuth")
public class RoleAuthController {

    @Autowired
    private RoleAuthService roleAuthService;

    /**
     * 根据角色id查询拥有的权限
     */
    @RequestMapping(value = "/authList/{roleId}", method = RequestMethod.GET)
    public Result roleList(@PathVariable String roleId) {
        try {
            List<String> roleIds = Lists.newArrayList();
            roleIds.add(roleId);
            List<AuthDO> list = roleAuthService.getByRoleIds(roleIds);
            // Lambda表达式取出集合中指定元素封装成另一个集合
            List<String> idList = list.stream().map(AuthDO::getAuthId).collect(Collectors.toList());
            return Result.ok(idList);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "查询失败！");
        }
    }

    /**
     * 为角色授权
     * 逻辑：类似于继承
     * 子角色拥有自己单独的权限
     * 父角色有的权限都会被子角色继承
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @RequiresPermissions("ar:role:auth")
    public Result update(@RequestBody List<RoleAuthDO> list) {
        try {
            roleAuthService.addAuth(list);
            return Result.ok("修改成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "修改失败！");
        }
    }

}

