package com.exam.ex.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.core.constant.CoreConstant;
import com.exam.ex.dto.GaConfigDTO;
import com.exam.ex.mapper.TrueFalseMapper;
import com.exam.core.pojo.Page;
import com.exam.ex.pojo.TrueFalseDO;
import com.exam.ex.service.TrueFalseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 判断题表 服务实现类
 * </p>
 *
 * @author 杨德石
 * @since 2019-04-12
 */
@Service
public class TrueFalseServiceImpl extends ServiceImpl<TrueFalseMapper, TrueFalseDO> implements TrueFalseService {

    @Autowired
    private TrueFalseMapper trueFalseMapper;

    /**
     * 分页查询
     * @param page
     * @return
     */
    @Override
    public Page<TrueFalseDO> getByPage(Page<TrueFalseDO> page) {
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
        List<TrueFalseDO> list = trueFalseMapper.getListByPage(page);
        page.setList(list);
        Integer totalCount = trueFalseMapper.getCountByPage(page);
        page.setTotalCount(totalCount);
        // 计算总页数
        page.setTotalPage((int) Math.ceil((page.getTotalCount() * 1.0) / page.getCurrentCount()));
        return page;
    }

    /**
     * 遗传算法专用查询列表
     * @param configDTO
     * @return
     */
    @Override
    public List<TrueFalseDO> getGaList(GaConfigDTO configDTO) {
        return trueFalseMapper.getGaList(configDTO);
    }

    /**
     * 遗传算法专用变异查询
     * @param trueFalseDO
     * @return
     */
    @Override
    public List<TrueFalseDO> getMutateList(TrueFalseDO trueFalseDO, GaConfigDTO configDTO) {
        return trueFalseMapper.getMutateList(trueFalseDO, configDTO);
    }
}
