package com.exam.ts.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.core.constant.CoreConstant;
import com.exam.core.constant.RoomEnum;
import com.exam.core.utils.IdWorker;
import com.exam.core.pojo.Page;
import com.exam.ts.mapper.RoomMapper;
import com.exam.ts.pojo.RoomDO;
import com.exam.ts.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 教室/考场表 服务实现类
 * </p>
 *
 * @author 杨德石
 * @since 2019-05-24
 */
@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, RoomDO> implements RoomService {

    @Autowired
    private IdWorker idWorker;
    @Autowired
    private RoomMapper roomMapper;

    /**
     * 添加教室
     * @param room
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addRoom(RoomDO room) {
        room.setRoomId(idWorker.nextId()+"");
        room.setRoomState(RoomEnum.FREE.getCode());
        roomMapper.insert(room);
    }

    /**
     * 分页查询
     * @param page
     * @return
     */
    @Override
    public Page<RoomDO> getByPage(Page<RoomDO> page) {
        // 处理参数
        page.filterParams();
        // 设置每页显示条数
        if (page.getCurrentCount() == null) {
            page.setCurrentCount(CoreConstant.CURRENT_COUNT);
        }
        // 计算索引
        Integer index = (page.getCurrentPage() - 1) * page.getCurrentCount();
        page.setIndex(index);
        // 查询每页数据
        List<RoomDO> list = roomMapper.getListByPage(page);
        page.setList(list);
        Integer totalCount = roomMapper.getCountByPage(page);
        page.setTotalCount(totalCount);
        // 计算总页数
        page.setTotalPage((int) Math.ceil((page.getTotalCount() * 1.0) / page.getCurrentCount()));
        return page;
    }
}
