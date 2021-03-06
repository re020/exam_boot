package com.exam.ex.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.core.constant.CoreConstant;
import com.exam.core.constant.ExtConstant;
import com.exam.core.constant.GaConstant;
import com.exam.core.constant.NumberConstant;
import com.exam.core.constant.PatternConstant;
import com.exam.core.constant.SelectEnum;
import com.exam.core.constant.SubmitEnum;
import com.exam.core.constant.TestEnum;
import com.exam.core.constant.TypeEnum;
import com.exam.ex.dto.GaConfigDTO;
import com.exam.ex.dto.GaPaperDTO;
import com.exam.ex.ga.Generation;
import com.exam.ex.ga.Population;
import com.exam.ex.mapper.ChoiceMapper;
import com.exam.ex.mapper.CodeMapper;
import com.exam.ex.mapper.CompletionMapper;
import com.exam.ex.mapper.PaperConfigMapper;
import com.exam.ex.mapper.PaperLogMapper;
import com.exam.ex.mapper.PaperMapper;
import com.exam.ex.mapper.QuestionMapper;
import com.exam.ex.mapper.TrueFalseMapper;
import com.exam.ex.pojo.ChoiceAnswerDO;
import com.exam.ex.pojo.ChoiceDO;
import com.exam.ex.pojo.CodeDO;
import com.exam.ex.pojo.CompletionDO;
import com.exam.core.pojo.Page;
import com.exam.ex.pojo.PaperConfigDO;
import com.exam.ex.pojo.PaperConfigQuestionDO;
import com.exam.ex.pojo.PaperDO;
import com.exam.ex.pojo.QuestionDO;
import com.exam.ex.pojo.TeacherDO;
import com.exam.ex.pojo.TrueFalseDO;
import com.exam.ex.service.PaperConfigQuestionService;
import com.exam.ex.service.PaperService;
import com.exam.core.utils.DateUtils;
import com.exam.core.utils.IdWorker;
import com.exam.core.utils.LogUtils;
import com.exam.core.utils.ShiroUtils;
import com.exam.core.utils.StringUtils;
import com.exam.core.utils.ToWordUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * <p>
 * ????????? ???????????????
 * </p>
 *
 * @author ?????????
 * @since 2019-04-20
 */
@Service
public class PaperServiceImpl extends ServiceImpl<PaperMapper, PaperDO> implements PaperService {

    @Autowired
    private PaperConfigQuestionService paperConfigQuestionService;
    @Autowired
    private PaperMapper paperMapper;
    @Autowired
    private PaperConfigMapper paperConfigMapper;
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
    private PaperLogMapper paperLogMapper;
    @Autowired
    private IdWorker idWorker;

    private static final Pattern UNDER_LINE_PATTERN = PatternConstant.THREE_UNDER_LINE_PATTERN;

    /**
     * ????????????
     */
    @Override
    public Page<PaperDO> getByPage(Page<PaperDO> page) {
        // ????????????
        page.filterParams();
        TeacherDO loginTeacher = ShiroUtils.getLoginTeacher();
        if (SelectEnum.SELECT_COLLEGE.getCode().equals(loginTeacher.getTeacherOrg())) {
            // ????????????
            page.getParams().put("orgCollege", loginTeacher.getTeacherCollege());
        }
        if (SelectEnum.SELECT_SELF.getCode().equals(loginTeacher.getTeacherOrg())) {
            // ????????????
            page.getParams().put("orgTeacher", loginTeacher.getTeacherId());
        }
        // ????????????????????????
        if (page.getCurrentCount() == null) {
            page.setCurrentCount(CoreConstant.CURRENT_COUNT);
        }
        // ????????????
        Integer index = (page.getCurrentPage() - 1) * page.getCurrentCount();
        page.setIndex(index);
        // ??????????????????
        List<PaperDO> list = paperMapper.getListByPage(page);

        page.setList(list);
        Integer totalCount = paperMapper.getCountByPage(page);
        page.setTotalCount(totalCount);
        // ???????????????
        page.setTotalPage((int) Math.ceil((page.getTotalCount() * 1.0) / page.getCurrentCount()));
        return page;
    }

    /**
     * ??????????????????
     * ????????????????????????????????????
     * ?????????????????????????????????????????????????????????????????????
     */
    @Override
    public PaperDO getQuestion(String paperId) {
        // ?????????????????????????????????
        PaperDO paper = paperMapper.getPaperQuestion(paperId);
        if (paper == null) {
            return null;
        }
        List<PaperConfigDO> configList = paper.getConfigList();
        // ??????????????????????????????
        Map<String, List<String>> configMap = Maps.newHashMap();
        for (PaperConfigDO configDO : configList) {
            List<String> questionIds = configMap.get(configDO.getConfigType());
            if (questionIds == null) {
                questionIds = Lists.newArrayList();
            }
            questionIds.addAll(configDO.getQuestionList().stream().map(PaperConfigQuestionDO::getQuestionId).collect(Collectors.toList()));
            configMap.put(configDO.getConfigType(), questionIds);
        }
        // ???????????????????????????????????????
        List<PaperConfigDO> newConfigList = paperConfigMapper.getQuestionNum(paperId);

        // ?????????????????????map?????????????????????
        for (PaperConfigDO paperConfigDO : newConfigList) {
            String key = paperConfigDO.getConfigType();
            List<String> questionIds = configMap.get(key);
            // ??????id??????
            if (key.equals(TypeEnum.ONE_CHOICE.getCode().toString()) || key.equals(TypeEnum.MANY_CHOICE.getCode().toString())) {
                // ?????????
                Map<String, Object> paramsMap = Maps.newHashMap();
                paramsMap.put("choiceType", key);
                paramsMap.put("choiceIds", questionIds);
                List<ChoiceDO> choiceList = choiceMapper.getListByMapNoAnswer(paramsMap);
                // ????????????????????????
                choiceList.forEach(e -> {
                    List<String> numberList = e.getChoiceAnswer().stream().filter(ChoiceAnswerDO::getAnswerTrue)
                            .map(ChoiceAnswerDO::getAnswerNumber).collect(Collectors.toList());
                    e.setChoiceTrue(StringUtils.join(numberList, ", "));
                });
                paperConfigDO.setQuestionDetailList(choiceList);
            } else if (key.equals(TypeEnum.JUDGEMENT.getCode().toString())) {
                // ?????????
                List<TrueFalseDO> trueFalseDOList = trueFalseMapper.selectBatchIds(questionIds);
                paperConfigDO.setQuestionDetailList(trueFalseDOList);
            } else if (key.equals(TypeEnum.COMPLETION.getCode().toString())) {
                // ?????????
                List<CompletionDO> completionDOList = completionMapper.getByIds(questionIds);
                paperConfigDO.setQuestionDetailList(completionDOList);
            } else if (key.equals(TypeEnum.PROGRAMMING.getCode().toString())) {
                // ?????????
                List<CodeDO> codeDOList = codeMapper.getByIds(questionIds);
                paperConfigDO.setQuestionDetailList(codeDOList);
            } else {
                // ?????????
                List<QuestionDO> questionDOList = questionMapper.getByIds(questionIds);
                paperConfigDO.setQuestionDetailList(questionDOList);
            }
        }

        // ????????????
        newConfigList.sort(Comparator.comparingLong(c -> Long.parseLong(c.getConfigType())));
        // ??????????????????
        paper.setConfigList(newConfigList);
        return paper;
    }

    /**
     * ????????????
     * ????????????????????????????????????
     * ??????????????????????????????
     * ???????????????????????????
     *
     * @param paperId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submit(String paperId) throws Exception {
        // ???????????????
        PaperDO paper = getQuestion(paperId);
        // ???????????????????????????????????????
        ToWordUtil toWordUtil = new ToWordUtil(CoreConstant.TEMPLATE_FOLD);
        toWordUtil.setTemplateName(CoreConstant.TEMPLATE_FILE_NAME);

        String filename = paper.getPaperTitle() + ExtConstant.WORD_EXT;

        toWordUtil.setFileName(filename);
        toWordUtil.setFilePath(CoreConstant.PAPER_URL);
        toWordUtil.createWord(paper);
        // ??????????????????
        // ????????????????????????
        paper.setPaperSubmit(SubmitEnum.SUBMIT.getCode());
        // ????????????????????????
        String downloadUrl = CoreConstant.SERVER_FILE_URL + filename;
        paper.setPaperDownload(downloadUrl);

        // ??????????????????
        paper.setPaperUpdateTime(DateUtils.newDateTime());
        paperMapper.updateById(paper);

        LogUtils.saveLog(paper);

    }

    /**
     * ????????????id?????????id????????????????????????
     *
     * @param paperId
     * @param questionId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteQuestion(String paperId, String questionId) {

        // ????????????????????????
        PaperDO paperDO = paperMapper.selectById(paperId);

        // ?????????????????????
        PaperConfigDO paperConfigDO = paperConfigMapper.getByPaperAndQuestion(paperId, questionId);

        String configType = paperConfigDO.getConfigType();

        // ??????????????????
        paperConfigQuestionService.remove(new QueryWrapper<PaperConfigQuestionDO>()
                .eq("question_config", paperConfigDO.getConfigId())
                .eq("question_id", questionId));

        // ???????????????????????????id?????????id?????????????????????????????????????????????
        BigDecimal score;
        double difficulty;
        if (TypeEnum.ONE_CHOICE.getCode().toString().equals(configType) ||
                TypeEnum.MANY_CHOICE.getCode().toString().equals(configType)) {
            // ????????????
            ChoiceDO choiceDO = choiceMapper.selectById(questionId);
            score = choiceDO.getChoiceScore();
            difficulty = score.multiply(new BigDecimal(choiceDO.getChoiceDifficulty())).doubleValue();
        } else if (TypeEnum.JUDGEMENT.getCode().toString().equals(configType)) {
            // ?????????
            TrueFalseDO trueFalseDO = trueFalseMapper.selectById(questionId);
            score = trueFalseDO.getTfScore();
            difficulty = score.multiply(new BigDecimal(trueFalseDO.getTfDifficulty())).doubleValue();
        } else if (TypeEnum.COMPLETION.getCode().toString().equals(configType)) {
            // ?????????
            CompletionDO completionDO = completionMapper.selectById(questionId);
            score = completionDO.getCompScore();
            difficulty = score.multiply(new BigDecimal(completionDO.getCompDifficulty())).doubleValue();
        } else if (TypeEnum.PROGRAMMING.getCode().toString().equals(configType)) {
            CodeDO codeDO = codeMapper.selectById(questionId);
            score = codeDO.getCodeScore();
            difficulty = score.multiply(new BigDecimal(codeDO.getCodeDifficulty())).doubleValue();
        } else {
            // ????????????
            QuestionDO questionDO = questionMapper.selectById(questionId);
            score = questionDO.getQuestionScore();
            difficulty = score.multiply(new BigDecimal(questionDO.getQuestionDifficulty())).doubleValue();
        }

        // ???????????????????????????????????????????????????????????????
        // ???????????????
        paperConfigDO.setConfigScore(paperConfigDO.getConfigScore().subtract(score));
        paperConfigDO.setConfigQuestionNum(paperConfigDO.getConfigQuestionNum() - 1);
        paperConfigMapper.updateById(paperConfigDO);

        // ???????????????
        Integer oldNum = paperDO.getPaperQuestionNum();
        BigDecimal oldScore = paperDO.getPaperScore();
        BigDecimal newScore = oldScore.subtract(score);
        BigDecimal oldDiff = paperDO.getPaperDifficulty();
        Integer newNum = oldNum - 1;

        BigDecimal newDiff = oldDiff.multiply(oldScore);
        if (newScore.compareTo(new BigDecimal(NumberConstant.ZERO)) == 0) {
            newDiff = new BigDecimal(1);
        } else {
            newDiff = newDiff.subtract(new BigDecimal(difficulty));

            newDiff = newDiff.divide(newScore, NumberConstant.DEFAULT_DECIMAL_RETAIN, BigDecimal.ROUND_HALF_DOWN);
        }

        paperDO.setPaperDifficulty(newDiff);
        paperDO.setPaperScore(newScore);
        paperDO.setPaperQuestionNum(newNum);
        paperMapper.updateById(paperDO);
    }

    /**
     * ????????????????????????
     *
     * @param paperDTO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void gaSubmitPaper(GaPaperDTO paperDTO) throws Exception {
        // ????????????
        PaperDO paperDO = paperMapper.selectById(paperDTO.getPaperId());
        paperDO.setConfigList(Lists.newArrayList());
        for (GaConfigDTO configDTO : paperDTO.getConfigList()) {
            System.out.println(configDTO.getTypeId() + "" + configDTO.getQuestionNum());
            configDTO.setBankId(paperDO.getPaperBank());

            int count = 0;
            int runCount = GaConstant.MAX_EVOLVE;
            double expand = GaConstant.DEFAULT_ADAPTATION_DEGREE;

            // ???????????????
            Population population = new Population();
            population.initPopulation(GaConstant.POPULATION_SIZE, configDTO);
            System.out.println("??????????????????" + population.getFitness(0).getAdaptationDegree() + "???????????????????????????" + population.getFitness(0).getKpCoverage());

            Generation generation = new Generation();
            // ???????????????????????????????????????????????????????????????????????????????????????????????????
            while (count < runCount && population.getFitness(0).getAdaptationDegree() < expand) {
                count++;
                population = generation.evolvePopulation(population, configDTO);
            }
            PaperConfigDO config = population.getFitness(0);

            paperDO.getConfigList().add(config);
        }

        // ????????????????????????????????????
        saveGaPaper(paperDO);
    }

    /**
     * ??????????????????????????????
     *
     * @param id
     * @return
     */
    @Override
    public List<PaperConfigDO> getTypeNum(String id) {
        List<PaperConfigDO> typeList = paperConfigMapper.getTypeNum(id);
        for (PaperConfigDO configDO : typeList) {
            configDO.setTypeName(configDO.getType().getTypeName());
        }
        return typeList;
    }

    /**
     * ????????????????????????????????????
     *
     * @param paperDO
     */
    @Transactional(rollbackFor = Exception.class)
    protected void saveGaPaper(PaperDO paperDO) throws Exception {
        String paperId = paperDO.getPaperId();
        List<PaperConfigDO> configList = paperDO.getConfigList();
        List<PaperConfigQuestionDO> configQuestionDOList = Lists.newArrayList();
        for (PaperConfigDO config : configList) {

            config.setConfigPaper(paperId);
            List questionList = config.getQuestionDetailList();
            config.setConfigQuestionNum(questionList.size());

            for (Object tmpQuestion : questionList) {

                PaperConfigQuestionDO paperConfigQuestionDO = new PaperConfigQuestionDO();
                paperConfigQuestionDO.setId(idWorker.nextId() + "");
                paperConfigQuestionDO.setQuestionConfig(config.getConfigId());
                if (tmpQuestion instanceof ChoiceDO) {
                    // ?????????
                    ChoiceDO choiceDO = (ChoiceDO) tmpQuestion;
                    paperConfigQuestionDO.setQuestionId(choiceDO.getChoiceId());
                } else if (tmpQuestion instanceof TrueFalseDO) {
                    // ?????????
                    TrueFalseDO trueFalseDO = (TrueFalseDO) tmpQuestion;
                    paperConfigQuestionDO.setQuestionId(trueFalseDO.getTfId());
                } else if (tmpQuestion instanceof CompletionDO) {
                    // ?????????
                    CompletionDO completionDO = (CompletionDO) tmpQuestion;
                    paperConfigQuestionDO.setQuestionId(completionDO.getCompId());
                } else if (tmpQuestion instanceof CodeDO) {
                    // ?????????
                    CodeDO codeDO = (CodeDO) tmpQuestion;
                    paperConfigQuestionDO.setQuestionId(codeDO.getCodeId());
                } else {
                    // ?????????
                    QuestionDO questionDO = (QuestionDO) tmpQuestion;
                    paperConfigQuestionDO.setQuestionId(questionDO.getQuestionId());
                }
                configQuestionDOList.add(paperConfigQuestionDO);
            }
        }

        // ???????????????????????????????????????????????????????????????????????????????????????
        BigDecimal totalScore = configList.stream().map(PaperConfigDO::getConfigScore).reduce(BigDecimal::add).get();
        BigDecimal configDiff = configList.stream().map(e -> e.getConfigScore().multiply(new BigDecimal(e.getConfigDifficulty()))).reduce(BigDecimal::add).get();
        int questionNum = configList.stream().mapToInt(PaperConfigDO::getConfigQuestionNum).sum();
        paperDO.setPaperScore(totalScore);
        paperDO.setPaperQuestionNum(questionNum);

        // ??????????????????
        BigDecimal paperDiff = configDiff.divide(totalScore, NumberConstant.DEFAULT_DECIMAL_RETAIN, BigDecimal.ROUND_HALF_DOWN);
        paperDO.setPaperDifficulty(paperDiff);
        // ????????????????????????
        paperDO.setPaperType(TestEnum.INTELLIGENCE.getCode());
        // ???????????????
        paperDO.setPaperSubmit(SubmitEnum.SUBMIT.getCode());

        // ??????????????????
        paperConfigQuestionService.saveBatch(configQuestionDOList);

        // ??????????????????
        paperConfigMapper.saveBatch(configList);

        // ??????????????????
        paperDO.setPaperUpdateTime(DateUtils.newDate());
        updateById(paperDO);

        // ????????????
        submit(paperId);
    }


}
