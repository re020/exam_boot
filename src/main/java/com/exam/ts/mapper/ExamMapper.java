package com.exam.ts.mapper;

import com.exam.core.pojo.Page;
import com.exam.ts.pojo.ExamDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.ts.pojo.DTO.StudentDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 考试表 Mapper 接口
 * </p>
 *
 * @author 杨德石
 * @since 2019-05-24
 */
@Mapper
public interface ExamMapper extends BaseMapper<ExamDO> {

    /**
     * 分页查询
     * @param page
     * @return
     */
    List<ExamDO> getListByPage(Page<ExamDO> page);

    /**
     * 查询总是
     * @param page
     * @return
     */
    Integer getCountByPage(Page<ExamDO> page);

    /**
     * 分页查询学生考试1
     */
    List<ExamDO> getListByStu(Page<ExamDO> examDO);
}
