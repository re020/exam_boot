package com.exam.ts.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.exam.core.utils.Result;
import com.exam.ts.pojo.StudentPaperConfigDO;
import com.exam.ts.service.StudentPaperConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 学生试卷配置表 前端控制器
 * </p>
 *
 * @author 杨德石
 * @since 2019-05-24
 */
@RestController
@RequestMapping("/studentPaperConfigDO")
public class StudentPaperConfigController {

    @Autowired
    private StudentPaperConfigService studentPaperConfigService;


    /**
     * 根据考试id得到题型对应的题目数
     */
    @RequestMapping(value = "/typeNum/{examId}/{stuId}", method = RequestMethod.GET)
    public Result getTypeNum(@PathVariable("examId") String examId,@PathVariable("stuId") String stuId) {
        List<StudentPaperConfigDO> data = studentPaperConfigService.getQuestionNum(examId,stuId);
        return new Result(data);
    }

}

