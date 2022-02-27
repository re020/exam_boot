package com.exam.ts.controller;

import com.exam.core.constant.ResultEnum;
import com.exam.core.pojo.Page;
import com.exam.core.utils.Result;
import com.exam.core.utils.ShiroUtils;
import com.exam.ts.pojo.ExamStudentDO;
import com.exam.ts.service.ExamStudentService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 考试-学生对应表 前端控制器
 * </p>
 *
 * @author 杨德石
 * @since 2019-05-24
 */
@RestController
@RequestMapping("/examStudent")
public class ExamStudentController {

    @Autowired
    private ExamStudentService examStudentService;

    /**
     * 分页查询不在本场考试中的学生
     * 参数中examId必传
     */
    @RequestMapping(value = "/list" ,method = RequestMethod.POST)
    public Result list(@RequestBody Page<ExamStudentDO> page) {
        try {
            page = examStudentService.getByPage(page);
            return Result.ok(page);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "查询失败！");
        }
    }

    /**
     * 查询本场考试的所有学生
     */
    @RequestMapping(value = "/getList/{examId}", method = RequestMethod.GET)
    @RequiresPermissions("ex:exam:stu:list")
    public Result getList(@PathVariable String examId) {
        try {
            List<ExamStudentDO> list = examStudentService.getListByExam(examId);
            return Result.ok(list);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "查询失败！");
        }
    }

    /**
     * 添加单个学生进入考试中
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @RequiresPermissions("ex:exam:stu:add")
    public Result add(@RequestBody ExamStudentDO examStudentDO) {
        try {
            return examStudentService.checkAndSave(examStudentDO);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "添加失败！");
        }

    }

    /**
     * 批量添加学生进入考试中
     */
    @RequestMapping(value = "/addList/{examId}", method = RequestMethod.POST)
    @RequiresPermissions("ex:exam:stu:add")
    public Result addList(@PathVariable String examId, @RequestBody List<String> studentIds) {
        try {
            examStudentService.saveList(examId, studentIds);
            return Result.ok("添加成功！");
        }catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "添加失败！");
        }
    }

    /**
     * 将考生移出本场考试
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @RequiresPermissions("ex:exam:stu:delete")
    public Result delete(@PathVariable String id) {
        try {
            examStudentService.removeById(id);
            return Result.ok("删除成功！");
        }catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "删除失败！");
        }
    }

}

