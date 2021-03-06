package com.exam.ex.controller;


import com.exam.core.constant.ResultEnum;
import com.exam.core.constant.TypeEnum;
import com.exam.core.pojo.Page;
import com.exam.ex.pojo.TypeDO;
import com.exam.ex.service.TypeService;
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
 * 题型表 前端控制器
 * </p>
 *
 * @author 杨德石
 * @since 2019-03-28
 */
@RestController
@RequestMapping("/type")
public class TypeController {

    @Autowired
    private TypeService typeService;
    @Autowired
    private IdWorker idWorker;

    /**
     * 新增题型
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @RequiresPermissions("type:add")
    public Result add(@RequestBody TypeDO type) {
        try {
            type.setTypeId(idWorker.nextId() + "");
            typeService.save(type);
            return Result.ok("添加成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "新增失败！");
        }
    }

    /**
     * 修改题型
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @RequiresPermissions("type:update")
    public Result update(@RequestBody TypeDO typeDO) {
        try {
            String typeId = typeDO.getTypeId();
            if (typeId.equals(TypeEnum.ONE_CHOICE.getCode().toString())
                    || typeId.equals(TypeEnum.MANY_CHOICE.getCode().toString())
                    || typeId.equals(TypeEnum.COMPLETION.getCode().toString())
                    || typeId.equals(TypeEnum.JUDGEMENT.getCode().toString())
                    || typeId.equals(TypeEnum.PROGRAMMING.getCode().toString())) {
                return Result.build(ResultEnum.ERROR.getCode(), "该题型为默认题型，禁止修改！");
            }
            typeService.updateById(typeDO);
            return Result.ok("修改成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "新增失败！");
        }
    }

    /**
     * 删除题型
     */
    @RequestMapping(value = "/delete/{typeId}", method = RequestMethod.DELETE)
    @RequiresPermissions("type:delete")
    public Result delete(@PathVariable String typeId) {
        try {
            if (typeId.equals(TypeEnum.ONE_CHOICE.getCode().toString())
                    || typeId.equals(TypeEnum.MANY_CHOICE.getCode().toString())
                    || typeId.equals(TypeEnum.COMPLETION.getCode().toString())
                    || typeId.equals(TypeEnum.JUDGEMENT.getCode().toString())
                    || typeId.equals(TypeEnum.PROGRAMMING.getCode().toString())) {
                return Result.build(ResultEnum.ERROR.getCode(), "该题型为默认题型，禁止删除！");
            }
            typeService.removeById(typeId);
            return Result.ok("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "新增失败！");
        }
    }


    /**
     * 分页查询
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @RequiresPermissions("type:list")
    public Result list(@RequestBody Page<TypeDO> page) {
        try {
            page = typeService.getListByPage(page);
            return Result.ok(page);
        } catch (Exception e) {
            return Result.build(ResultEnum.ERROR.getCode(), "查询失败！");
        }
    }

    /**
     * 查询所有
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Result getAll() {
        List<TypeDO> list = typeService.list();
        return Result.ok(list);
    }

    /**
     * 根据知识点id查询所有
     */
    @RequestMapping(value = "/all/{knowId}", method = RequestMethod.GET)
    public Result getAll(@PathVariable String knowId) {
        List<TypeDO> list = typeService.getByKnowId(knowId);
        return Result.ok(list);
    }

    /**
     * 根据知识点id数组查询所有
     */
    @RequestMapping(value = "/all", method = RequestMethod.POST)
    public Result getAll(@RequestBody List<String> knowIds) {
        List<TypeDO> list = typeService.getByKnowIds(knowIds);
        return Result.ok(list);
    }

    /**
     * 根据id查询
     */
    @RequestMapping(value = "/get/{typeId}", method = RequestMethod.GET)
    public Result get(@PathVariable String typeId) {
        TypeDO typeDO = typeService.getById(typeId);
        return Result.ok(typeDO);
    }

    /**
     * 根据试卷id查询试卷的所有题型
     */


}