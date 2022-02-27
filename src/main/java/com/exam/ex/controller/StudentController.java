package com.exam.ex.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exam.core.constant.ResultEnum;
import com.exam.core.constant.UserType;
import com.exam.core.pojo.Page;
import com.exam.core.realm.CustomLoginToken;
import com.exam.ex.pojo.PwdDO;
import com.exam.ex.pojo.StudentDO;
import com.exam.ex.pojo.TeacherDO;
import com.exam.ex.service.PwdService;
import com.exam.ex.service.StudentService;
import com.exam.core.utils.IdWorker;
import com.exam.core.utils.Md5Utils;
import com.exam.core.utils.Result;
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

import javax.security.auth.login.LoginException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 学生表 前端控制器
 * </p>
 *
 * @author 杨德石
 * @since 2019-03-28
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private PwdService pwdService;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 新增学生
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @RequiresPermissions("user:student:add")
    public Result add(@RequestBody StudentDO studentDO) {
        try {

            // 根据学号和身份证号查询，这两个是唯一的
            QueryWrapper<StudentDO> wrapper = new QueryWrapper<StudentDO>()
                    .eq("stu_number", studentDO.getStuNumber());
            StudentDO byNumber = studentService.getOne(wrapper);
            if (byNumber != null) {
                return Result.build(ResultEnum.ERROR.getCode(), "学号已存在！");
            }

            wrapper = new QueryWrapper<StudentDO>()
                    .eq("stu_card", studentDO.getStuCard());
            StudentDO byCard = studentService.getOne(wrapper);
            if (byCard != null) {
                return Result.build(ResultEnum.ERROR.getCode(), "身份证号已存在！");
            }

            String stuId = idWorker.nextId() + "";
            studentDO.setStuId(stuId);
            String plaintext = studentDO.getStuPassword();
            String ciphertext = Md5Utils.toMD5(plaintext);
            studentDO.setStuPassword(ciphertext);
            studentService.save(studentDO);
            // 生成密码表
            pwdService.saveOrUpdate(new PwdDO(stuId, plaintext, ciphertext));
            return Result.ok("添加成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "添加失败！");
        }
    }

    /**
     * 修改学生
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @RequiresPermissions("user:student:update")
    public Result update(@RequestBody StudentDO studentDO) {
        try {
            String studentDOStuId = studentDO.getStuId();
            StudentDO student = studentService.getById(studentDOStuId);

            // 根据学号和身份证号查询，这两个是唯一的
            QueryWrapper<StudentDO> wrapper = new QueryWrapper<StudentDO>()
                    .eq("stu_number", studentDO.getStuNumber());
            StudentDO byNumber = studentService.getOne(wrapper);
            if (!student.getStuNumber().equals(studentDO.getStuNumber()) && byNumber != null) {
                return Result.build(ResultEnum.ERROR.getCode(), "学号已存在！");
            }

            wrapper = new QueryWrapper<StudentDO>()
                    .eq("stu_card", studentDO.getStuCard());
            StudentDO byCard = studentService.getOne(wrapper);
            if (!student.getStuCard().equals(studentDO.getStuCard()) && byCard != null) {
                return Result.build(ResultEnum.ERROR.getCode(), "身份证号已存在！");
            }

            String plaintext = studentDO.getStuPassword();
            if (!plaintext.equals(student.getStuPassword())) {
                String ciphertext = Md5Utils.toMD5(plaintext);
                studentDO.setStuPassword(ciphertext);
                // 修改密码表
                pwdService.saveOrUpdate(new PwdDO(studentDOStuId, plaintext, ciphertext));
            }
            studentService.updateById(studentDO);

            return Result.ok("修改成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "修改失败！");
        }
    }

    /**
     * 根据id查询
     */
    @RequestMapping(value = "/get/{studentId}", method = RequestMethod.GET)
    public Result get(@PathVariable String studentId) {
        try {
            StudentDO studentDO = studentService.getById(studentId);
            return Result.ok(studentDO);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "查询失败！");
        }
    }

    /**
     * 根据id删除
     */
    @RequestMapping(value = "/delete/{studentId}", method = RequestMethod.DELETE)
    @RequiresPermissions("user:student:delete")
    public Result delete(@PathVariable String studentId) {
        try {
            studentService.removeById(studentId);
            return Result.ok("删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "删除失败！");
        }
    }

    /**
     * 分页查询
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @RequiresPermissions("user:student:list")
    public Result list(@RequestBody Page<StudentDO> page) {
        try {
            page = studentService.getByPage(page);
            return Result.ok(page);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "查询失败！");
        }
    }

    /**
     * 重置密码
     */
    @RequestMapping(value = "/resetPwd", method = RequestMethod.PUT)
    public Result resetPwd(@RequestBody List<String> ids) {
        try {
            List<StudentDO> list = (List<StudentDO>) studentService.listByIds(ids);
            for (StudentDO student : list) {
                String stuNumber = student.getStuNumber();
                stuNumber = stuNumber.substring(stuNumber.length() - 6);

                String ciphertext = Md5Utils.toMD5(stuNumber);
                student.setStuPassword(ciphertext);
                studentService.updateById(student);
            }
            studentService.updateBatchById(list);
            return Result.ok("重置成功！新密码为学号后6位");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "重置失败！请检查学号是否正常！");
        }
    }

    /**
     * 全部重置密码
     */
    @RequestMapping(value = "/resetAll", method = RequestMethod.GET)
    public Result resetPwd() {
        try {
            studentService.resetPwdAll();
            return Result.ok("重置成功！新密码为学号后6位");
        }catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "操作失败！");
        }
    }

    /**
     * 学生登录
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody StudentDO studentDO) {
        // 使用shiro框架进行认证
        // 获取当前用户对象，状态为“未认证”
        Subject subject = SecurityUtils.getSubject();
        AuthenticationToken token = new CustomLoginToken(studentDO.getStuNumber(), Md5Utils.toMD5(studentDO.getStuPassword()), UserType.STUDENT.getCode());
        try {
            subject.login(token);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "用户名或密码错误！");
        }
        // 查询登录成功的数据，放到redis中
        StudentDO student = (StudentDO) subject.getPrincipal();
        Serializable sessionId = subject.getSession().getId();
        Map<String, Object> dataMap = Maps.newHashMap();
        dataMap.put("token", "stu" + sessionId);
        dataMap.put("student", student);
        redisTemplate.opsForValue().set(student.getStuNumber(), sessionId);
        return Result.ok("登陆成功！", dataMap);
    }
}

