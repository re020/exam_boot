package com.exam.ts.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exam.core.constant.ExamConstant;
import com.exam.core.constant.ResultEnum;
import com.exam.core.utils.IdWorker;
import com.exam.core.utils.Result;
import com.exam.ts.pojo.ExamTeacherDO;
import com.exam.ts.mapper.ExamTeacherMapper;
import com.exam.ts.service.ExamTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 考试-监考教师表 服务实现类
 * </p>
 *
 * @author 杨德石
 * @since 2019-05-24
 */
@Service
public class ExamTeacherServiceImpl extends ServiceImpl<ExamTeacherMapper, ExamTeacherDO> implements ExamTeacherService {

    @Autowired
    private ExamTeacherMapper examTeacherMapper;
    @Autowired
    private IdWorker idWorker;

    /**
     * 检查后进行保存
     *
     * @param examTeacherDO
     * @return
     */
    @Override
    public Result checkAndSave(ExamTeacherDO examTeacherDO) {
        // 检查该考试监考教师数量是否已经超过最大值
        List<ExamTeacherDO> list = examTeacherMapper.selectList(new QueryWrapper<ExamTeacherDO>().eq("tt_exam", examTeacherDO.getTtExam()));
        if (list != null && list.size() >= ExamConstant.MAX_TEACHER_NUM) {
            return Result.build(ResultEnum.ERROR.getCode(), "每场考试监考教师最多指定" + ExamConstant.MAX_TEACHER_NUM + "名");
        }

        // 检查该监考教师是否已经有了考试需要监考
        list = examTeacherMapper.getNotEndTeacher(examTeacherDO.getTtTeacher());
        if (list != null && !list.isEmpty()) {
            return Result.build(ResultEnum.ERROR.getCode(), "该教师已分配了其他监考任务！");
        }
        examTeacherDO.setTtId(idWorker.nextId() + "");
        examTeacherMapper.insert(examTeacherDO);
        return Result.ok("添加成功！");
    }

    /**
     * 根据考试id查询
     * @param examId
     * @return
     */
    @Override
    public List<ExamTeacherDO> getByExamId(String examId) {
        return examTeacherMapper.getByExamId(examId);
    }
}
