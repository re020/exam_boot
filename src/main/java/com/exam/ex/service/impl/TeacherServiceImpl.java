package com.exam.ex.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.core.constant.CoreConstant;
import com.exam.core.constant.SelectEnum;
import com.exam.ex.mapper.TeacherMapper;
import com.exam.core.pojo.Page;
import com.exam.ex.pojo.TeacherDO;
import com.exam.ex.service.TeacherService;
import com.exam.core.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 教师表 服务实现类
 * </p>
 *
 * @author 杨德石
 * @since 2019-03-31
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, TeacherDO> implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public Page<TeacherDO> getByPage(Page<TeacherDO> page) {
        // 处理参数
        page.filterParams();
        TeacherDO loginTeacher = ShiroUtils.getLoginTeacher();
        if(!SelectEnum.SELECT_ALL.getCode().equals(loginTeacher.getTeacherOrg())) {
            // 不是查询所有，就只查询自己学院的
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
        List<TeacherDO> list = teacherMapper.getListByPage(page);
        page.setList(list);
        Integer totalCount = teacherMapper.getCountByPage(page);
        page.setTotalCount(totalCount);
        // 计算总页数
        page.setTotalPage((int) Math.ceil((page.getTotalCount() * 1.0) / page.getCurrentCount()));
        return page;
    }

    /**
     * 全部重置密码
     */
    @Override
    public void resetPwdAll() {
        teacherMapper.resetPwdAll();
    }

    /**
     * 更新年龄，+1
     */
    @Override
    public void updateAllAge() {
        teacherMapper.updateAllAge();
    }

    /**
     * 查询所有不在监考的空闲教师
     * @return
     */
    @Override
    public List<TeacherDO> freeList() {
        return teacherMapper.freeList();
    }

}
