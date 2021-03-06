package com.exam.ts.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.core.constant.CoreConstant;
import com.exam.core.constant.ExamEnum;
import com.exam.core.constant.GaConstant;
import com.exam.core.constant.MqConstant;
import com.exam.core.constant.PaperEnum;
import com.exam.core.constant.RedisConstant;
import com.exam.core.constant.ResultEnum;
import com.exam.core.constant.RoomEnum;
import com.exam.core.constant.SelectEnum;
import com.exam.core.constant.SubmitEnum;
import com.exam.core.constant.TypeEnum;
import com.exam.core.exception.ExamException;
import com.exam.core.pojo.Page;
import com.exam.core.utils.DateUtils;
import com.exam.core.utils.IdWorker;
import com.exam.core.utils.ShiroUtils;
import com.exam.ex.dto.GaConfigDTO;
import com.exam.ex.dto.GaPaperDTO;
import com.exam.ex.ga.Generation;
import com.exam.ex.ga.Population;
import com.exam.ex.mapper.ChoiceMapper;
import com.exam.ex.mapper.CodeMapper;
import com.exam.ex.mapper.CompletionMapper;
import com.exam.ex.mapper.PaperMapper;
import com.exam.ex.mapper.QuestionMapper;
import com.exam.ex.mapper.TrueFalseMapper;
import com.exam.ex.pojo.ChoiceAnswerDO;
import com.exam.ex.pojo.ChoiceDO;
import com.exam.ex.pojo.CodeDO;
import com.exam.ex.pojo.CompletionDO;
import com.exam.ex.pojo.PaperConfigDO;
import com.exam.ex.pojo.PaperDO;
import com.exam.ex.pojo.QuestionDO;
import com.exam.ex.pojo.StudentDO;
import com.exam.ex.pojo.TeacherDO;
import com.exam.ex.pojo.TrueFalseDO;
import com.exam.ts.mapper.ExamMapper;
import com.exam.ts.mapper.ExamStudentMapper;
import com.exam.ts.mapper.ExamTeacherMapper;
import com.exam.ts.mapper.RoomMapper;
import com.exam.ts.mapper.StudentPaperConfigMapper;
import com.exam.ts.mapper.StudentPaperConfigQuestionMapper;
import com.exam.ts.mapper.StudentPaperMapper;
import com.exam.ts.pojo.DTO.CommitDTO;
import com.exam.ts.pojo.ExamDO;
import com.exam.ts.pojo.ExamStudentDO;
import com.exam.ts.pojo.ExamTeacherDO;
import com.exam.ts.pojo.RoomDO;
import com.exam.ts.pojo.StudentAnswerDO;
import com.exam.ts.pojo.StudentPaperConfigDO;
import com.exam.ts.pojo.StudentPaperConfigQuestionDO;
import com.exam.ts.pojo.StudentPaperDO;
import com.exam.ts.pojo.DTO.ExamDTO;
import com.exam.ts.pojo.DTO.TopicDTO;
import com.exam.ts.service.ExamService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.naming.ldap.Rdn;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * ????????? ???????????????
 * </p>
 *
 * @author ?????????
 * @since 2019-05-24
 */
@Slf4j
@Service
public class ExamServiceImpl extends ServiceImpl<ExamMapper, ExamDO> implements ExamService {

    @Autowired
    private ExamMapper examMapper;
    @Autowired
    private RoomMapper roomMapper;
    @Autowired
    private PaperMapper paperMapper;
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
    private ExamStudentMapper examStudentMapper;
    @Autowired
    private ExamTeacherMapper examTeacherMapper;
    @Autowired
    private StudentPaperMapper studentPaperMapper;
    @Autowired
    private StudentPaperConfigMapper studentPaperConfigMapper;
    @Autowired
    private StudentPaperConfigQuestionMapper studentPaperConfigQuestionMapper;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * ????????????
     *
     * @param exam
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addExam(ExamDO exam) {
        // ????????????
        exam.setExamId(idWorker.nextId() + "");
        exam.setExamCreateBy(ShiroUtils.getLoginTeacher().getTeacherId());
        // ???????????????
        exam.setExamState(ExamEnum.NOT_STARTED.getCode());
        exam.setExamCreateTime(DateUtils.newDateTime());
        // ??????
        examMapper.insert(exam);

        // ????????????????????????????????????????????????
        RoomDO room = roomMapper.selectById(exam.getExamRoom());
        room.setRoomState(RoomEnum.APPLY.getCode());
        roomMapper.updateById(room);
    }

    /**
     * ????????????
     *
     * @param page
     * @return
     */
    @Override
    public Page<ExamDO> getByPage(Page<ExamDO> page) {
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
        List<ExamDO> list = examMapper.getListByPage(page);
        page.setList(list);
        Integer totalCount = examMapper.getCountByPage(page);
        page.setTotalCount(totalCount);
        // ???????????????
        page.setTotalPage((int) Math.ceil((page.getTotalCount() * 1.0) / page.getCurrentCount()));
        return page;
    }

    /**
     * ???????????????????????????????????????
     *
     * @param exam
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateExam(ExamDO exam) {
        // ??????????????????
        ExamDO old = examMapper.selectById(exam.getExamId());
        String oldRoomId = old.getExamRoom();
        // ??????????????????id???????????????id???????????????????????????
        if (!oldRoomId.equals(exam.getExamRoom())) {
            // ????????????????????????
            RoomDO oldRoom = roomMapper.selectById(oldRoomId);
            oldRoom.setRoomState(RoomEnum.FREE.getCode());
            roomMapper.updateById(oldRoom);
            // ???????????????????????????
            RoomDO newRoom = roomMapper.selectById(exam.getExamRoom());
            newRoom.setRoomState(RoomEnum.APPLY.getCode());
            roomMapper.updateById(newRoom);
        }
        examMapper.updateById(exam);
    }

    /**
     * ???????????????????????????????????????
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteExam(String id) {
        // ??????
        ExamDO examDO = examMapper.selectById(id);
        RoomDO roomDO = roomMapper.selectById(examDO.getExamRoom());
        roomDO.setRoomState(RoomEnum.FREE.getCode());
        roomMapper.updateById(roomDO);
        examMapper.deleteById(id);
    }

    /**
     * ??????id??????????????????
     *
     * @param id
     * @return
     */
    @Override
    public ExamDO getInfo(String id) {
        // ??????????????????
        ExamDO examDo = examMapper.selectById(id);
        // ????????????
        List<ExamStudentDO> studentList = examStudentMapper.getListByExam(id);
        examDo.setStudentList(studentList);
        // ??????????????????
        PaperDO paperDO = paperMapper.selectById(examDo.getExamPaper());
        examDo.setPaper(paperDO);
        // ??????????????????
        List<ExamTeacherDO> teacherList = examTeacherMapper.getByExamId(id);
        examDo.setTeacherList(teacherList);
        // ????????????
        RoomDO roomDO = roomMapper.selectById(examDo.getExamRoom());
        examDo.setRoom(roomDO);
        return examDo;
    }

    /**
     * ??????????????????
     *
     * @param paperDTO
     */
    @Override
    public void createPaper(GaPaperDTO paperDTO) throws ExamException {
        // ????????????id
        String examId = paperDTO.getExamId();
        // ??????????????????????????????
        List<ExamStudentDO> studentList = examStudentMapper.getListByExam(examId);
        // ????????????
        PaperDO sourcePaper = paperMapper.selectById(paperDTO.getPaperId());
        for (ExamStudentDO examStudentDO : studentList) {
            // ?????????????????????????????????????????????
            StudentDO student = examStudentDO.getStudent();
            StudentPaperDO studentPaperDO = new StudentPaperDO();


        }
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


    @Override
    public StudentPaperDO startExam(String examId) throws ExamException {
        // ??????????????????????????????
        ExamDO examDO = examMapper.selectById(examId);
        if(examDO == null){
            throw new ExamException("??????????????????");
        }
        if(examDO.getExamState().equals(ExamEnum.ENDED.getCode()) ||
                examDO.getExamState().equals(ExamEnum.NOT_STARTED.getCode())){
            throw new  ExamException("???????????????????????????");
        }
        StudentDO loginStudent = ShiroUtils.getLoginStudent();
        // ????????????id?????????????????????id
        StudentPaperDO paper = studentPaperMapper.getQuestion(examId, loginStudent.getStuId());
        if (paper == null) {
            throw new  ExamException("???????????????????????????");
        }
        List<StudentPaperConfigDO> configList = paper.getConfigList();

        // ????????????????????????????????? ????????????ids
        HashMap<String, List<String>> configMap = Maps.newHashMap();
        for (StudentPaperConfigDO configDO : configList) {
            List<String> questionIds = configMap.getOrDefault(configDO.getType(), Lists.newArrayList());
            questionIds.addAll(configDO.getQuestionList().stream().map(StudentPaperConfigQuestionDO::getQuestionId)
                    .collect(Collectors.toList()));
            configMap.put(configDO.getConfigType(), questionIds);
        }
        // ???????????????????????????
        List<StudentPaperConfigDO> newConfigList = studentPaperConfigMapper.getQuestionNum(paper.getPaperId());
        if (newConfigList == null) {
            throw new ExamException(ResultEnum.NO_QUESTION);
        }
        // ????????????????????????????????????????????????
        for (StudentPaperConfigDO studentPaperConfigDO : newConfigList) {
            String key = studentPaperConfigDO.getConfigType();
            // ??????id??????
            List<String> questionIds = configMap.get(key);
            //????????????id??????????????????
                Map<String, Object> paramsMap = Maps.newHashMap();
                paramsMap.put("stuId",loginStudent.getStuId());
                paramsMap.put("paperId",paper.getPaperId());

            if (key.equals(TypeEnum.ONE_CHOICE.getCode().toString()) || key.equals(TypeEnum.MANY_CHOICE.getCode().toString())) {
                // ?????????
                paramsMap.put("choiceType", key);
                paramsMap.put("choiceIds", questionIds);

                List<ChoiceDO> choiceList = choiceMapper.getListByMapAnswer(paramsMap);

                studentPaperConfigDO.setQuestionDetailList(choiceList);
            } else if (key.equals(TypeEnum.JUDGEMENT.getCode().toString())) {
                // ?????????
                paramsMap.put("judeIds", questionIds);
                List<TrueFalseDO> trueFalseDOList = trueFalseMapper.getListByMapAnswer(paramsMap);
                studentPaperConfigDO.setQuestionDetailList(trueFalseDOList);

            } else if (key.equals(TypeEnum.COMPLETION.getCode().toString())) {
                // ?????????
                paramsMap.put("completionIds", questionIds);
                List<CompletionDO> completionDOList = completionMapper.getListByMapAnswer(paramsMap);
                studentPaperConfigDO.setQuestionDetailList(completionDOList);
            } else if (key.equals(TypeEnum.PROGRAMMING.getCode().toString())) {
                // ?????????
                paramsMap.put("programIds", questionIds);
                List<CodeDO> codeDOList = codeMapper.getListByMapWithAnswer(paramsMap);
                studentPaperConfigDO.setQuestionDetailList(codeDOList);
            } else {
                // ?????????
                paramsMap.put("Ids", questionIds);
                List<QuestionDO> questionDOList = questionMapper.getListByMapWithAnswer(paramsMap);
                studentPaperConfigDO.setQuestionDetailList(questionDOList);
            }
        }
        newConfigList.sort(Comparator.comparingLong(c -> Long.parseLong(c.getConfigType())));
        // ??????????????????
        paper.setConfigList(newConfigList);
        return paper;
    }

    @Override
    public boolean submit(CommitDTO commitDTO) throws ExamException {
        // ???????????????????????????
        QueryWrapper<StudentPaperDO> wrapper = new QueryWrapper<>();
        wrapper.eq("paper_id", commitDTO.getPaperId());
        StudentPaperDO paper = studentPaperMapper.selectOne(wrapper);
        if (paper == null) {
            throw new ExamException("?????????????????????");
        }
        // ????????????????????????????????????????????????????????????????????????
        Integer flag = paper.getPaperFlag();
        if (PaperEnum.NOT_USE.getCode().equals(flag)) {
            throw new ExamException(ResultEnum.NO_USE);
        }

        if (PaperEnum.COMMIT.getCode().equals(flag)) {
            throw new ExamException(ResultEnum.COMMITED);
        }

        if (PaperEnum.LOADING.getCode().equals(flag)) {
            throw new ExamException(ResultEnum.LOADED);
        }

        if (PaperEnum.FINISH.getCode().equals(flag)) {
            throw new ExamException(ResultEnum.FINISHED);
        }
        //1. ????????????
        rabbitTemplate.convertAndSend(MqConstant.SUBMIT_EXAM_QUEUE, commitDTO);
        // ????????????????????????
        StudentPaperDO studentPaperDO = new StudentPaperDO();
        studentPaperDO.setPaperId(commitDTO.getPaperId());
        studentPaperDO.setPaperFlag(PaperEnum.LOADING.getCode());
        studentPaperDO.setPaperStudent(commitDTO.getStuId());
        return studentPaperMapper.updateById(studentPaperDO) > 0;
    }

    @Override
    public void submit_tmp(CommitDTO commitDTO) throws ExamException {
        StudentPaperDO studentPaperDO = studentPaperMapper.selectById(commitDTO.getPaperId());
        Integer flag = studentPaperDO.getPaperFlag();
        if (PaperEnum.NOT_USE.getCode().equals(flag)) {
            throw new ExamException(ResultEnum.NO_USE);
        }
        if (PaperEnum.COMMIT.getCode().equals(flag)) {
            throw new ExamException(ResultEnum.COMMITED);
        }
        studentPaperDO.setPaperStudentScore(commitDTO.getGrade());
        studentPaperDO.setPaperFlag(2);
        studentPaperMapper.updateById(studentPaperDO);
    }

    private void saveGaPaper(PaperDO paperDO) {
        paperMapper.insert(paperDO);
    }
}
