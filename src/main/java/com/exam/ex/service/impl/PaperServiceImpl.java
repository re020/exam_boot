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
 * 试卷表 服务实现类
 * </p>
 *
 * @author 杨德石
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
     * 分页查询
     */
    @Override
    public Page<PaperDO> getByPage(Page<PaperDO> page) {
        // 处理参数
        page.filterParams();
        TeacherDO loginTeacher = ShiroUtils.getLoginTeacher();
        if (SelectEnum.SELECT_COLLEGE.getCode().equals(loginTeacher.getTeacherOrg())) {
            // 查询学院
            page.getParams().put("orgCollege", loginTeacher.getTeacherCollege());
        }
        if (SelectEnum.SELECT_SELF.getCode().equals(loginTeacher.getTeacherOrg())) {
            // 查询自己
            page.getParams().put("orgTeacher", loginTeacher.getTeacherId());
        }
        // 设置每页显示条数
        if (page.getCurrentCount() == null) {
            page.setCurrentCount(CoreConstant.CURRENT_COUNT);
        }
        // 计算索引
        Integer index = (page.getCurrentPage() - 1) * page.getCurrentCount();
        page.setIndex(index);
        // 查询每页数据
        List<PaperDO> list = paperMapper.getListByPage(page);

        page.setList(list);
        Integer totalCount = paperMapper.getCountByPage(page);
        page.setTotalCount(totalCount);
        // 计算总页数
        page.setTotalPage((int) Math.ceil((page.getTotalCount() * 1.0) / page.getCurrentCount()));
        return page;
    }

    /**
     * 查询题目列表
     * 查询试卷的配置和配置题目
     * 遍历试卷的配置，查询出每个题目列表，封装进集合
     */
    @Override
    public PaperDO getQuestion(String paperId) {
        // 连表一次查询出试卷内容
        PaperDO paper = paperMapper.getPaperQuestion(paperId);
        if (paper == null) {
            return null;
        }
        List<PaperConfigDO> configList = paper.getConfigList();
        // 遍历集合，对集合分组
        Map<String, List<String>> configMap = Maps.newHashMap();
        for (PaperConfigDO configDO : configList) {
            List<String> questionIds = configMap.get(configDO.getConfigType());
            if (questionIds == null) {
                questionIds = Lists.newArrayList();
            }
            questionIds.addAll(configDO.getQuestionList().stream().map(PaperConfigQuestionDO::getQuestionId).collect(Collectors.toList()));
            configMap.put(configDO.getConfigType(), questionIds);
        }
        // 查询每个题型的分值、题目数
        List<PaperConfigDO> newConfigList = paperConfigMapper.getQuestionNum(paperId);

        // 分组完毕，遍历map，查询题目列表
        for (PaperConfigDO paperConfigDO : newConfigList) {
            String key = paperConfigDO.getConfigType();
            List<String> questionIds = configMap.get(key);
            // 获取id列表
            if (key.equals(TypeEnum.ONE_CHOICE.getCode().toString()) || key.equals(TypeEnum.MANY_CHOICE.getCode().toString())) {
                // 选择题
                Map<String, Object> paramsMap = Maps.newHashMap();
                paramsMap.put("choiceType", key);
                paramsMap.put("choiceIds", questionIds);
                List<ChoiceDO> choiceList = choiceMapper.getListByMapNoAnswer(paramsMap);
                // 过滤正确答案选项
                choiceList.forEach(e -> {
                    List<String> numberList = e.getChoiceAnswer().stream().filter(ChoiceAnswerDO::getAnswerTrue)
                            .map(ChoiceAnswerDO::getAnswerNumber).collect(Collectors.toList());
                    e.setChoiceTrue(StringUtils.join(numberList, ", "));
                });
                paperConfigDO.setQuestionDetailList(choiceList);
            } else if (key.equals(TypeEnum.JUDGEMENT.getCode().toString())) {
                // 判断题
                List<TrueFalseDO> trueFalseDOList = trueFalseMapper.selectBatchIds(questionIds);
                paperConfigDO.setQuestionDetailList(trueFalseDOList);
            } else if (key.equals(TypeEnum.COMPLETION.getCode().toString())) {
                // 填空题
                List<CompletionDO> completionDOList = completionMapper.getByIds(questionIds);
                paperConfigDO.setQuestionDetailList(completionDOList);
            } else if (key.equals(TypeEnum.PROGRAMMING.getCode().toString())) {
                // 编程题
                List<CodeDO> codeDOList = codeMapper.getByIds(questionIds);
                paperConfigDO.setQuestionDetailList(codeDOList);
            } else {
                // 其他题
                List<QuestionDO> questionDOList = questionMapper.getByIds(questionIds);
                paperConfigDO.setQuestionDetailList(questionDOList);
            }
        }

        // 排一下序
        newConfigList.sort(Comparator.comparingLong(c -> Long.parseLong(c.getConfigType())));
        // 设置新的配置
        paper.setConfigList(newConfigList);
        return paper;
    }

    /**
     * 提交组卷
     * 将试卷组卷状态改为已提交
     * 根据模板生成一份试卷
     * 更新试卷的下载链接
     *
     * @param paperId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submit(String paperId) throws Exception {
        // 先生成试卷
        PaperDO paper = getQuestion(paperId);
        // 下面生成一份试卷在服务器里
        ToWordUtil toWordUtil = new ToWordUtil(CoreConstant.TEMPLATE_FOLD);
        toWordUtil.setTemplateName(CoreConstant.TEMPLATE_FILE_NAME);

        String filename = paper.getPaperTitle() + ExtConstant.WORD_EXT;

        toWordUtil.setFileName(filename);
        toWordUtil.setFilePath(CoreConstant.PAPER_URL);
        toWordUtil.createWord(paper);
        // 创建试卷成功
        // 更新状态为已提交
        paper.setPaperSubmit(SubmitEnum.SUBMIT.getCode());
        // 更新试卷下载链接
        String downloadUrl = CoreConstant.SERVER_FILE_URL + filename;
        paper.setPaperDownload(downloadUrl);

        // 修改更新时间
        paper.setPaperUpdateTime(DateUtils.newDateTime());
        paperMapper.updateById(paper);

        LogUtils.saveLog(paper);

    }

    /**
     * 根据分类id和题目id从试卷中删除题目
     *
     * @param paperId
     * @param questionId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteQuestion(String paperId, String questionId) {

        // 查询到配置、试卷
        PaperDO paperDO = paperMapper.selectById(paperId);

        // 先查询试卷配置
        PaperConfigDO paperConfigDO = paperConfigMapper.getByPaperAndQuestion(paperId, questionId);

        String configType = paperConfigDO.getConfigType();

        // 删除试卷配置
        paperConfigQuestionService.remove(new QueryWrapper<PaperConfigQuestionDO>()
                .eq("question_config", paperConfigDO.getConfigId())
                .eq("question_id", questionId));

        // 删除之后，根据配置id和题目id查询题目，获取到分值和难度系数
        BigDecimal score;
        double difficulty;
        if (TypeEnum.ONE_CHOICE.getCode().toString().equals(configType) ||
                TypeEnum.MANY_CHOICE.getCode().toString().equals(configType)) {
            // 是选择题
            ChoiceDO choiceDO = choiceMapper.selectById(questionId);
            score = choiceDO.getChoiceScore();
            difficulty = score.multiply(new BigDecimal(choiceDO.getChoiceDifficulty())).doubleValue();
        } else if (TypeEnum.JUDGEMENT.getCode().toString().equals(configType)) {
            // 判断题
            TrueFalseDO trueFalseDO = trueFalseMapper.selectById(questionId);
            score = trueFalseDO.getTfScore();
            difficulty = score.multiply(new BigDecimal(trueFalseDO.getTfDifficulty())).doubleValue();
        } else if (TypeEnum.COMPLETION.getCode().toString().equals(configType)) {
            // 填空题
            CompletionDO completionDO = completionMapper.selectById(questionId);
            score = completionDO.getCompScore();
            difficulty = score.multiply(new BigDecimal(completionDO.getCompDifficulty())).doubleValue();
        } else if (TypeEnum.PROGRAMMING.getCode().toString().equals(configType)) {
            CodeDO codeDO = codeMapper.selectById(questionId);
            score = codeDO.getCodeScore();
            difficulty = score.multiply(new BigDecimal(codeDO.getCodeDifficulty())).doubleValue();
        } else {
            // 是其他题
            QuestionDO questionDO = questionMapper.selectById(questionId);
            score = questionDO.getQuestionScore();
            difficulty = score.multiply(new BigDecimal(questionDO.getQuestionDifficulty())).doubleValue();
        }

        // 重新计算配置和试卷的分值、题目亮、难度系数
        // 计算配置的
        paperConfigDO.setConfigScore(paperConfigDO.getConfigScore().subtract(score));
        paperConfigDO.setConfigQuestionNum(paperConfigDO.getConfigQuestionNum() - 1);
        paperConfigMapper.updateById(paperConfigDO);

        // 计算试卷的
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
     * 遗传算法智能组卷
     *
     * @param paperDTO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void gaSubmitPaper(GaPaperDTO paperDTO) throws Exception {
        // 循环组卷
        PaperDO paperDO = paperMapper.selectById(paperDTO.getPaperId());
        paperDO.setConfigList(Lists.newArrayList());
        for (GaConfigDTO configDTO : paperDTO.getConfigList()) {
            System.out.println(configDTO.getTypeId() + "" + configDTO.getQuestionNum());
            configDTO.setBankId(paperDO.getPaperBank());

            int count = 0;
            int runCount = GaConstant.MAX_EVOLVE;
            double expand = GaConstant.DEFAULT_ADAPTATION_DEGREE;

            // 初始化种群
            Population population = new Population();
            population.initPopulation(GaConstant.POPULATION_SIZE, configDTO);
            System.out.println("初次适应度：" + population.getFitness(0).getAdaptationDegree() + "，知识点覆盖率为：" + population.getFitness(0).getKpCoverage());

            Generation generation = new Generation();
            // 如果进化次数小于最大进化次数，并且个体适应度小于适应度大小，就进化
            while (count < runCount && population.getFitness(0).getAdaptationDegree() < expand) {
                count++;
                population = generation.evolvePopulation(population, configDTO);
            }
            PaperConfigDO config = population.getFitness(0);

            paperDO.getConfigList().add(config);
        }

        // 处理一下试卷，添加进题库
        saveGaPaper(paperDO);
    }

    /**
     * 查看每个题型的题目数
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
     * 智能组卷，处理试卷和配置
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
                    // 选择题
                    ChoiceDO choiceDO = (ChoiceDO) tmpQuestion;
                    paperConfigQuestionDO.setQuestionId(choiceDO.getChoiceId());
                } else if (tmpQuestion instanceof TrueFalseDO) {
                    // 判断题
                    TrueFalseDO trueFalseDO = (TrueFalseDO) tmpQuestion;
                    paperConfigQuestionDO.setQuestionId(trueFalseDO.getTfId());
                } else if (tmpQuestion instanceof CompletionDO) {
                    // 填空题
                    CompletionDO completionDO = (CompletionDO) tmpQuestion;
                    paperConfigQuestionDO.setQuestionId(completionDO.getCompId());
                } else if (tmpQuestion instanceof CodeDO) {
                    // 编程题
                    CodeDO codeDO = (CodeDO) tmpQuestion;
                    paperConfigQuestionDO.setQuestionId(codeDO.getCodeId());
                } else {
                    // 其他题
                    QuestionDO questionDO = (QuestionDO) tmpQuestion;
                    paperConfigQuestionDO.setQuestionId(questionDO.getQuestionId());
                }
                configQuestionDOList.add(paperConfigQuestionDO);
            }
        }

        // 更改试卷状态：设置总分、难度系数，生成试卷、修改状态、题量
        BigDecimal totalScore = configList.stream().map(PaperConfigDO::getConfigScore).reduce(BigDecimal::add).get();
        BigDecimal configDiff = configList.stream().map(e -> e.getConfigScore().multiply(new BigDecimal(e.getConfigDifficulty()))).reduce(BigDecimal::add).get();
        int questionNum = configList.stream().mapToInt(PaperConfigDO::getConfigQuestionNum).sum();
        paperDO.setPaperScore(totalScore);
        paperDO.setPaperQuestionNum(questionNum);

        // 计算难度系数
        BigDecimal paperDiff = configDiff.divide(totalScore, NumberConstant.DEFAULT_DECIMAL_RETAIN, BigDecimal.ROUND_HALF_DOWN);
        paperDO.setPaperDifficulty(paperDiff);
        // 组卷状态智能组卷
        paperDO.setPaperType(TestEnum.INTELLIGENCE.getCode());
        // 状态已提交
        paperDO.setPaperSubmit(SubmitEnum.SUBMIT.getCode());

        // 插入题目数据
        paperConfigQuestionService.saveBatch(configQuestionDOList);

        // 插入配置数据
        paperConfigMapper.saveBatch(configList);

        // 修改试卷信息
        paperDO.setPaperUpdateTime(DateUtils.newDate());
        updateById(paperDO);

        // 生成试卷
        submit(paperId);
    }


}
