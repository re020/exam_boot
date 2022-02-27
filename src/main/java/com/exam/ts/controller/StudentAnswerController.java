package com.exam.ts.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.exam.core.exception.ExamException;
import com.exam.core.utils.Result;
import com.exam.ts.pojo.StudentAnswerDO;
import com.exam.ts.pojo.DTO.AnswerDTO;
import com.exam.ts.pojo.DTO.TopicDTO;
import com.exam.ts.service.StudentAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 学生做题答案表 前端控制器
 * </p>
 *
 * @author 杨德石
 * @since 2019-05-24
 */
@RestController
@RequestMapping("/studentAnswerDO")
public class StudentAnswerController {

    @Autowired
    private StudentAnswerService studentAnswerService;

    /**
     * 进行单个题目的答案提交
     */
    @PostMapping("/issue")
    public Result issue(@RequestBody  StudentAnswerDO answerDO){
        studentAnswerService.saveIssue(answerDO);
        return Result.ok("该题回答完成!");
    }




}

