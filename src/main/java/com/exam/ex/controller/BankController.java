package com.exam.ex.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exam.core.constant.ResultEnum;
import com.exam.core.constant.SelectEnum;
import com.exam.ex.pojo.BankDO;
import com.exam.core.pojo.Page;
import com.exam.ex.pojo.TeacherDO;
import com.exam.ex.service.BankService;
import com.exam.ex.service.DictService;
import com.exam.core.utils.IdWorker;
import com.exam.core.utils.Result;
import com.exam.core.utils.ShiroUtils;
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
 * 题库表 前端控制器
 * </p>
 *
 * @author 杨德石
 * @since 2019-03-28
 */
@RestController
@RequestMapping("/bank")
public class BankController {

    @Autowired
    private BankService bankService;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private DictService dictService;

    /**
     * 新增题库
     *
     * @param bankDO
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @RequiresPermissions("bank:add")
    public Result add(@RequestBody BankDO bankDO) {
        try {
            bankDO.setBankId(idWorker.nextId() + "");
            bankService.save(bankDO);
            return Result.ok("添加成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "添加失败！");
        }
    }

    /**
     * 更新题库
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @RequiresPermissions("bank:update")
    public Result update(@RequestBody BankDO bankDO) {
        try {
            bankService.updateById(bankDO);
            return Result.ok("更新成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "修改失败！");
        }
    }

    /**
     * 根据id删除
     */
    @RequestMapping(value = "/delete/{bankId}", method = RequestMethod.DELETE)
    @RequiresPermissions("bank:delete")
    public Result delete(@PathVariable String bankId) {
        try {
            bankService.removeById(bankId);
            return Result.ok("删除成功！");
        } catch (Exception e) {
            return Result.build(ResultEnum.ERROR.getCode(), "删除失败！");
        }
    }

    /**
     * 分页查询
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @RequiresPermissions("bank:list")
    public Result list(@RequestBody Page<BankDO> page) {
        try {
            page = bankService.getListByPage(page);
            return Result.ok(page);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "查询失败！");
        }
    }

    /**
     * 根据id查询
     */
    @RequestMapping(value = "/get/{bankId}", method = RequestMethod.GET)
    public Result get(@PathVariable String bankId) {
        BankDO bankDO = bankService.getById(bankId);
        // 查询对应的学院和科目
        bankDO.setCollege(dictService.getById(bankDO.getBankCollege()));
        bankDO.setSubject(dictService.getById(bankDO.getBankSubject()));
        return Result.ok(bankDO);
    }

    /**
     * 查询所有
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Result findAll() {
        try {
            QueryWrapper<BankDO> wrapper = new QueryWrapper<>();
            TeacherDO loginTeacher = ShiroUtils.getLoginTeacher();
            if(!SelectEnum.SELECT_ALL.getCode().equals(loginTeacher.getTeacherOrg())) {
                // 不是查询所有，就只查询自己学院的
                wrapper.eq("bank_college", loginTeacher.getTeacherCollege());
            }
            List<BankDO> list = bankService.list(wrapper);
            return Result.ok(list);
        } catch (Exception e) {
            return Result.build(ResultEnum.ERROR.getCode(), "查询失败！");
        }
    }

    /**
     * 根据学院查询
     */
    @RequestMapping(value = "/getByCollege/{collegeId}", method = RequestMethod.GET)
    public Result getByCollege(@PathVariable String collegeId) {
        QueryWrapper<BankDO> wrapper = new QueryWrapper<>();
        wrapper.eq("bank_college", collegeId);
        List<BankDO> list = bankService.list(wrapper);
        return Result.ok(list);
    }

    /**
     * 根据科目查询
     */
    @RequestMapping(value = "/getBySubject/{subjectId}", method = RequestMethod.GET)
    public Result getBySubject(@PathVariable String subjectId) {
        QueryWrapper<BankDO> wrapper = new QueryWrapper<>();
        wrapper.eq("bank_subject", subjectId);
        List<BankDO> list = bankService.list(wrapper);
        return Result.ok(list);
    }

}

