package com.exam.ex.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.core.constant.CoreConstant;
import com.exam.core.constant.DictEnum;
import com.exam.core.constant.SelectEnum;
import com.exam.ex.mapper.DictMapper;
import com.exam.ex.pojo.DictDO;
import com.exam.core.pojo.Page;
import com.exam.ex.pojo.TeacherDO;
import com.exam.ex.service.DictService;
import com.exam.core.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 数据字典表
 * college-学院
 * major-专业
 * job-职务
 * title-职称
 * subject-科目 服务实现类
 * </p>
 *
 * @author 杨德石
 * @since 2019-03-28
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, DictDO> implements DictService {

    @Autowired
    private DictMapper dictMapper;

    private final String dictKey = "dictType";

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    @Override
    public Page<DictDO> getListByPage(Page<DictDO> page) {
        // 处理参数
        page.filterParams();
        TeacherDO loginTeacher = ShiroUtils.getLoginTeacher();
        if(!SelectEnum.SELECT_ALL.getCode().equals(loginTeacher.getTeacherOrg()) &&
                DictEnum.MAJOR.getCode().equals(page.getParams().get(dictKey).toString())) {
            // 不是查询所有，并且是查询专业，那就只能查看自己学院的专业
            page.getParams().put("orgCollege", loginTeacher.getTeacherCollege());
        }
        // 设置每页显示条数
        if (page.getCurrentCount() == null) {
            page.setCurrentCount(CoreConstant.CURRENT_COUNT);
        }
        // 计算索引
        Integer index = (page.getCurrentPage() - 1) * page.getCurrentCount();
        page.setIndex(index);
        // 查询每页数据
        List<DictDO> list = dictMapper.getListByPage(page);
        page.setList(list);
        Integer totalCount = dictMapper.getCountByPage(page);
        page.setTotalCount(totalCount);
        // 计算总页数
        page.setTotalPage((int) Math.ceil((page.getTotalCount() * 1.0) / page.getCurrentCount()));
        return page;
    }

    /**
     * 根据父级id查询
     * @param fatherId
     * @return
     */
    @Override
    public List<DictDO> getByFather(String fatherId) {
        return dictMapper.getByFather(fatherId);
    }
}
