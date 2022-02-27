package com.exam.ts.controller;


import com.exam.core.constant.ResultEnum;
import com.exam.core.exception.ExamException;
import com.exam.core.utils.Result;
import com.exam.ts.pojo.DTO.QuestionDTO;
import com.exam.ts.service.StudentPaperConfigSubScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 学生-试卷-每个题型-主观题得分表（一题一分） 前端控制器
 * </p>
 *
 * @author 杨德石
 * @since 2019-05-24
 */
@RestController
@RequestMapping("/studentPaperConfigSubScoreDO")
public class StudentPaperConfigSubScoreController {

    @Autowired
    private StudentPaperConfigSubScoreService studentPaperConfigSubScoreService;

    /**
     * 老师批改学生题目，单个题目
     */
    @RequestMapping(value = "/correct",method = RequestMethod.POST)
    public Result corrent(@RequestBody QuestionDTO questionDTO){
        try {
            studentPaperConfigSubScoreService.correctQuestion(questionDTO);
            return Result.ok("提交完毕");
        } catch (ExamException e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "提交失败"+e.getMessage());
        }
    }

}

