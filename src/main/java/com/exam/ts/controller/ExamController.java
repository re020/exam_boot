package com.exam.ts.controller;

import com.exam.core.constant.ResultEnum;
import com.exam.core.exception.ExamException;
import com.exam.core.pojo.Page;
import com.exam.core.utils.DateUtils;
import com.exam.core.utils.Result;
import com.exam.core.utils.ShiroUtils;
import com.exam.ex.dto.GaPaperDTO;
import com.exam.ex.pojo.StudentDO;
import com.exam.ts.pojo.DTO.CommitDTO;
import com.exam.ts.pojo.ExamDO;
import com.exam.ts.pojo.ExamStudentDO;
import com.exam.ts.pojo.DTO.StudentDTO;
import com.exam.ts.pojo.StudentPaperDO;
import com.exam.ts.service.ExamService;
import com.exam.ts.service.ExamStudentService;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 考试表 前端控制器
 * </p>
 *
 * @author 杨德石
 * @since 2019-05-24
 */
@RestController
@RequestMapping("/exam")
public class ExamController {

    @Autowired
    private ExamService examService;
    @Autowired
    private ExamStudentService examStudentService;


    /**
     * 创建考试
     * 只创建基本信息，包括所用试卷
     *
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @RequiresPermissions("ex:exam:add")
    public Result createExam(@RequestBody ExamDO exam) {
        try {
            examService.addExam(exam);
            return Result.ok("操作成功！请通知学生及时考试！");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "操作失败！");
        }
    }

    /**
     * 根据id删除
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @RequiresPermissions("ex:exam:delete")
    public Result delete(@PathVariable String id) {
        try {
            examService.deleteExam(id);
            return Result.ok("删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "删除失败！");
        }
    }

    /**
     * 根据id获取
     */
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public Result get(@PathVariable String id) {
        try {
            ExamDO examDO = examService.getById(id);
            return Result.ok(examDO);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "查询失败！");
        }
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @RequiresPermissions("ex:exam:update")
    public Result update(@RequestBody ExamDO exam) {
        try {
            examService.updateExam(exam);
            return Result.ok("修改成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "修改失败！");
        }
    }

    /**
     * 分页查询
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @RequiresPermissions("ex:exam:list")
    public Result list(@RequestBody Page<ExamDO> page) {
        try {
            page = examService.getByPage(page);
            return Result.ok(page);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "查询失败！");
        }
    }

    /**
     * 查看考试信息
     */
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    @RequiresPermissions("ex:exam:info")
    public Result info(@PathVariable String id) {
        try {
            ExamDO examDO = examService.getInfo(id);
            return Result.ok(examDO);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "查询失败！");
        }
    }

    /**
     * 生成试卷
     */
    @RequestMapping(value = "/createPaper", method = RequestMethod.POST)
    @RequiresPermissions("ex:exam:paper:create")
    public Result createPaper(@RequestBody GaPaperDTO paperDTO) {
        try {
            examService.createPaper(paperDTO);
            return Result.ok("生成成功！请及时通知各班学生参加上机考试！");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "部分学生生成失败，请手动生成！");
        }
    }

    /**
     * 生成当前时间
     */
    @GetMapping(value = "/currentTime")
    public Result getCurrentTime() {
        return new Result(DateUtils.newDateTime());
    }

    /**
     * 开始进行考试
     */
    @GetMapping(value = "/start/{examId}")
    public Result start(@PathVariable @NotNull String examId) {
        try {
            return new Result(examService.startExam(examId));
        } catch (ExamException e) {
            e.printStackTrace();
            return Result.build(e.getCode(),e.getMessage());
        }
    }


    /**
     * 获得学生考试的列表
     */
    @PostMapping(value = "/getList")
    @RequiresPermissions("paper:list")
    public Result getlist(@RequestBody Page<ExamDO> page) {
        // 根据用户的id进行筛选
        page = examStudentService.getList(page);
        return new Result(page);
    }


    /**
     * 提交试卷
     */
    @PostMapping(value = "/submit")
    public Result submit(@RequestBody CommitDTO commitDTO) {
        StudentDO loginStudent = ShiroUtils.getLoginStudent();
        commitDTO.setStuId(loginStudent.getStuId());
        try {
            examService.submit(commitDTO);
            return Result.ok("提交成功！");
        } catch (ExamException e) {
            e.printStackTrace();
            return Result.build(e.getCode(), e.getMessage());
        }
    }


    /**
     * 提交试卷（只含客观题）
     */
    @PostMapping(value = "/submit2")
    public Result submit_tmp(@RequestBody CommitDTO commitDTO) {
        StudentDO loginStudent = ShiroUtils.getLoginStudent();
        commitDTO.setStuId(loginStudent.getStuId());
        try {
            examService.submit_tmp(commitDTO);
            return Result.ok("提交成功！");
        } catch (ExamException e) {
            e.printStackTrace();
            return Result.build(e.getCode(), e.getMessage());
        }
    }
}

