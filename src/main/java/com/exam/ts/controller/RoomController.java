package com.exam.ts.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exam.core.constant.ResultEnum;
import com.exam.core.constant.RoomEnum;
import com.exam.core.pojo.Page;
import com.exam.core.utils.Result;
import com.exam.core.utils.StringUtils;
import com.exam.ts.pojo.RoomDO;
import com.exam.ts.service.RoomService;
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
 * 教室/考场表 前端控制器
 * </p>
 *
 * @author 杨德石
 * @since 2019-05-24
 */
@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    /**
     * 添加教室
     *
     * @param room
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @RequiresPermissions("ex:room:add")
    public Result add(@RequestBody RoomDO room) {
        try {
            roomService.addRoom(room);
            return Result.ok("添加成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "添加失败！");
        }
    }

    /**
     * 修改教室
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @RequiresPermissions("ex:room:update")
    public Result update(@RequestBody RoomDO room) {
        try {
            roomService.updateById(room);
            return Result.ok("修改成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "修改失败！");
        }
    }

    /**
     * 删除教室
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @RequiresPermissions("ex:room:delete")
    public Result delete(@PathVariable String id) {
        try {
            roomService.removeById(id);
            return Result.ok("删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "删除失败！");
        }
    }

    /**
     * 查询所有未被占用的
     */
    @RequestMapping(value = "/freeList/{id}", method = RequestMethod.GET)
    public Result freeList(@PathVariable String id) {
        List<RoomDO> roomList;
        if (StringUtils.isBlank(id)) {
            roomList = roomService.list(new QueryWrapper<RoomDO>().eq("room_state", RoomEnum.FREE.getCode()));
        } else {
            roomList = roomService.list(new QueryWrapper<RoomDO>()
                    .eq("room_state", RoomEnum.FREE.getCode())
                    .or().eq("room_id", id));
        }
        return Result.ok(roomList);
    }

    /**
     * 查询全部
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Result all() {
        List<RoomDO> roomDOList = roomService.list();
        return Result.ok(roomDOList);
    }

    /**
     * 分页查询
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @RequiresPermissions("ex:room:list")
    public Result list(@RequestBody Page<RoomDO> page) {
        try {
            page = roomService.getByPage(page);
            return Result.ok(page);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "查询失败！");
        }
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public Result get(@PathVariable String id) {
        try {
            RoomDO room = roomService.getById(id);
            return Result.ok(room);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.ERROR.getCode(), "查询失败！");
        }
    }
}

