package com.exam.ex.service;

import com.exam.core.pojo.Page;
import com.exam.ex.pojo.PaperLogDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 组卷日志表 服务类
 * </p>
 *
 * @author 杨德石
 * @since 2019-05-01
 */
public interface PaperLogService extends IService<PaperLogDO> {

    /**
     * 分页查询
     * @param page
     * @return
     */
    Page<PaperLogDO> getByPage(Page<PaperLogDO> page);
}
