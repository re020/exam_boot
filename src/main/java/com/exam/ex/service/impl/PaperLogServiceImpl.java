package com.exam.ex.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.core.constant.CoreConstant;
import com.exam.core.constant.SelectEnum;
import com.exam.ex.mapper.PaperLogMapper;
import com.exam.core.pojo.Page;
import com.exam.ex.pojo.PaperLogDO;
import com.exam.ex.pojo.TeacherDO;
import com.exam.ex.service.PaperLogService;
import com.exam.core.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 组卷日志表 服务实现类
 * </p>
 *
 * @author 杨德石
 * @since 2019-05-01
 */
@Service
public class PaperLogServiceImpl extends ServiceImpl<PaperLogMapper, PaperLogDO> implements PaperLogService {

    @Autowired
    private PaperLogMapper paperLogMapper;

    @Override
    public Page<PaperLogDO> getByPage(Page<PaperLogDO> page) {
        // 处理参数
        page.filterParams();
        // 处理角色
        TeacherDO loginTeacher = ShiroUtils.getLoginTeacher();
        if (SelectEnum.SELECT_COLLEGE.getCode().equals(loginTeacher.getTeacherOrg())) {
            // 查询学院
            page.getParams().put("orgCollege", loginTeacher.getTeacherCollege());
        }
        if (SelectEnum.SELECT_SELF.getCode().equals(loginTeacher.getTeacherOrg())) {
            // 查询自己
            page.getParams().put("orgTeacher", loginTeacher.getTeacherId());
        }
        // 设置每页显示条数
        if (page.getCurrentCount() == null) {
            page.setCurrentCount(CoreConstant.CURRENT_COUNT);
        }
        // 计算索引
        Integer index = (page.getCurrentPage() - 1) * page.getCurrentCount();
        page.setIndex(index);
        // 查询每页数据
        List<PaperLogDO> list = paperLogMapper.getListByPage(page);
        page.setList(list);
        Integer totalCount = paperLogMapper.getCountByPage(page);
        page.setTotalCount(totalCount);
        // 计算总页数
        page.setTotalPage((int) Math.ceil((page.getTotalCount() * 1.0) / page.getCurrentCount()));
        return page;
    }
}
