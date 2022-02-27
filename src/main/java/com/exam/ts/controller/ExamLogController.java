package com.exam.ts.controller;


import com.exam.core.constant.ResultEnum;
import com.exam.core.pojo.Page;
import com.exam.core.utils.Result;
import com.exam.ex.pojo.BankDO;
import com.exam.ts.pojo.ExamLogDO;
import com.exam.ts.service.ExamLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 考试日志表 前端控制器
 * </p>
 *
 * @author 杨德石
 * @since 2019-05-24
 */
@RestController
@RequestMapping("/examLogDO")
public class ExamLogController {
    @Autowired
    private ExamLogService examLogService;

    @GetMapping("/add/{examId}")
    @RequiresPermissions("paper:submit")
    public Result add(@PathVariable("examId") String examId) {
        try {
            examLogService.addExamLog(examId);
            return Result.ok("生成日志成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), e.getMessage());
        }
    }


    /**
     * 查看组卷
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @RequiresPermissions("paper:log:list")
    public Result list(@RequestBody Page<ExamLogDO> page) {
        try {
            page = examLogService.getListByPage(page);
            return Result.ok(page);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "查询失败！");
        }


    }
}

