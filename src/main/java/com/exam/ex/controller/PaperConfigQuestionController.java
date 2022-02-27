package com.exam.ex.controller;

import com.exam.core.constant.ResultEnum;
import com.exam.ex.service.PaperService;
import com.exam.core.utils.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 试卷配置-题目表 前端控制器
 * </p>
 *
 * @author 杨德石
 * @since 2019-03-28
 */
@RestController
@RequestMapping("/paperConfigQuestion")
public class PaperConfigQuestionController {

    @Autowired
    private PaperService paperService;

    /**
     * 根据config和id删除
     * 因为后端没有给前端传id
     * 而 相同的config下，题目id只会有一个
     */
    @RequestMapping(value = "/delete/{paperId}/{questionId}", method = RequestMethod.DELETE)
    @RequiresPermissions("paper:submit")
    public Result delete(@PathVariable String paperId, @PathVariable String questionId) {
        try {
            paperService.deleteQuestion(paperId, questionId);
            return Result.ok("删除成功！");
        }catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "删除失败！");
        }
    }

}

