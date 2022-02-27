package com.exam.ex.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.ex.mapper.TeacherRoleMapper;
import com.exam.ex.pojo.TeacherDO;
import com.exam.ex.pojo.TeacherRoleDO;
import com.exam.ex.service.TeacherRoleService;
import com.exam.core.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 教师-角色表 服务实现类
 * </p>
 *
 * @author 杨德石
 * @since 2019-04-05
 */
@Service
public class TeacherRoleServiceImpl extends ServiceImpl<TeacherRoleMapper, TeacherRoleDO> implements TeacherRoleService {

    @Autowired
    private TeacherRoleMapper teacherRoleMapper;
    @Autowired
    private IdWorker idWorker;

    /**
     * 查询教师角色
     *
     * @param teacherDO
     * @return
     */
    @Override
    public List<TeacherRoleDO> getByTeacher(TeacherDO teacherDO) {
        QueryWrapper<TeacherRoleDO> wrapper = new QueryWrapper<TeacherRoleDO>()
                .eq("tr_teacher", teacherDO.getTeacherId());
        return teacherRoleMapper.selectList(wrapper);
    }

    @Override
    public List<TeacherRoleDO> getByTeacherById(String teacherId) {
        QueryWrapper<TeacherRoleDO> wrapper = new QueryWrapper<TeacherRoleDO>()
                .eq("tr_teacher", teacherId);
        return teacherRoleMapper.selectList(wrapper);
    }

    /**
     * 为教师添加角色
     *
     * @param roleList
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addRole(List<TeacherRoleDO> roleList) {
        if (!roleList.isEmpty()) {
            String teacherId = roleList.get(0).getTrTeacher();
            QueryWrapper<TeacherRoleDO> wrapper = new QueryWrapper<TeacherRoleDO>()
                    .eq("tr_teacher", teacherId);
            teacherRoleMapper.delete(wrapper);

            // 设置id
            roleList = roleList.stream().map(e -> {
                TeacherRoleDO tr = new TeacherRoleDO();
                tr.setTrId(idWorker.nextId() + "");
                tr.setTrRole(e.getTrRole());
                tr.setTrTeacher(e.getTrTeacher());
                return tr;
            }).collect(Collectors.toList());
            teacherRoleMapper.saveBatch(roleList);
        }
    }
}
