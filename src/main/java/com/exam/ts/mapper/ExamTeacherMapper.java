package com.exam.ts.mapper;

import com.exam.ts.pojo.ExamTeacherDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 考试-监考教师表 Mapper 接口
 * </p>
 *
 * @author 杨德石
 * @since 2019-05-24
 */
@Mapper
public interface ExamTeacherMapper extends BaseMapper<ExamTeacherDO> {

    /**
     * 查询所有非空闲的教师
     * @param ttTeacher
     * @return
     */
    List<ExamTeacherDO> getNotEndTeacher(String ttTeacher);

    /**
     * 根据考试id查询
     * @param examId
     * @return
     */
    List<ExamTeacherDO> getByExamId(String examId);
}
