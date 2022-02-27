package com.exam.ts.controller;


import com.exam.core.utils.Result;
import com.exam.ex.pojo.PaperDO;
import com.exam.ex.pojo.QuestionDO;
import com.exam.ts.service.StudentPaperConfigQuestionService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 学生试卷配置-题目表 前端控制器
 * </p>
 *
 * @author 杨德石
 * @since 2019-05-24
 */
@Controller
@RequestMapping("/studentPaperConfigQuestionDO")
public class StudentPaperConfigQuestionController {

    @Autowired
    private StudentPaperConfigQuestionService studentPaperConfigQuestionService;



}

