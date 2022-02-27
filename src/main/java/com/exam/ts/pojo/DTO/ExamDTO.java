package com.exam.ts.pojo.DTO;

import com.exam.ts.pojo.ExamDO;
import com.exam.ts.pojo.StudentPaperDO;
import lombok.Data;

import java.io.Serializable;

/**
 *  学生考试返回
 * @author lth
 * @version 1.0.0
 * @date
 */

@Data
public class ExamDTO implements Serializable {

    private ExamDO examDO;

    private StudentPaperDO studentPaperDO;

    private TopicDTO topicDto;

}
