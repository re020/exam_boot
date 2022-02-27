package com.exam.ex.ga;

import com.exam.core.constant.TypeEnum;
import com.exam.ex.dto.GaConfigDTO;
import com.exam.ex.pojo.ChoiceDO;
import com.exam.ex.pojo.CodeDO;
import com.exam.ex.pojo.CompletionDO;
import com.exam.ex.pojo.QuestionDO;
import com.exam.ex.pojo.TrueFalseDO;
import com.exam.ex.service.ChoiceService;
import com.exam.ex.service.CodeService;
import com.exam.ex.service.CompletionService;
import com.exam.ex.service.TrueFalseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 遗传算法专用Service
 * 由于方法比较少所以不写接口了
 * @Author: 杨德石
 * @Date: 2019/5/3 0003 下午 4:48
 * @Version 1.0
 */
@Service
public class GaService {

    @Autowired
    private ChoiceService choiceService;
    @Autowired
    private TrueFalseService trueFalseService;
    @Autowired
    private CompletionService completionService;
    @Autowired
    private CodeService codeService;
    @Autowired
    private com.exam.ex.service.QuestionService questionService;

    /**
     * 查询题目列表
     * @param configDTO
     * @return
     */
    public List getGaList(GaConfigDTO configDTO) {
        List list;
        // 根据dto查询出所有符合条件的题目
        if (configDTO.getTypeId().equals(TypeEnum.ONE_CHOICE.getCode().toString()) ||
                configDTO.getTypeId().equals(TypeEnum.MANY_CHOICE.getCode().toString())) {
            // 选择题
            list = choiceService.getGaList(configDTO);
        } else if (configDTO.getTypeId().equals(TypeEnum.JUDGEMENT.getCode().toString())) {
            // 判断题
            list = trueFalseService.getGaList(configDTO);
        } else if (configDTO.getTypeId().equals(TypeEnum.COMPLETION.getCode().toString())) {
            // 填空题
            list = completionService.getGaList(configDTO);
        } else if (configDTO.getTypeId().equals(TypeEnum.PROGRAMMING.getCode().toString())) {
            // 编程题
            list = codeService.getGaList(configDTO);
        } else {
            // 其他题
            list = questionService.getGaList(configDTO);
        }
        return list;
    }

    /**
     * 从题库中查询所有跟原题难度相同的题目
     * @return
     */
    public List getMutateList(Object tmpQuestion, GaConfigDTO configDTO) {
        List list;
        // 根据dto查询出所有符合条件的题目
        if (tmpQuestion instanceof ChoiceDO) {
            // 选择题
            ChoiceDO choiceDO = (ChoiceDO) tmpQuestion;
            list = choiceService.getMutateList(choiceDO, configDTO);
        } else if (tmpQuestion instanceof TrueFalseDO) {
            // 判断题
            TrueFalseDO trueFalseDO = (TrueFalseDO) tmpQuestion;
            list = trueFalseService.getMutateList(trueFalseDO, configDTO);
        } else if (tmpQuestion instanceof CompletionDO) {
            // 填空题
            CompletionDO completionDO = (CompletionDO) tmpQuestion;
            list = completionService.getMutateList(completionDO, configDTO);
        } else if (tmpQuestion instanceof CodeDO) {
            // 编程题
            CodeDO codeDO = (CodeDO) tmpQuestion;
            list = codeService.getMutateList(codeDO, configDTO);
        } else {
            // 其他题
            QuestionDO questionDO = (QuestionDO) tmpQuestion;
            list = questionService.getMutateList(questionDO, configDTO);
        }
        return list;
    }
}
