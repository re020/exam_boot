package com.exam.ex.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.core.constant.CoreConstant;
import com.exam.ex.mapper.TypeMapper;
import com.exam.ex.pojo.BankTypeDO;
import com.exam.core.pojo.Page;
import com.exam.ex.pojo.TypeDO;
import com.exam.ex.service.BankTypeService;
import com.exam.ex.service.TypeService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 题型表 服务实现类
 * </p>
 *
 * @author 杨德石
 * @since 2019-03-28
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, TypeDO> implements TypeService {

    @Autowired
    private TypeMapper typeMapper;
    @Autowired
    private BankTypeService bankTypeService;

    /**
     * 分页查询
     * @param page
     * @return
     */
    @Override
    public Page<TypeDO> getListByPage(Page<TypeDO> page) {
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
        List<TypeDO> list = typeMapper.getListByPage(page);
        page.setList(list);
        Integer totalCount = typeMapper.getCountByPage(page);
        page.setTotalCount(totalCount);
        // 计算总页数
        page.setTotalPage((int) Math.ceil((page.getTotalCount() * 1.0) / page.getCurrentCount()));
        return page;
    }

    /**
     * 根据知识点id查询所有
     * @param knowId
     * @return
     */
    @Override
    public List<TypeDO> getByKnowId(String knowId) {
        List<BankTypeDO> bankTypeList = bankTypeService.list(new QueryWrapper<BankTypeDO>().eq("bank_know", knowId));
        if(bankTypeList.isEmpty()) {
            return Lists.newArrayList();
        }
        List<String> typeIds = bankTypeList.stream().map(BankTypeDO::getBankType).collect(Collectors.toList());
        return typeMapper.selectBatchIds(typeIds);
    }

    /**
     * 根据知识点数组查询所有
     * @param knowIds
     * @return
     */
    @Override
    public List<TypeDO> getByKnowIds(List<String> knowIds) {
        List<BankTypeDO> bankTypeList = bankTypeService.list(new QueryWrapper<BankTypeDO>().in("bank_know", knowIds));
        if(bankTypeList.isEmpty()) {
            return Lists.newArrayList();
        }
        List<String> typeIds = bankTypeList.stream().map(BankTypeDO::getBankType).collect(Collectors.toList());
        return typeMapper.selectBatchIds(typeIds);
    }
}
