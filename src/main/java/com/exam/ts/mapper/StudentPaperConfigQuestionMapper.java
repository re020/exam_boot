package com.exam.ts.mapper;

import com.exam.ts.pojo.StudentPaperConfigQuestionDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 学生试卷配置-题目表 Mapper 接口
 * </p>
 *
 * @author 杨德石
 * @since 2019-05-24
 */
@Mapper
public interface StudentPaperConfigQuestionMapper extends BaseMapper<StudentPaperConfigQuestionDO> {

    StudentPaperConfigQuestionDO getByPaperId(String paperId);
}
