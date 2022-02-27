package com.exam.ex.controller;

import com.exam.ex.pojo.ChoiceDO;
import com.exam.ex.service.ChoiceService;
import com.exam.ex.service.ExpertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: 杨德石
 * @Date: 2019/5/18 0018 下午 12:44
 * @Version 1.0
 */
@Controller
@RequestMapping("/export")
public class ExportController {

    @Autowired
    private ExpertService expertService;
    @Autowired
    private ChoiceService choiceService;

    @RequestMapping("/paper")
    public void exportPaper(HttpServletRequest request, HttpServletResponse response) {
        ChoiceDO choiceDO = choiceService.getById("1128540528102432768");
        expertService.expertWord(request, response, "我是标题", choiceDO.getChoiceTitle());
    }

}
