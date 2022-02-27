package com.exam.ex.mapper;

import com.exam.ex.pojo.TeacherRoleDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 教师-角色表 Mapper 接口
 * </p>
 *
 * @author 杨德石
 * @since 2019-04-05
 */
public interface TeacherRoleMapper extends BaseMapper<TeacherRoleDO> {

    /**
     * 批量添加
     * @param list
     */
    void saveBatch(List<TeacherRoleDO> list);
}
