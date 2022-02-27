package com.exam.ex.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.core.constant.CoreConstant;
import com.exam.core.constant.SelectEnum;
import com.exam.ex.mapper.StudentMapper;
import com.exam.core.pojo.Page;
import com.exam.ex.pojo.StudentDO;
import com.exam.ex.pojo.TeacherDO;
import com.exam.ex.service.StudentService;
import com.exam.core.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.util.List;

/**
 * <p>
 * 学生表 服务实现类
 * </p>
 *
 * @author 杨德石
 * @since 2019-03-28
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, StudentDO> implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    /**
     * 分页查询
     * @param page
     * @return
     */
    @Override
    public Page<StudentDO> getByPage(Page<StudentDO> page) {
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
        List<StudentDO> list = studentMapper.getListByPage(page);
        page.setList(list);
        Integer totalCount = studentMapper.getCountByPage(page);
        page.setTotalCount(totalCount);
        // 计算总页数
        page.setTotalPage((int) Math.ceil((page.getTotalCount() * 1.0) / page.getCurrentCount()));
        return page;
    }

    /**
     * 重置所有人密码
     */
    @Override
    public void resetPwdAll() {
        studentMapper.resetPwdAll();
    }

    /**
     * 更新全部年龄
     */
    @Override
    public void updateAllAge() {
        studentMapper.updateAllAge();
    }

}
