package com.exam.ex.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exam.core.constant.ResultEnum;
import com.exam.core.constant.UserType;
import com.exam.core.realm.CustomLoginToken;
import com.exam.ex.pojo.AuthDO;
import com.exam.core.pojo.Page;
import com.exam.ex.pojo.PwdDO;
import com.exam.ex.pojo.RoleDO;
import com.exam.ex.pojo.TeacherDO;
import com.exam.ex.pojo.TeacherRoleDO;
import com.exam.ex.service.PwdService;
import com.exam.ex.service.RoleAuthService;
import com.exam.ex.service.RoleService;
import com.exam.ex.service.TeacherRoleService;
import com.exam.ex.service.TeacherService;
import com.exam.core.utils.IdWorker;
import com.exam.core.utils.Md5Utils;
import com.exam.core.utils.Result;
import com.exam.core.utils.ShiroUtils;
import com.exam.core.utils.StringUtils;
import com.google.common.collect.Maps;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 教师表 前端控制器
 * </p>
 *
 * @author 杨德石
 * @since 2019-03-31
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private PwdService pwdService;
    @Autowired
    private TeacherRoleService teacherRoleService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleAuthService roleAuthService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 教师登录
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody TeacherDO teacherDO) {
        // 使用shiro框架进行认证
        // 获取当前用户对象，状态为“未认证”
        Subject subject = SecurityUtils.getSubject();
        AuthenticationToken token = new CustomLoginToken(teacherDO.getTeacherUsername(),Md5Utils.toMD5(teacherDO.getTeacherPassword()), UserType.TEACHER.getCode());
        try {
            subject.login(token);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "用户名或密码错误！");
        }
        // 查询登录成功的数据，放到redis中
        TeacherDO teacher = (TeacherDO) subject.getPrincipal();
        Serializable sessionId = subject.getSession().getId();
        Map<String, Object> dataMap = Maps.newHashMap();
        dataMap.put("token", "tea" + sessionId);
        dataMap.put("teacher", teacher);
        redisTemplate.opsForValue().set(teacher.getTeacherUsername(), sessionId);
        return Result.ok("登陆成功！", dataMap);
    }

    /**
     * 添加教师
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @RequiresPermissions("user:teacher:add")
    public Result add(@RequestBody TeacherDO teacherDO) {
        try {
            // 根据工号、账号、身份证号分别查询一次，这几列都是唯一的
            QueryWrapper<TeacherDO> wrapper = new QueryWrapper<TeacherDO>()
                    .eq("teacher_number", teacherDO.getTeacherNumber());
            TeacherDO byNumber = teacherService.getOne(wrapper);
            if (byNumber != null) {
                return Result.build(ResultEnum.ERROR.getCode(), "工号已存在！");
            }
            wrapper = new QueryWrapper<TeacherDO>()
                    .eq("teacher_card", teacherDO.getTeacherCard());
            TeacherDO byCard = teacherService.getOne(wrapper);
            if (byCard != null) {
                return Result.build(ResultEnum.ERROR.getCode(), "身份证号已存在！");
            }
            wrapper = new QueryWrapper<TeacherDO>()
                    .eq("teacher_username", teacherDO.getTeacherUsername());
            TeacherDO byUsername = teacherService.getOne(wrapper);
            if (byUsername != null) {
                return Result.build(ResultEnum.ERROR.getCode(), "用户名已存在！");
            }
            // 设置属性
            String teacherId = idWorker.nextId() + "";
            teacherDO.setTeacherId(teacherId);
            teacherDO.setTeacherUsername(teacherDO.getTeacherNumber());
            String plaintext = teacherDO.getTeacherPassword();
            String ciphertext = Md5Utils.toMD5(plaintext);
            teacherDO.setTeacherPassword(ciphertext);
            teacherService.save(teacherDO);

            // 在密码表里添加一套密码对应关系，防止忘记密码。
            pwdService.saveOrUpdate(new PwdDO(teacherId, plaintext, ciphertext));

        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "添加失败！");
        }
        return Result.ok("添加成功！");
    }

    /**
     * 修改教师
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @RequiresPermissions("user:teacher:update")
    public Result update(@RequestBody TeacherDO teacherDO) {
        try {
            TeacherDO teacher = teacherService.getById(teacherDO.getTeacherId());

            // 根据工号、账号、身份证号分别查询一次，这几列都是唯一的
            QueryWrapper<TeacherDO> wrapper = new QueryWrapper<>();
            wrapper.eq("teacher_number", teacherDO.getTeacherNumber());
            TeacherDO byNumber = teacherService.getOne(wrapper);
            if (!teacher.getTeacherNumber().equals(teacherDO.getTeacherNumber()) && byNumber != null) {
                return Result.build(ResultEnum.ERROR.getCode(), "工号已存在！");
            }
            wrapper = new QueryWrapper<>();
            wrapper.eq("teacher_card", teacherDO.getTeacherCard());
            TeacherDO byCard = teacherService.getOne(wrapper);
            if (!teacher.getTeacherCard().equals(teacherDO.getTeacherCard()) && byCard != null) {
                return Result.build(ResultEnum.ERROR.getCode(), "身份证号已存在！");
            }
            wrapper = new QueryWrapper<>();
            wrapper.eq("teacher_username", teacherDO.getTeacherUsername());
            TeacherDO byUsername = teacherService.getOne(wrapper);
            if (!teacher.getTeacherUsername().equals(teacherDO.getTeacherUsername()) && byUsername != null) {
                return Result.build(ResultEnum.ERROR.getCode(), "用户名已存在！");
            }

            String plaintext = teacherDO.getTeacherPassword();
            if (!plaintext.equals(teacher.getTeacherPassword())) {
                // 密码不一样，就加密
                String ciphertext = Md5Utils.toMD5(plaintext);
                teacherDO.setTeacherPassword(ciphertext);
                // 修改对应的密码表
                pwdService.saveOrUpdate(new PwdDO(teacherDO.getTeacherId(), plaintext, ciphertext));
            }
            teacherService.updateById(teacherDO);

        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "修改失败！");
        }
        return Result.ok("修改成功！");
    }

    /**
     * 根据id查询教师
     */
    @RequestMapping(value = "/get/{teacherId}", method = RequestMethod.GET)
    public Result get(@PathVariable String teacherId) {
        try {
            TeacherDO teacherDO = teacherService.getById(teacherId);
            return Result.ok(teacherDO);
        } catch (Exception e) {
            return Result.build(ResultEnum.ERROR.getCode(), "查询失败！");
        }
    }

    /**
     * 根据id删除
     */
    @RequestMapping(value = "/delete/{teacherId}", method = RequestMethod.DELETE)
    @RequiresPermissions("user:teacher:delete")
    public Result delete(@PathVariable String teacherId) {
        try {
            teacherService.removeById(teacherId);
            return Result.ok("删除成功！");
        } catch (Exception e) {
            return Result.build(ResultEnum.ERROR.getCode(), "删除失败！");
        }
    }

    /**
     * 分页查询教师
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @RequiresPermissions("user:teacher:list")
    public Result list(@RequestBody Page<TeacherDO> page) {
        try {
            page = teacherService.getByPage(page);
            return Result.ok(page);
        } catch (Exception e) {
            return Result.build(ResultEnum.ERROR.getCode(), "查询失败！");
        }
    }

    /**
     * 重置密码
     */
    @RequestMapping(value = "/resetPwd", method = RequestMethod.PUT)
    public Result resetPwd(@RequestBody List<String> teacherIds) {
        try {
            List<TeacherDO> teacherDOList = (List<TeacherDO>) teacherService.listByIds(teacherIds);
            for (TeacherDO teacherDO : teacherDOList) {
                // 获取身份证号码
                String teacherCard = teacherDO.getTeacherCard();
                teacherCard = teacherCard.substring(teacherCard.length() - 6);

                String ciphertext = Md5Utils.toMD5(teacherCard);
                teacherDO.setTeacherPassword(ciphertext);
                teacherService.updateById(teacherDO);

            }

            teacherService.updateBatchById(teacherDOList);
            return Result.ok("重置成功！新密码为身份证号后6位");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "重置失败！请检查身份证号是否合法！");
        }
    }

    /**
     * 修改密码
     */
    @RequestMapping(value = "/rePwd", method = RequestMethod.PUT)
    public Result rePwd(@RequestBody Map<String, String> map) {
        try {
            TeacherDO loginTeacher = ShiroUtils.getLoginTeacher();
            String oldPassword = map.get("oldPassword");
            if (!loginTeacher.getTeacherPassword().equals(Md5Utils.toMD5(oldPassword))) {
                // 原密码错误
                return Result.build(ResultEnum.ERROR.getCode(), "原密码错误！");
            }
            String newPassword = map.get("newPassword");
            String rePassword = map.get("rePassword");
            if (StringUtils.isBlank(newPassword) || !newPassword.equals(rePassword)) {
                // 原密码错误
                return Result.build(ResultEnum.ERROR.getCode(), "两次密码不一致！");
            }
            loginTeacher.setTeacherPassword(Md5Utils.toMD5(newPassword));
            teacherService.updateById(loginTeacher);
            return Result.ok("修改成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "修改失败！");
        }
    }

    /**
     * 全部重置密码
     */
    @RequestMapping(value = "/resetAll", method = RequestMethod.GET)
    public Result resetPwd() {
        try {
            teacherService.resetPwdAll();
            return Result.ok("重置成功！新密码为身份证号后6位");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "操作失败！");
        }
    }

    /**
     * 根据token获取用户信息
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Result info() {
        try {
            TeacherDO teacherDO = ShiroUtils.getLoginTeacher();
            // 查询角色， 封装成集合
            List<TeacherRoleDO> roleList = teacherRoleService.getByTeacher(teacherDO);
            // Lambda表达式取出集合中指定元素封装成另一个集合
            List<String> roleIds = roleList.stream().map(TeacherRoleDO::getTrRole).collect(Collectors.toList());
            // 使用roleIds查询所有的角色，将角色名封装成集合
            List<String> roleNames = roleService.listByIds(roleIds).stream().map(RoleDO::getRoleName).collect(Collectors.toList());
            teacherDO.setRoleList(roleNames);

            // 根据roles查询权限
            List<AuthDO> authList = roleAuthService.getByRoleIds(roleIds);
            List<String> authCodes = authList.stream().map(AuthDO::getAuthCode).collect(Collectors.toList());
            teacherDO.setAuthList(authCodes);

            return Result.ok(teacherDO);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "您未登录，请登录");
        }
    }

    /**
     * 查询所有空闲的老师
     */
    @RequestMapping(value = "/freeList", method = RequestMethod.GET)
    public Result freeList() {
        try {
            List<TeacherDO> teacherDoList = teacherService.freeList();
            return Result.ok(teacherDoList);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "操作失败！");
        }
    }

}
