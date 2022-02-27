package com.exam.ts.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exam.core.constant.*;
import com.exam.core.pojo.Page;
import com.exam.core.utils.*;
import com.exam.ex.mapper.ChoiceMapper;
import com.exam.ex.mapper.CodeMapper;
import com.exam.ex.mapper.CompletionMapper;
import com.exam.ex.mapper.QuestionMapper;
import com.exam.ex.mapper.TrueFalseMapper;

import com.exam.core.exception.ExamException;
import com.exam.ex.dto.GaConfigDTO;
import com.exam.ex.ga.Generation;
import com.exam.ex.ga.Population;
import com.exam.ex.mapper.PaperMapper;
import com.exam.ex.pojo.ChoiceAnswerDO;
import com.exam.ex.pojo.ChoiceDO;
import com.exam.ex.pojo.CodeDO;
import com.exam.ex.pojo.CompletionDO;
import com.exam.ex.pojo.PaperConfigDO;
import com.exam.ex.pojo.PaperDO;
import com.exam.ex.pojo.QuestionDO;
import com.exam.ex.pojo.TeacherDO;
import com.exam.ex.pojo.TrueFalseDO;
import com.exam.ts.mapper.StudentAnswerMapper;
import com.exam.ts.mapper.StudentPaperConfigMapper;
import com.exam.ts.mapper.StudentPaperConfigScoreMapper;
import com.exam.ts.pojo.ExamStudentDO;
import com.exam.ts.pojo.StudentAnswerDO;
import com.exam.ts.pojo.StudentPaperConfigDO;
import com.exam.ts.pojo.StudentPaperConfigQuestionDO;
import com.exam.ts.pojo.StudentPaperDO;
import com.exam.ts.mapper.StudentPaperMapper;
import com.exam.ts.pojo.DTO.ExPaperDTO;
import com.exam.ts.service.ExamStudentService;
import com.exam.ts.service.StudentPaperConfigQuestionService;
import com.exam.ts.service.StudentPaperService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 学生试卷表 服务实现类
 * </p>
 *
 * @author 杨德石
 * @since 2019-05-24
 */
@Slf4j
@Service
public class StudentPaperServiceImpl extends ServiceImpl<StudentPaperMapper, StudentPaperDO> implements StudentPaperService {

    @Autowired
    private StudentPaperConfigQuestionService studentPaperConfigQuestionService;
    @Autowired
    private StudentPaperMapper studentPaperMapper;
    @Autowired
    private StudentPaperConfigMapper studentPaperConfigMapper;
    @Autowired
    private PaperMapper paperMapper;
    @Autowired
    private ExamStudentService examStudentService;
    @Autowired
    private ChoiceMapper choiceMapper;
    @Autowired
    private TrueFalseMapper trueFalseMapper;
    @Autowired
    private CompletionMapper completionMapper;
    @Autowired
    private StudentAnswerMapper studentAnswerMapper;
    @Autowired
    private StudentPaperConfigScoreMapper studentPaperConfigScoreMapper;
    @Autowired
    private CodeMapper codeMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private IdWorker idWorker;

    /**
     * 在线考试生成试卷
     *
     * @param exPaperDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void gaExam(ExPaperDTO exPaperDTO) throws Exception {
        log.info("开始生成试卷 exPaperDTO: [{}]", exPaperDTO);
        if(exPaperDTO.getConfigList() == null){
            throw new ExamException("所选试卷未预先进行设置，请先配置好基本信息");
        }

        // 过滤拥有试卷的学生
        List<String> stuId = exPaperDTO.getStuId();

        List<String> stuIds = studentPaperMapper.selectStuByexamId(exPaperDTO.getExamId());
        String paperId = exPaperDTO.getPaperId();
        PaperDO paperDO = paperMapper.selectById(paperId);
        if(paperDO == null){
            throw new ExamException("查询不到当前所选试卷，请确定试卷是否存在");
        }
        log.info("当前的试卷信息 :[{}]",paperDO);
        stuId.removeAll(stuIds);

        for (String student : stuId) {
            log.info("当前组卷学生 id: [{}]",student);
            // 循环组卷
            StudentPaperDO studentPaperDO = new StudentPaperDO();

            studentPaperDO.setPaperId(idWorker.nextId() + "");
            studentPaperDO.setPaperTitle(paperDO.getPaperTitle());
            studentPaperDO.setPaperExam(exPaperDTO.getExamId());
            studentPaperDO.setPaperStartYear(paperDO.getPaperStartYear());
            studentPaperDO.setPaperEndYear(paperDO.getPaperEndYear());
            studentPaperDO.setPaperSeme(paperDO.getPaperSeme());
            studentPaperDO.setPaperCollege(paperDO.getPaperCollege());
            studentPaperDO.setPaperBank(paperDO.getPaperBank());
            studentPaperDO.setPaperCreateTime(DateUtils.newDate());
            studentPaperDO.setConfigList(Lists.newArrayList());
            studentPaperDO.setPaperFlag(paperDO.getPaperFlag());
            studentPaperDO.setPaperDifficulty(new BigDecimal("0"));
            studentPaperDO.setPaperScore(new BigDecimal("0"));
            studentPaperDO.setPaperStudentScore(new BigDecimal("0"));
            studentPaperDO.setPaperStudent(student);
            studentPaperDO.setPaperQuestionNum(0);
            studentPaperDO.setPaperVersion(0);
            studentPaperDO.setPaperDelete(1);

            for (GaConfigDTO configDTO : exPaperDTO.getConfigList()) {
                System.out.println(configDTO.getTypeId() + "" + configDTO.getQuestionNum());

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
                // 复制属性到学生试卷配置类
                PaperConfigDO config = population.getFitness(0);
                StudentPaperConfigDO stuConfig = new StudentPaperConfigDO();
                BeanUtils.copyProperties(config, stuConfig);
                log.info("stuConfig: [{}]",config);
                studentPaperDO.getConfigList().add(stuConfig);
            }

            // 处理一下试卷，添加进题库
            saveGaExam(studentPaperDO, student);
            log.info("组卷结束");

            log.info("增加学生与考试");
            ExamStudentDO examStudentDO = new ExamStudentDO();
            examStudentDO.setStId(idWorker.nextId() + "");
            examStudentDO.setStExam(exPaperDTO.getExamId());
            examStudentDO.setStStu(student);
            examStudentDO.setStCreateTime(DateUtils.newDate());

            examStudentService.save(examStudentDO);
        }
    }

    @Override
    public Page<StudentPaperDO> listById(String id) {
        Page<StudentPaperDO> page = new Page<>();
        List<StudentPaperDO> list = studentPaperMapper.selectPaperById(id);
        page.setCurrentCount(10);
        page.setTotalCount(list.size());
        page.setTotalPage((int) Math.ceil((page.getTotalCount() * 1.0) / page.getCurrentCount()));
        page.setList(list);
        return page;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StudentPaperDO getPaperInfo(String paperId) {
        // 得到全部信息的试卷
        StudentPaperDO paper = studentPaperMapper.getQuestionById(paperId);
        if(paper == null){
            return null;
        }
        List<StudentPaperConfigDO> configList = paper.getConfigList();
        HashMap<String, List<String>> configMap = Maps.newHashMap();
        QueryWrapper<StudentAnswerDO> wrapper = new QueryWrapper<>();
        //设置学生做题答案
        QueryWrapper<StudentAnswerDO> query =
                wrapper.eq("answer_paper", paperId)
                .eq("answer_student", paper.getPaperStudent());
        for (StudentPaperConfigDO configDO : configList) {
            List<String> questionIds = configMap.get(configDO.getConfigType());
            if (questionIds == null) {
                questionIds = Lists.newArrayList();
            }
            questionIds.addAll(configDO.getQuestionList().stream().map(StudentPaperConfigQuestionDO::getQuestionId).collect(Collectors.toList()));
            configMap.put(configDO.getConfigType(), questionIds);
        }
        // 查询每个题型的分数，题目书
        List<StudentPaperConfigDO> newConfigList = studentPaperConfigMapper.getQuestionNum(paperId);
        for (StudentPaperConfigDO paperConfigDO : newConfigList) {
            String key = paperConfigDO.getConfigType();
            List<String> questionIds = configMap.get(key);
            // 根据id查询
            if (key.equals(TypeEnum.ONE_CHOICE.getCode().toString()) || key.equals(TypeEnum.MANY_CHOICE.getCode().toString())) {
                // 选择题
                Map<String, Object> paramsMap = Maps.newHashMap();
                paramsMap.put("choiceType", key);
                paramsMap.put("choiceIds", questionIds);
                List<ChoiceDO> choiceList = choiceMapper.getListByMap(paramsMap);
                // 过滤正确答案选项
                choiceList.forEach(e -> {
                    List<String> numberList = e.getChoiceAnswer().stream().filter(ChoiceAnswerDO::getAnswerTrue)
                            .map(ChoiceAnswerDO::getAnswerNumber).collect(Collectors.toList());
                    e.setChoiceTrue(StringUtils.join(numberList, ", "));
                    query.eq("answer_question",e.getChoiceId());
                    e.setAnswerContent(studentAnswerMapper.selectOne(wrapper));
                });
                paperConfigDO.setQuestionDetailList(choiceList);
            } else if (key.equals(TypeEnum.JUDGEMENT.getCode().toString())) {
                // 判断题
                List<TrueFalseDO> trueFalseDOList = trueFalseMapper.selectBatchIds(questionIds);
                trueFalseDOList.forEach(e ->{
                    query.eq("answer_question",e.getTfId());
                    e.setAnswerContent(studentAnswerMapper.selectOne(wrapper));
                });
                paperConfigDO.setQuestionDetailList(trueFalseDOList);
            } else if (key.equals(TypeEnum.COMPLETION.getCode().toString())) {
                // 填空题
                List<CompletionDO> completionDOList = completionMapper.getByIds(questionIds);
                completionDOList.forEach(e ->{
                    query.eq("answer_question",e.getCompId());
                    e.setAnswerContent(studentAnswerMapper.selectOne(wrapper));
                });
                paperConfigDO.setQuestionDetailList(completionDOList);
            } else if (key.equals(TypeEnum.PROGRAMMING.getCode().toString())) {
                // 编程题
                List<CodeDO> codeDOList = codeMapper.getByIds(questionIds);
                codeDOList.forEach(e ->{
                    query.eq("answer_question",e.getCodeId());
                    e.setAnswerContent(studentAnswerMapper.selectOne(wrapper));
                });
                paperConfigDO.setQuestionDetailList(codeDOList);
            } else {
                // 其他题
                List<QuestionDO> questionDOList = questionMapper.getByIds(questionIds);
                questionDOList.forEach(e ->{
                    query.eq("answer_question",e.getQuestionId());
                    e.setAnswerContent(studentAnswerMapper.selectOne(wrapper));
                });
                paperConfigDO.setQuestionDetailList(questionDOList);
            }
        }
        // 排一下序
        newConfigList.sort(Comparator.comparingLong(c -> Long.parseLong(c.getConfigType())));
        // 设置新的配置
        paper.setConfigList(newConfigList);
        return paper;
    }

    @Override
    public void correctSubmit(String paperId) {
        // 根据试卷id得到配置id
        QueryWrapper<StudentPaperConfigDO> wrapper = new QueryWrapper<>();
        wrapper.select("config_id")
                .eq("config_paper",paperId)
                .eq("config_delete", DeleteEnum.NOT_DELETE.getCode());
        List<StudentPaperConfigDO> configIds = studentPaperConfigMapper.selectList(wrapper);
        // 去学生试卷配置-总得分表统计成绩，更新学生试卷
        List<String> ids =
                configIds
                        .stream()
                        .map(StudentPaperConfigDO::getConfigId)
                        .collect(Collectors.toList());

        BigDecimal decimal =  studentPaperConfigScoreMapper.getScoresByIds(paperId,ids);
        StudentPaperDO paper = new StudentPaperDO();
        paper.setPaperId(paperId);
        paper.setPaperScore(decimal);
        paper.setPaperFlag(PaperEnum.FINISH.getCode());
        int result = studentPaperMapper.updateById(paper);
        log.info("统计分数完成:{}",result > 0);
    }


    /**
     * 智能组卷，处理试卷和配置
     *
     * @param studentpaperDO
     */
    @Transactional(rollbackFor = Exception.class)
    protected void saveGaExam(StudentPaperDO studentpaperDO, String studentId) throws Exception {
        log.info("开始智能组卷 试卷id: [{}]",studentpaperDO.getPaperId());
        String paperId = studentpaperDO.getPaperId();

        List<StudentPaperConfigDO> configList = studentpaperDO.getConfigList();

        List<StudentPaperConfigQuestionDO> configQuestionDOList = Lists.newArrayList();
        for (StudentPaperConfigDO config : configList) {
            log.info("当前配置 config: [{}]",config.getConfigQuestionNum());
            config.setConfigPaper(paperId);
            List questionList = config.getQuestionDetailList();
            config.setConfigQuestionNum(questionList.size());

            for (Object tmpQuestion : questionList) {

                StudentPaperConfigQuestionDO studentpaperConfigQuestionDO = new StudentPaperConfigQuestionDO();
                studentpaperConfigQuestionDO.setId(idWorker.nextId() + "");
                studentpaperConfigQuestionDO.setQuestionConfig(config.getConfigId());
                if (tmpQuestion instanceof ChoiceDO) {
                    // 选择题
                    ChoiceDO choiceDO = (ChoiceDO) tmpQuestion;
                    studentpaperConfigQuestionDO.setQuestionId(choiceDO.getChoiceId());
                } else if (tmpQuestion instanceof TrueFalseDO) {
                    // 判断题
                    TrueFalseDO trueFalseDO = (TrueFalseDO) tmpQuestion;
                    studentpaperConfigQuestionDO.setQuestionId(trueFalseDO.getTfId());
                } else if (tmpQuestion instanceof CompletionDO) {
                    // 填空题
                    CompletionDO completionDO = (CompletionDO) tmpQuestion;
                    studentpaperConfigQuestionDO.setQuestionId(completionDO.getCompId());
                } else if (tmpQuestion instanceof CodeDO) {
                    // 编程题
                    CodeDO codeDO = (CodeDO) tmpQuestion;
                    studentpaperConfigQuestionDO.setQuestionId(codeDO.getCodeId());
                } else {
                    // 其他题
                    QuestionDO questionDO = (QuestionDO) tmpQuestion;
                    studentpaperConfigQuestionDO.setQuestionId(questionDO.getQuestionId());
                }
                configQuestionDOList.add(studentpaperConfigQuestionDO);
            }
        }

        // 更改试卷状态：设置总分、难度系数，生成试卷、修改状态、题量
        BigDecimal totalScore = configList.stream().map(StudentPaperConfigDO::getConfigScore).reduce(BigDecimal::add).get();
        BigDecimal configDiff = configList.stream().map(e -> e.getConfigScore().multiply(new BigDecimal(e.getConfigDifficulty()))).reduce(BigDecimal::add).get();
        int questionNum = configList.stream().mapToInt(StudentPaperConfigDO::getConfigQuestionNum).sum();
        studentpaperDO.setPaperScore(totalScore);
        studentpaperDO.setPaperQuestionNum(questionNum);

        // 计算难度系数
        BigDecimal paperDiff = configDiff.divide(totalScore, NumberConstant.DEFAULT_DECIMAL_RETAIN, BigDecimal.ROUND_HALF_DOWN);
        studentpaperDO.setPaperDifficulty(paperDiff);
        studentpaperDO.setPaperFlag(PaperEnum.NOT_USE.getCode());
        // 设置所属学生
        studentpaperDO.setPaperStudent(studentId);

        // 插入题目数据
        studentPaperConfigQuestionService.saveBatch(configQuestionDOList);

        // 插入配置数据
        studentPaperConfigMapper.saveBatch(configList);

        // 修改试卷信息
        log.info("studentDo : [{}]",studentpaperDO.toString());
        saveOrUpdate(studentpaperDO);

        log.info("组卷结束");

    }

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
        List<PaperDO> list = studentPaperMapper.getListByPage(page);

        page.setList(list);
        Integer totalCount = studentPaperMapper.getCountByPage(page);
        page.setTotalCount(totalCount);
        // 计算总页数
        page.setTotalPage((int) Math.ceil((page.getTotalCount() * 1.0) / page.getCurrentCount()));
        return page;
    }

}
