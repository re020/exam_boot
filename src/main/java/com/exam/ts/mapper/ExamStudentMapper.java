package com.exam.ts.mapper;

import com.exam.core.pojo.Page;
import com.exam.ts.pojo.ExamDO;
import com.exam.ts.pojo.ExamStudentDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 考试-学生对应表 Mapper 接口
 * </p>
 *
 * @author 杨德石
 * @since 2019-05-24
 */
@Mapper
public interface ExamStudentMapper extends BaseMapper<ExamStudentDO> {

    /**
     * 根据考试id和学生id，查询未结束的考试中是否有这个学生
     * @param examStudentDO
     * @return
     */
    ExamStudentDO getByPojo(ExamStudentDO examStudentDO);

    /**
     * 根据考试id和学生id列表查询
     * @param examId
     * @param studentIds
     * @return
     */
    List<ExamStudentDO> getByExamIdAndStudentIds(@Param("examId") String examId, @Param("ids") List<String> studentIds);

    /**
     * 查询本场考试的所有学生
     * @param examId
     * @return
     */
    List<ExamStudentDO> getListByExam(String examId);

    /**
     * 分页查询
     * @param page
     * @return
     */
    List<ExamStudentDO> getListByPage(Page<ExamStudentDO> page);

    /**
     * 查询总数
     * @param page
     * @return
     */
    Integer getCountByPage(Page<ExamStudentDO> page);

    List<ExamDO> getById(String id);
}
