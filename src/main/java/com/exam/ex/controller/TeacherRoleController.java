package com.exam.ex.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exam.core.constant.ResultEnum;
import com.exam.ex.pojo.TeacherRoleDO;
import com.exam.ex.service.TeacherRoleService;
import com.exam.core.utils.IdWorker;
import com.exam.core.utils.Result;
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
 * 教师-角色表 前端控制器
 * </p>
 *
 * @author 杨德石
 * @since 2019-04-05
 */
@RestController
@RequestMapping("/teacherRole")
public class TeacherRoleController {

    @Autowired
    private TeacherRoleService teacherRoleService;
    @Autowired
    private IdWorker idWorker;

    /**
     * 根据教师id查询拥有的角色
     */
    @RequestMapping(value = "/roleList/{teacherId}", method = RequestMethod.GET)
    public Result roleList(@PathVariable String teacherId) {
        try {
            QueryWrapper<TeacherRoleDO> wrapper = new QueryWrapper<TeacherRoleDO>()
                    .eq("tr_teacher", teacherId);
            List<TeacherRoleDO> list = teacherRoleService.list(wrapper);
            // Lambda表达式取出集合中指定元素封装成另一个集合
            List<String> idList = list.stream().map(TeacherRoleDO::getTrRole).collect(Collectors.toList());
            return Result.ok(idList);
        } catch (Exception e) {
            return Result.build(ResultEnum.ERROR.getCode(), "查询失败！");
        }
    }

    /**
     * 修改角色
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @RequiresPermissions("user:teacher:role")
    public Result update(@RequestBody List<TeacherRoleDO> list) {
        try {
            teacherRoleService.addRole(list);
            return Result.ok("修改成功！");
        } catch (Exception e) {
            return Result.build(ResultEnum.ERROR.getCode(), "修改失败！");
        }
    }

}

