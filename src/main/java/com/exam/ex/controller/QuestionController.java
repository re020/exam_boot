package com.exam.ex.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exam.core.constant.ResultEnum;
import com.exam.core.pojo.Page;
import com.exam.ex.pojo.QuestionAnswerDO;
import com.exam.ex.pojo.QuestionDO;
import com.exam.ex.service.QuestionAnswerService;
import com.exam.ex.service.QuestionService;
import com.exam.core.utils.Result;
import com.exam.ex.vo.QuestionVO;
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
 * 其他题型 前端控制器
 * </p>
 *
 * @author 杨德石
 * @since 2019-04-12
 */
@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuestionAnswerService questionAnswerService;

    /**
     * 添加其他题
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @RequiresPermissions("question:add")
    public Result add(@RequestBody QuestionDO question) {
        try {
            if (question.getAnswerList().isEmpty()) {
                return Result.build(ResultEnum.ERROR.getCode(), "大题至少要有一个小问！");
            }
            questionService.saveQuestion(question);
            return Result.ok("添加成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "添加失败！");
        }
    }

    /**
     * 修改其他题
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @RequiresPermissions("question:update")
    public Result update(@RequestBody QuestionDO question) {
        try {
            if (question.getAnswerList().isEmpty()) {
                return Result.build(ResultEnum.ERROR.getCode(), "大题至少要有一个小问！");
            }
            questionService.updateQuestion(question);
            return Result.ok("修改成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "修改失败！");
        }
    }

    /**
     * 根据id查询
     */
    @RequestMapping(value = "/get/{questionId}", method = RequestMethod.GET)
    public Result get(@PathVariable String questionId) {
        try {
            QuestionDO question = questionService.getById(questionId);
            List<QuestionAnswerDO> answerDOList = questionAnswerService.list(new QueryWrapper<QuestionAnswerDO>().eq("answer_question", questionId));
            question.setAnswerList(answerDOList);
            return Result.ok(question);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "查询失败！");
        }
    }

    /**
     * 根据id删除
     */
    @RequestMapping(value = "/delete/{questionId}", method = RequestMethod.DELETE)
    @RequiresPermissions("question:delete")
    public Result delete(@PathVariable String questionId) {
        try {
            questionService.removeById(questionId);
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
    @RequiresPermissions("question:list")
    public Result list(@RequestBody Page<QuestionDO> page) {
        try {
            page = questionService.getByPage(page);
            return Result.ok(page);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "查询失败！");
        }
    }

    /**
     * 组卷分页查找题目
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result search(@RequestBody Page<QuestionVO> voPage) {
        try {
            voPage = questionService.getVoByPage(voPage);
            return Result.ok(voPage);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "查询失败！");
        }
    }

}

