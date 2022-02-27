package com.exam.ts.mapper;

import com.exam.ts.pojo.StudentPaperConfigObjScoreDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 学生-试卷-每个题型-客观题得分表（一题一分） Mapper 接口
 * </p>
 *
 * @author 杨德石
 * @since 2019-05-24
 */
@Mapper
public interface StudentPaperConfigObjScoreMapper extends BaseMapper<StudentPaperConfigObjScoreDO> {

}
