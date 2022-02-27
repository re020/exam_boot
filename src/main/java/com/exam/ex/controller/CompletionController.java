package com.exam.ex.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exam.core.constant.ResultEnum;
import com.exam.ex.pojo.CompletionAnswerDO;
import com.exam.ex.pojo.CompletionDO;
import com.exam.core.pojo.Page;
import com.exam.ex.service.CompletionAnswerService;
import com.exam.ex.service.CompletionService;
import com.exam.core.utils.IdWorker;
import com.exam.core.utils.Result;
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
 * 填空题表 前端控制器
 * </p>
 *
 * @author 杨德石
 * @since 2019-03-28
 */
@RestController
@RequestMapping("/completion")
public class CompletionController {

    @Autowired
    private CompletionService completionService;
    @Autowired
    private CompletionAnswerService completionAnswerService;
    @Autowired
    private IdWorker idWorker;

    /**
     * 添加填空题
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @RequiresPermissions("question:add")
    public Result add(@RequestBody CompletionDO completion) {
        try {
            Result result = completionService.saveOrUpdateCompletion(completion);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "添加失败！");
        }
    }

    /**
     * 根据id查询
     */
    @RequestMapping(value = "/get/{compId}", method = RequestMethod.GET)
    public Result get(@PathVariable String compId) {
        try {
            CompletionDO completionDO = completionService.getById(compId);
            // 查询所有的答案
            String comId = completionDO.getCompId();
            List<CompletionAnswerDO> answerDOList = completionAnswerService.list(new QueryWrapper<CompletionAnswerDO>().eq("answer_comp", comId));
            completionDO.setAnswerList(answerDOList);
            return Result.ok(completionDO);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "查询失败！");
        }
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @RequiresPermissions("question:update")
    public Result update(@RequestBody CompletionDO completionDO) {
        try {
            Result result = completionService.saveOrUpdateCompletion(completionDO);
            return result;
        } catch (Exception e) {
            e.printStackTrace();

            return Result.build(ResultEnum.ERROR.getCode(), "修改失败！");
        }
    }

    /**
     * 根据id删除
     */
    @RequestMapping(value = "/delete/{compId}", method = RequestMethod.DELETE)
    @RequiresPermissions("question:delete")
    public Result delete(@PathVariable String compId) {
        try {
            completionService.removeById(compId);
            return Result.ok("删除成功！");
        }catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "删除失败！");
        }
    }

    /**
     * 分页查询
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @RequiresPermissions("question:list")
    public Result list(@RequestBody Page<CompletionDO> page) {
        try {
            page = completionService.getByPage(page);
            return  Result.ok(page);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "查询失败！");
        }
    }
}


