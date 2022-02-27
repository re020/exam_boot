package com.exam.ex.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exam.core.constant.ResultEnum;
import com.exam.ex.dto.GaPaperDTO;
import com.exam.core.pojo.Page;
import com.exam.ex.pojo.PaperConfigDO;
import com.exam.ex.pojo.PaperDO;
import com.exam.ex.service.PaperService;
import com.exam.core.utils.DateUtils;
import com.exam.core.utils.IdWorker;
import com.exam.core.utils.Result;
import com.exam.core.utils.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
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
 * 试卷表 前端控制器
 * </p>
 *
 * @author 杨德石
 * @since 2019-04-20
 */
@RestController
@RequestMapping("/paper")
public class PaperController {

    @Autowired
    private PaperService paperService;
    @Autowired
    private IdWorker idWorker;

    /**
     * 创建试卷
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @RequiresPermissions("paper:add")
    public Result add(@RequestBody PaperDO paper) {
        try {
            PaperDO paperDO = paperService.getOne(new QueryWrapper<PaperDO>().eq("paper_title", paper.getPaperTitle()));
            if (paperDO != null) {
                return Result.build(ResultEnum.ERROR.getCode(), "试卷标题已存在！");
            }

            // 补全属性
            paper.setPaperId(idWorker.nextId() + "");
            paper.setPaperCreateTime(DateUtils.newDate());
            paper.setPaperUpdateTime(DateUtils.newDate());
            paper.setPaperCreateBy(ShiroUtils.getLoginTeacher().getTeacherId());
            paperService.save(paper);
            return Result.ok("创建成功！请及时组卷！");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "创建失败！");
        }
    }

    /**
     * 根据id查询试卷基本信息
     */
    @RequestMapping(value = "/get/{paperId}", method = RequestMethod.GET)
    public Result get(@PathVariable String paperId) {
        try {
            PaperDO paper = paperService.getById(paperId);
            return Result.ok(paper);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "查询失败！");
        }
    }

    /**
     * 更新试卷信息
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @RequiresPermissions("paper:update")
    public Result update(@RequestBody PaperDO paper) {
        try {
            paper.setPaperUpdateTime(DateUtils.newDate());
            paperService.updateById(paper);
            return Result.ok("修改成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "修改失败！");
        }
    }

    /**
     * 根据id删除
     */
    @RequestMapping(value = "/delete/{paperId}", method = RequestMethod.DELETE)
    @RequiresPermissions("paper:delete")
    public Result delete(@PathVariable String paperId) {
        try {
            paperService.removeById(paperId);
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
    @RequiresPermissions("paper:list")
    public Result list(@RequestBody Page<PaperDO> page) {
        try {
            page = paperService.getByPage(page);
            return Result.ok(page);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "查询失败！");
        }
    }

    /**
     * 查询试卷的题目
     */
    @RequestMapping(value = "/questionList/{paperId}", method = RequestMethod.GET)
    @RequiresPermissions("paper:submit")
    public Result questionList(@PathVariable String paperId) {
        try {
            PaperDO paper = paperService.getQuestion(paperId);
            return Result.ok(paper);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "查询失败！");
        }
    }

    /**
     * 提交组卷请求，组卷完毕后，试卷将不可以修改
     *
     * @param paperId
     * @return
     */
    @RequestMapping(value = "/submit/{paperId}", method = RequestMethod.GET)
    @RequiresPermissions("paper:submit")
    public Result submit(@PathVariable String paperId) {
        try {
            paperService.submit(paperId);
            return Result.ok("提交成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "提交失败！");
        }
    }

    /**
     * 智能组卷
     * @param paperDTO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/gaSubmit", method = RequestMethod.POST)
    @RequiresPermissions("paper:submit")
    public Result gaSubmit(@RequestBody GaPaperDTO paperDTO) throws Exception {
        paperService.gaSubmitPaper(paperDTO);
        return Result.ok("组卷成功！");
    }

    /**
     * 查看试卷题型中的题目数
     */
    @RequestMapping(value = "/typeNum/{id}", method = RequestMethod.GET)
    public Result typeNum(@PathVariable String id) {
        try {
            List<PaperConfigDO> list = paperService.getTypeNum(id);
            return Result.ok(list);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "查看失败！");
        }
    }

    /**
     * 查询所有试卷
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Result getAll() {
        try {
            List<PaperDO> list = paperService.list();
            return Result.ok(list);
        }catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "查询失败！");
        }
    }

}

