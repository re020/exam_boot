package com.exam.ts.service;

import com.exam.core.pojo.Page;
import com.exam.ts.pojo.RoomDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 教室/考场表 服务类
 * </p>
 *
 * @author 杨德石
 * @since 2019-05-24
 */
public interface RoomService extends IService<RoomDO> {

    /**
     * 添加教室
     * @param room
     */
    void addRoom(RoomDO room);

    /**
     * 分页查询
     * @param page
     * @return
     */
    Page<RoomDO> getByPage(Page<RoomDO> page);
}
