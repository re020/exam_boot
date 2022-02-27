package com.exam.ts.service;

import com.exam.core.pojo.Page;
import com.exam.core.utils.Result;
import com.exam.ts.pojo.ExamDO;
import com.exam.ts.pojo.ExamStudentDO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.ts.pojo.DTO.StudentDTO;

import java.util.List;

/**
 * <p>
 * 考试-学生对应表 服务类
 * </p>
 *
 * @author 杨德石
 * @since 2019-05-24
 */
public interface ExamStudentService extends IService<ExamStudentDO> {

    /**
     * 校验并保存
     * @param examStudentDO
     * @return
     */
    Result checkAndSave(ExamStudentDO examStudentDO);

    /**
     * 根据考试id和学生id集合进行添加
     * @param examId
     * @param studentIds
     */
    void saveList(String examId, List<String> studentIds);

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
    Page<ExamStudentDO> getByPage(Page<ExamStudentDO> page);


    /**
     * 获得学生考试的列表
     * @param examDO
     * @return
     */
    Page<ExamDO> getList(Page<ExamDO> examDO);

}
