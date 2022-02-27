package com.exam.ex.service;

import com.exam.ex.pojo.BankDO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.core.pojo.Page;
import com.exam.ex.vo.BankVO;

import java.util.List;

/**
 * <p>
 * 题库表 服务类
 * </p>
 *
 * @author 杨德石
 * @since 2019-03-28
 */
public interface BankService extends IService<BankDO> {

    /**
     * 分页查询
     * @param page
     * @return
     */
    Page<BankDO> getListByPage(Page<BankDO> page);

    /**
     * 获取题库的基本信息：试卷数、题目数
     * @return
     */
    List<BankVO> getBankInfo();
}
