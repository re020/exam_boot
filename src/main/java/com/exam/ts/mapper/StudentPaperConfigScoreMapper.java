package com.exam.ts.mapper;

import com.exam.ts.pojo.StudentPaperConfigScoreDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 学生试卷配置-总得分表 Mapper 接口
 * </p>
 *
 * @author 杨德石
 * @since 2019-05-24
 */
@Mapper
public interface StudentPaperConfigScoreMapper extends BaseMapper<StudentPaperConfigScoreDO> {


    BigDecimal getGradesByPaperAndStu(@Param("paperId") String answerPaper,@Param("stuId") String answerStudent);

    /**
     * 查询该学生的成绩
     */
    BigDecimal getScoresByIds(@Param("paperId") String paperId,@Param("configIds") List<String> ids);
}
