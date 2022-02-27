package com.exam.ts.controller;

import com.exam.core.constant.ResultEnum;
import com.exam.core.utils.Result;
import com.exam.ex.service.TeacherService;
import com.exam.ts.pojo.ExamTeacherDO;
import com.exam.ts.service.ExamTeacherService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 考试-监考教师表 前端控制器
 * </p>
 *
 * @author 杨德石
 * @since 2019-05-24
 */
@RestController
@RequestMapping("/examTeacher")
public class ExamTeacherController {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private ExamTeacherService examTeacherService;

    /**
     * 为考试添加监考教师
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @RequiresPermissions("ex:insp:add")
    public Result add(@RequestBody ExamTeacherDO examTeacherDO) {
        try {
            return examTeacherService.checkAndSave(examTeacherDO);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "添加失败！");
        }
    }

    /**
     * 查看该考试的监考教师
     */
    @RequestMapping(value = "/getList/{examId}", method = RequestMethod.GET)
    @RequiresPermissions("ex:insp:list")
    public Result getList(@PathVariable String examId) {
        try {
            List<ExamTeacherDO> teacherList = examTeacherService.getByExamId(examId);
            return Result.ok(teacherList);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "查询失败！");
        }
    }

    /**
     * 移除监考
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @RequiresPermissions("ex:insp:delete")
    public Result delete(@PathVariable String id) {
        try {
            examTeacherService.removeById(id);
            return Result.ok("删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "删除失败！");
        }
    }

}

