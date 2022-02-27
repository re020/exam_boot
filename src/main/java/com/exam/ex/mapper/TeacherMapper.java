package com.exam.ex.mapper;

import com.exam.core.pojo.Page;
import com.exam.ex.pojo.TeacherDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 教师表 Mapper 接口
 * </p>
 *
 * @author 杨德石
 * @since 2019-03-31
 */
public interface TeacherMapper extends BaseMapper<TeacherDO> {

    /**
     * 分页查询
     * @param page
     * @return
     */
    List<TeacherDO> getListByPage(Page<TeacherDO> page);

    /**
     * 查询总数
     * @param page
     * @return
     */
    Integer getCountByPage(Page<TeacherDO> page);

    /**
     * 全部重置密码
     */
    void resetPwdAll();

    /**
     * 更新所有教师的年龄
     */
    void updateAllAge();

    /**
     * 查询所有空闲的教师
     * @return
     */
    List<TeacherDO> freeList();
}
