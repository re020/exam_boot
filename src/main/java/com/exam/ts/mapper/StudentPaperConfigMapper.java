package com.exam.ts.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.ex.pojo.PaperConfigDO;
import com.exam.ts.pojo.StudentPaperConfigDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 学生试卷配置表 Mapper 接口
 * </p>
 *
 * @author 杨德石
 * @since 2019-05-24
 */
@Mapper
public interface StudentPaperConfigMapper extends BaseMapper<StudentPaperConfigDO> {

    /**
     * 批量插入
     * @param configList
     */
    void saveBatch(List<StudentPaperConfigDO> configList);

    List<StudentPaperConfigDO> getQuestionNum(String paperId);


    StudentPaperConfigDO getByPaperAndQuestion(@Param("paperId") String paperId, @Param("questionId") String questionId);




}
