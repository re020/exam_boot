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
 * ??????????????? ???????????????
 * </p>
 *
 * @author ?????????
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
     * ????????????????????????
     *
     * @param exPaperDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void gaExam(ExPaperDTO exPaperDTO) throws Exception {
        log.info("?????????????????? exPaperDTO: [{}]", exPaperDTO);
        if(exPaperDTO.getConfigList() == null){
            throw new ExamException("???????????????????????????????????????????????????????????????");
        }

        // ???????????????????????????
        List<String> stuId = exPaperDTO.getStuId();

        List<String> stuIds = studentPaperMapper.selectStuByexamId(exPaperDTO.getExamId());
        String paperId = exPaperDTO.getPaperId();
        PaperDO paperDO = paperMapper.selectById(paperId);
        if(paperDO == null){
            throw new ExamException("????????????????????????????????????????????????????????????");
        }
        log.info("????????????????????? :[{}]",paperDO);
        stuId.removeAll(stuIds);

        for (String student : stuId) {
            log.info("?????????????????? id: [{}]",student);
            // ????????????
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
                // ????????????????????????????????????
                PaperConfigDO config = population.getFitness(0);
                StudentPaperConfigDO stuConfig = new StudentPaperConfigDO();
                BeanUtils.copyProperties(config, stuConfig);
                log.info("stuConfig: [{}]",config);
                studentPaperDO.getConfigList().add(stuConfig);
            }

            // ????????????????????????????????????
            saveGaExam(studentPaperDO, student);
            log.info("????????????");

            log.info("?????????????????????");
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
        // ???????????????????????????
        StudentPaperDO paper = studentPaperMapper.getQuestionById(paperId);
        if(paper == null){
            return null;
        }
        List<StudentPaperConfigDO> configList = paper.getConfigList();
        HashMap<String, List<String>> configMap = Maps.newHashMap();
        QueryWrapper<StudentAnswerDO> wrapper = new QueryWrapper<>();
        //????????????????????????
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
        // ???????????????????????????????????????
        List<StudentPaperConfigDO> newConfigList = studentPaperConfigMapper.getQuestionNum(paperId);
        for (StudentPaperConfigDO paperConfigDO : newConfigList) {
            String key = paperConfigDO.getConfigType();
            List<String> questionIds = configMap.get(key);
            // ??????id??????
            if (key.equals(TypeEnum.ONE_CHOICE.getCode().toString()) || key.equals(TypeEnum.MANY_CHOICE.getCode().toString())) {
                // ?????????
                Map<String, Object> paramsMap = Maps.newHashMap();
                paramsMap.put("choiceType", key);
                paramsMap.put("choiceIds", questionIds);
                List<ChoiceDO> choiceList = choiceMapper.getListByMap(paramsMap);
                // ????????????????????????
                choiceList.forEach(e -> {
                    List<String> numberList = e.getChoiceAnswer().stream().filter(ChoiceAnswerDO::getAnswerTrue)
                            .map(ChoiceAnswerDO::getAnswerNumber).collect(Collectors.toList());
                    e.setChoiceTrue(StringUtils.join(numberList, ", "));
                    query.eq("answer_question",e.getChoiceId());
                    e.setAnswerContent(studentAnswerMapper.selectOne(wrapper));
                });
                paperConfigDO.setQuestionDetailList(choiceList);
            } else if (key.equals(TypeEnum.JUDGEMENT.getCode().toString())) {
                // ?????????
                List<TrueFalseDO> trueFalseDOList = trueFalseMapper.selectBatchIds(questionIds);
                trueFalseDOList.forEach(e ->{
                    query.eq("answer_question",e.getTfId());
                    e.setAnswerContent(studentAnswerMapper.selectOne(wrapper));
                });
                paperConfigDO.setQuestionDetailList(trueFalseDOList);
            } else if (key.equals(TypeEnum.COMPLETION.getCode().toString())) {
                // ?????????
                List<CompletionDO> completionDOList = completionMapper.getByIds(questionIds);
                completionDOList.forEach(e ->{
                    query.eq("answer_question",e.getCompId());
                    e.setAnswerContent(studentAnswerMapper.selectOne(wrapper));
                });
                paperConfigDO.setQuestionDetailList(completionDOList);
            } else if (key.equals(TypeEnum.PROGRAMMING.getCode().toString())) {
                // ?????????
                List<CodeDO> codeDOList = codeMapper.getByIds(questionIds);
                codeDOList.forEach(e ->{
                    query.eq("answer_question",e.getCodeId());
                    e.setAnswerContent(studentAnswerMapper.selectOne(wrapper));
                });
                paperConfigDO.setQuestionDetailList(codeDOList);
            } else {
                // ?????????
                List<QuestionDO> questionDOList = questionMapper.getByIds(questionIds);
                questionDOList.forEach(e ->{
                    query.eq("answer_question",e.getQuestionId());
                    e.setAnswerContent(studentAnswerMapper.selectOne(wrapper));
                });
                paperConfigDO.setQuestionDetailList(questionDOList);
            }
        }
        // ????????????
        newConfigList.sort(Comparator.comparingLong(c -> Long.parseLong(c.getConfigType())));
        // ??????????????????
        paper.setConfigList(newConfigList);
        return paper;
    }

    @Override
    public void correctSubmit(String paperId) {
        // ????????????id????????????id
        QueryWrapper<StudentPaperConfigDO> wrapper = new QueryWrapper<>();
        wrapper.select("config_id")
                .eq("config_paper",paperId)
                .eq("config_delete", DeleteEnum.NOT_DELETE.getCode());
        List<StudentPaperConfigDO> configIds = studentPaperConfigMapper.selectList(wrapper);
        // ?????????????????????-?????????????????????????????????????????????
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
        log.info("??????????????????:{}",result > 0);
    }


    /**
     * ????????????????????????????????????
     *
     * @param studentpaperDO
     */
    @Transactional(rollbackFor = Exception.class)
    protected void saveGaExam(StudentPaperDO studentpaperDO, String studentId) throws Exception {
        log.info("?????????????????? ??????id: [{}]",studentpaperDO.getPaperId());
        String paperId = studentpaperDO.getPaperId();

        List<StudentPaperConfigDO> configList = studentpaperDO.getConfigList();

        List<StudentPaperConfigQuestionDO> configQuestionDOList = Lists.newArrayList();
        for (StudentPaperConfigDO config : configList) {
            log.info("???????????? config: [{}]",config.getConfigQuestionNum());
            config.setConfigPaper(paperId);
            List questionList = config.getQuestionDetailList();
            config.setConfigQuestionNum(questionList.size());

            for (Object tmpQuestion : questionList) {

                StudentPaperConfigQuestionDO studentpaperConfigQuestionDO = new StudentPaperConfigQuestionDO();
                studentpaperConfigQuestionDO.setId(idWorker.nextId() + "");
                studentpaperConfigQuestionDO.setQuestionConfig(config.getConfigId());
                if (tmpQuestion instanceof ChoiceDO) {
                    // ?????????
                    ChoiceDO choiceDO = (ChoiceDO) tmpQuestion;
                    studentpaperConfigQuestionDO.setQuestionId(choiceDO.getChoiceId());
                } else if (tmpQuestion instanceof TrueFalseDO) {
                    // ?????????
                    TrueFalseDO trueFalseDO = (TrueFalseDO) tmpQuestion;
                    studentpaperConfigQuestionDO.setQuestionId(trueFalseDO.getTfId());
                } else if (tmpQuestion instanceof CompletionDO) {
                    // ?????????
                    CompletionDO completionDO = (CompletionDO) tmpQuestion;
                    studentpaperConfigQuestionDO.setQuestionId(completionDO.getCompId());
                } else if (tmpQuestion instanceof CodeDO) {
                    // ?????????
                    CodeDO codeDO = (CodeDO) tmpQuestion;
                    studentpaperConfigQuestionDO.setQuestionId(codeDO.getCodeId());
                } else {
                    // ?????????
                    QuestionDO questionDO = (QuestionDO) tmpQuestion;
                    studentpaperConfigQuestionDO.setQuestionId(questionDO.getQuestionId());
                }
                configQuestionDOList.add(studentpaperConfigQuestionDO);
            }
        }

        // ???????????????????????????????????????????????????????????????????????????????????????
        BigDecimal totalScore = configList.stream().map(StudentPaperConfigDO::getConfigScore).reduce(BigDecimal::add).get();
        BigDecimal configDiff = configList.stream().map(e -> e.getConfigScore().multiply(new BigDecimal(e.getConfigDifficulty()))).reduce(BigDecimal::add).get();
        int questionNum = configList.stream().mapToInt(StudentPaperConfigDO::getConfigQuestionNum).sum();
        studentpaperDO.setPaperScore(totalScore);
        studentpaperDO.setPaperQuestionNum(questionNum);

        // ??????????????????
        BigDecimal paperDiff = configDiff.divide(totalScore, NumberConstant.DEFAULT_DECIMAL_RETAIN, BigDecimal.ROUND_HALF_DOWN);
        studentpaperDO.setPaperDifficulty(paperDiff);
        studentpaperDO.setPaperFlag(PaperEnum.NOT_USE.getCode());
        // ??????????????????
        studentpaperDO.setPaperStudent(studentId);

        // ??????????????????
        studentPaperConfigQuestionService.saveBatch(configQuestionDOList);

        // ??????????????????
        studentPaperConfigMapper.saveBatch(configList);

        // ??????????????????
        log.info("studentDo : [{}]",studentpaperDO.toString());
        saveOrUpdate(studentpaperDO);

        log.info("????????????");

    }

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
        List<PaperDO> list = studentPaperMapper.getListByPage(page);

        page.setList(list);
        Integer totalCount = studentPaperMapper.getCountByPage(page);
        page.setTotalCount(totalCount);
        // ???????????????
        page.setTotalPage((int) Math.ceil((page.getTotalCount() * 1.0) / page.getCurrentCount()));
        return page;
    }

}
