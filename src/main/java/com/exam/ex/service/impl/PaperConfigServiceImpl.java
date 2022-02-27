package com.exam.ex.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.core.constant.CharConstant;
import com.exam.core.constant.NumberConstant;
import com.exam.core.constant.TestEnum;
import com.exam.core.constant.TypeEnum;
import com.exam.core.exception.ExamException;
import com.exam.ex.mapper.ChoiceMapper;
import com.exam.ex.mapper.CodeMapper;
import com.exam.ex.mapper.CompletionMapper;
import com.exam.ex.mapper.PaperConfigMapper;
import com.exam.ex.mapper.QuestionMapper;
import com.exam.ex.mapper.TrueFalseMapper;
import com.exam.ex.pojo.ChoiceDO;
import com.exam.ex.pojo.CodeDO;
import com.exam.ex.pojo.CompletionDO;
import com.exam.ex.pojo.PaperConfigDO;
import com.exam.ex.pojo.PaperConfigQuestionDO;
import com.exam.ex.pojo.PaperDO;
import com.exam.ex.pojo.QuestionDO;
import com.exam.ex.pojo.TrueFalseDO;
import com.exam.ex.service.PaperConfigQuestionService;
import com.exam.ex.service.PaperConfigService;
import com.exam.ex.service.PaperService;
import com.exam.core.utils.IdWorker;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 试卷配置表 服务实现类
 * </p>
 *
 * @author 杨德石
 * @since 2019-04-12
 */
@Service
public class PaperConfigServiceImpl extends ServiceImpl<PaperConfigMapper, PaperConfigDO> implements PaperConfigService {

    @Autowired
    private PaperConfigMapper paperConfigMapper;
    @Autowired
    private PaperConfigQuestionService paperConfigQuestionService;
    @Autowired
    private PaperService paperService;
    @Autowired
    private ChoiceMapper choiceMapper;
    @Autowired
    private TrueFalseMapper trueFalseMapper;
    @Autowired
    private CompletionMapper completionMapper;
    @Autowired
    private CodeMapper codeMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private IdWorker idWorker;

    /**
     * 添加题目到试卷中
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addQuestionToPaper(PaperConfigDO config) throws ExamException {
        List<PaperConfigQuestionDO> questionList = config.getQuestionList();
        // 查看这个配置是否存在
        PaperConfigDO configDO = paperConfigMapper.selectOne(new QueryWrapper<PaperConfigDO>()
                .eq("config_paper", config.getConfigPaper())
                .eq("config_know", config.getConfigKnow())
                .eq("config_type", config.getConfigType()));
        String configId;
        if (configDO == null) {
            // 如果是空的，id就生成
            configId = idWorker.nextId() + "";
        } else {
            // 如果非空，就取出id
            configId = configDO.getConfigId();
        }
        // 遍历题目列表，设置id和配置id
        List<String> questionIds = Lists.newArrayList();
        for (PaperConfigQuestionDO questionDO : questionList) {
            questionDO.setId(idWorker.nextId() + "");
            questionDO.setQuestionConfig(configId);
            questionIds.add(questionDO.getQuestionId());
        }
        List<PaperConfigQuestionDO> configQuestionDOList = checkQuestion(configId, questionList, config);
        // 将题目列表中已存在的删除
        questionList.removeAll(configQuestionDOList);
        // 批量添加题目
        paperConfigQuestionService.saveBatch(questionList);
        BigDecimal score = new BigDecimal(0);
        double difficulty = 0;
        // 查询出所有的题目
        if (TypeEnum.ONE_CHOICE.getCode().toString().equals(config.getConfigType()) ||
                TypeEnum.MANY_CHOICE.getCode().toString().equals(config.getConfigType())) {
            // 是选择题
            List<ChoiceDO> choiceList = choiceMapper.selectBatchIds(questionIds);
            for (ChoiceDO choiceDO : choiceList) {
                score = score.add(choiceDO.getChoiceScore());
                difficulty += choiceDO.getChoiceScore().multiply(new BigDecimal(choiceDO.getChoiceDifficulty())).doubleValue();
            }
        } else if (TypeEnum.JUDGEMENT.getCode().toString().equals(config.getConfigType())) {
            // 是判断题
            List<TrueFalseDO> trueFalseList = trueFalseMapper.selectBatchIds(questionIds);
            for (TrueFalseDO trueFalseDO : trueFalseList) {
                score = score.add(trueFalseDO.getTfScore());
                difficulty += trueFalseDO.getTfScore().multiply(new BigDecimal(trueFalseDO.getTfDifficulty())).doubleValue();
            }
        } else if (TypeEnum.COMPLETION.getCode().toString().equals(config.getConfigType())) {
            // 是填空题
            List<CompletionDO> completionList = completionMapper.selectBatchIds(questionIds);
            for (CompletionDO completionDO : completionList) {
                score = score.add(completionDO.getCompScore());
                difficulty += completionDO.getCompScore().multiply(new BigDecimal(completionDO.getCompDifficulty())).doubleValue();
            }
        } else if (TypeEnum.PROGRAMMING.getCode().toString().equals(config.getConfigType())) {
            // 是编程题
            List<CodeDO> codeList = codeMapper.selectBatchIds(questionIds);
            for (CodeDO codeDO : codeList) {
                score = score.add(codeDO.getCodeScore());
                difficulty += codeDO.getCodeScore().multiply(new BigDecimal(codeDO.getCodeDifficulty())).doubleValue();
            }
        } else {
            // 是其他题
            List<QuestionDO> questions = questionMapper.selectBatchIds(questionIds);
            for (QuestionDO questionDO : questions) {
                score = score.add(questionDO.getQuestionScore());
                difficulty += questionDO.getQuestionScore().multiply(new BigDecimal(questionDO.getQuestionDifficulty())).doubleValue();
            }
        }
        if (configDO == null) {
            // 如果是空的，就添加
            config.setConfigId(configId);
            config.setConfigScore(score);
            config.setConfigQuestionNum(questionList.size());
            paperConfigMapper.insert(config);
        } else {
            // 如果非空，就更新
            configDO.setConfigScore(config.getConfigScore().add(score));
            configDO.setConfigQuestionNum(questionList.size());
            paperConfigMapper.updateById(configDO);
        }
        updatePaper(config, questionList, score, difficulty);
    }

    /**
     * 查询试卷中每个题型的数量
     * @param paperId
     * @return
     */
    @Override
    public List<PaperConfigDO> getQuestionNum(String paperId) {
        return paperConfigMapper.getQuestionNum(paperId);
    }

    /**
     * 根据试卷id和题目id查询
     * @param paperId
     * @param questionId
     * @return
     */
    @Override
    public PaperConfigDO getByPaperAndQuestion(String paperId, String questionId) {
        return paperConfigMapper.getByPaperAndQuestion(paperId, questionId);
    }

    /**
     * 更新试卷状态
     * @param config
     * @param questionList
     * @param score
     * @param difficulty
     */
    private void updatePaper(PaperConfigDO config, List<PaperConfigQuestionDO> questionList, BigDecimal score, double difficulty) {
        // 试卷状态需要更新
        // 先查询试卷
        PaperDO paperDO = paperService.getById(config.getConfigPaper());
        // 获取总分数
        BigDecimal paperScore = paperDO.getPaperScore();
        // 获取难度系数
        BigDecimal paperDifficulty = paperDO.getPaperDifficulty();
        // 计算总难度系数
        BigDecimal sumDiff = paperDifficulty.multiply(paperScore);
        // 状态改成手动组卷
        paperDO.setPaperType(TestEnum.MANUAL.getCode());
        // 设置总分
        paperDO.setPaperScore(paperScore.add(score));
        // 设置总题数
        paperDO.setPaperQuestionNum(paperDO.getPaperQuestionNum() + questionList.size());
        // 计算难度系数
        BigDecimal newDiff = new BigDecimal(difficulty).add(sumDiff);

        newDiff = newDiff.divide(paperDO.getPaperScore(), NumberConstant.DEFAULT_DECIMAL_RETAIN, BigDecimal.ROUND_HALF_DOWN);
        // 设置难度系数

        paperDO.setPaperDifficulty(newDiff);
        paperService.updateById(paperDO);
    }

    /**
     * 检查题目是否存在
     *
     * @param configId
     * @param questionList
     * @param config
     * @return
     * @throws ExamException
     */
    private List<PaperConfigQuestionDO> checkQuestion(String configId, List<PaperConfigQuestionDO> questionList, PaperConfigDO config) throws ExamException {
        // 查询出这个配置的所有题目，判断一下是否已经存在该题
        List<PaperConfigQuestionDO> configQuestionDOList = paperConfigQuestionService
                .list(new QueryWrapper<PaperConfigQuestionDO>().eq("question_config", configId));
        for (PaperConfigQuestionDO questionDO : questionList) {
            if (configQuestionDOList.contains(questionDO)) {
                String questionTitle;
                if (TypeEnum.ONE_CHOICE.getCode().toString().equals(config.getConfigType()) ||
                        TypeEnum.MANY_CHOICE.getCode().toString().equals(config.getConfigType())) {
                    // 是选择题
                    ChoiceDO choiceDO = choiceMapper.selectById(questionDO.getQuestionId());
                    questionTitle = choiceDO.getChoiceTitle();
                } else if (TypeEnum.JUDGEMENT.getCode().toString().equals(config.getConfigType())) {
                    // 是判断题
                    TrueFalseDO trueFalseDO = trueFalseMapper.selectById(questionDO.getQuestionId());
                    questionTitle = trueFalseDO.getTfTitle();
                } else if (TypeEnum.COMPLETION.getCode().toString().equals(config.getConfigType())) {
                    // 是填空题
                    CompletionDO completionDO = completionMapper.selectById(questionDO.getQuestionId());
                    questionTitle = completionDO.getCompTitle();
                } else if (TypeEnum.PROGRAMMING.getCode().toString().equals(config.getConfigType())) {
                    // 是编程题
                    CodeDO codeDO = codeMapper.selectById(questionDO.getQuestionId());
                    questionTitle = codeDO.getCodeTitle();
                } else {
                    // 是其他题
                    QuestionDO question = questionMapper.selectById(questionDO.getQuestionId());
                    questionTitle = question.getQuestionTitle();
                }
                if (questionTitle.length() > NumberConstant.TEN) {
                    questionTitle = questionTitle.substring(NumberConstant.ZERO, NumberConstant.TEN) + CharConstant.THREE_POINT;
                }
                throw new ExamException("题目[" + questionTitle + "]已存在！");
            }
        }
        return configQuestionDOList;
    }

}
