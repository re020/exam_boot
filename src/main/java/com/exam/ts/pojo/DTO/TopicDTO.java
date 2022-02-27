package com.exam.ts.pojo.DTO;

import com.exam.ex.pojo.ChoiceDO;
import com.exam.ex.pojo.CodeDO;
import com.exam.ex.pojo.CompletionDO;
import com.exam.ex.pojo.QuestionDO;
import com.exam.ex.pojo.TrueFalseDO;
import lombok.Data;

/**
 *  题目信息及答案
 * @author lth
 * @version 1.0.0
 * @date
 */

@Data
public class TopicDTO {

    private ChoiceDO choiceDO;

    private CodeDO codeDO;

    private CompletionDO completionDO;

    private TrueFalseDO trueFalseDO;

    private QuestionDO questionDO;



}
