package com.exam.ts.mapper;

import com.exam.ts.pojo.StudentAnswerDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 学生做题答案表 Mapper 接口
 * </p>
 *
 * @author 杨德石
 * @since 2019-05-24
 */
@Mapper
public interface StudentAnswerMapper extends BaseMapper<StudentAnswerDO> {

    List<StudentAnswerDO> selectcofigList(StudentAnswerDO studentAnswerDO);

}
