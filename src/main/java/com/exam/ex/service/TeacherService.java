package com.exam.ex.service;

import com.exam.core.pojo.Page;
import com.exam.ex.pojo.TeacherDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 教师表 服务类
 * </p>
 *
 * @author 杨德石
 * @since 2019-03-31
 */
public interface TeacherService extends IService<TeacherDO> {

    /**
     * 分页查询
     * @param page
     * @return
     */
    Page<TeacherDO> getByPage(Page<TeacherDO> page);

    /**
     * 全部重置密码
     */
    void resetPwdAll();

    /**
     * 更新所有人的年龄（+1）
     */
    void updateAllAge();

    /**
     * 查询所有不在监考的空闲教师
     * @return
     */
    List<TeacherDO> freeList();
}
