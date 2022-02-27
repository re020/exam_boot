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
 * 考试表 服务实现类
 * </p>
 *
 * @author 杨德石
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
     * 添加考试
     *
     * @param exam
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addExam(ExamDO exam) {
        // 设置属性
        exam.setExamId(idWorker.nextId() + "");
        exam.setExamCreateBy(ShiroUtils.getLoginTeacher().getTeacherId());
        // 状态未开始
        exam.setExamState(ExamEnum.NOT_STARTED.getCode());
        exam.setExamCreateTime(DateUtils.newDateTime());
        // 存入
        examMapper.insert(exam);

        // 查询对应的考场，将状态改为已占用
        RoomDO room = roomMapper.selectById(exam.getExamRoom());
        room.setRoomState(RoomEnum.APPLY.getCode());
        roomMapper.updateById(room);
    }

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    @Override
    public Page<ExamDO> getByPage(Page<ExamDO> page) {
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
        List<ExamDO> list = examMapper.getListByPage(page);
        page.setList(list);
        Integer totalCount = examMapper.getCountByPage(page);
        page.setTotalCount(totalCount);
        // 计算总页数
        page.setTotalPage((int) Math.ceil((page.getTotalCount() * 1.0) / page.getCurrentCount()));
        return page;
    }

    /**
     * 修改考试，同时更新考场状态
     *
     * @param exam
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateExam(ExamDO exam) {
        // 查询原始数据
        ExamDO old = examMapper.selectById(exam.getExamId());
        String oldRoomId = old.getExamRoom();
        // 如果旧的考场id和新的考场id不一样，就更新考场
        if (!oldRoomId.equals(exam.getExamRoom())) {
            // 旧考场更新为空闲
            RoomDO oldRoom = roomMapper.selectById(oldRoomId);
            oldRoom.setRoomState(RoomEnum.FREE.getCode());
            roomMapper.updateById(oldRoom);
            // 新考场更新为已占用
            RoomDO newRoom = roomMapper.selectById(exam.getExamRoom());
            newRoom.setRoomState(RoomEnum.APPLY.getCode());
            roomMapper.updateById(newRoom);
        }
        examMapper.updateById(exam);
    }

    /**
     * 删除考试，同时更新考场状态
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteExam(String id) {
        // 查询
        ExamDO examDO = examMapper.selectById(id);
        RoomDO roomDO = roomMapper.selectById(examDO.getExamRoom());
        roomDO.setRoomState(RoomEnum.FREE.getCode());
        roomMapper.updateById(roomDO);
        examMapper.deleteById(id);
    }

    /**
     * 根据id查询考试信息
     *
     * @param id
     * @return
     */
    @Override
    public ExamDO getInfo(String id) {
        // 先查基本信息
        ExamDO examDo = examMapper.selectById(id);
        // 查询考生
        List<ExamStudentDO> studentList = examStudentMapper.getListByExam(id);
        examDo.setStudentList(studentList);
        // 查询试卷信息
        PaperDO paperDO = paperMapper.selectById(examDo.getExamPaper());
        examDo.setPaper(paperDO);
        // 查询监考教师
        List<ExamTeacherDO> teacherList = examTeacherMapper.getByExamId(id);
        examDo.setTeacherList(teacherList);
        // 查询考场
        RoomDO roomDO = roomMapper.selectById(examDo.getExamRoom());
        examDo.setRoom(roomDO);
        return examDo;
    }

    /**
     * 智能生成试卷
     *
     * @param paperDTO
     */
    @Override
    public void createPaper(GaPaperDTO paperDTO) throws ExamException {
        // 获取考试id
        String examId = paperDTO.getExamId();
        // 查询该考试的所有学生
        List<ExamStudentDO> studentList = examStudentMapper.getListByExam(examId);
        // 查询试卷
        PaperDO sourcePaper = paperMapper.selectById(paperDTO.getPaperId());
        for (ExamStudentDO examStudentDO : studentList) {
            // 获取学生，为每一位学生生成试卷
            StudentDO student = examStudentDO.getStudent();
            StudentPaperDO studentPaperDO = new StudentPaperDO();


        }
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


    @Override
    public StudentPaperDO startExam(String examId) throws ExamException {
        // 如果结束了则不能考试
        ExamDO examDO = examMapper.selectById(examId);
        if(examDO == null){
            throw new ExamException("没有这个考试");
        }
        if(examDO.getExamState().equals(ExamEnum.ENDED.getCode()) ||
                examDO.getExamState().equals(ExamEnum.NOT_STARTED.getCode())){
            throw new  ExamException("考试未开始或已结束");
        }
        StudentDO loginStudent = ShiroUtils.getLoginStudent();
        // 根据考试id和学生得到试卷id
        StudentPaperDO paper = studentPaperMapper.getQuestion(examId, loginStudent.getStuId());
        if (paper == null) {
            throw new  ExamException("该考试下找不到试卷");
        }
        List<StudentPaperConfigDO> configList = paper.getConfigList();

        // 将试卷根据题型进行分组 选择题：ids
        HashMap<String, List<String>> configMap = Maps.newHashMap();
        for (StudentPaperConfigDO configDO : configList) {
            List<String> questionIds = configMap.getOrDefault(configDO.getType(), Lists.newArrayList());
            questionIds.addAll(configDO.getQuestionList().stream().map(StudentPaperConfigQuestionDO::getQuestionId)
                    .collect(Collectors.toList()));
            configMap.put(configDO.getConfigType(), questionIds);
        }
        // 得到指定题型的总数
        List<StudentPaperConfigDO> newConfigList = studentPaperConfigMapper.getQuestionNum(paper.getPaperId());
        if (newConfigList == null) {
            throw new ExamException(ResultEnum.NO_QUESTION);
        }
        // 查询每个题型分支，具体的题目明细
        for (StudentPaperConfigDO studentPaperConfigDO : newConfigList) {
            String key = studentPaperConfigDO.getConfigType();
            // 获取id列表
            List<String> questionIds = configMap.get(key);
            //根据题目id找寻做题记录
                Map<String, Object> paramsMap = Maps.newHashMap();
                paramsMap.put("stuId",loginStudent.getStuId());
                paramsMap.put("paperId",paper.getPaperId());

            if (key.equals(TypeEnum.ONE_CHOICE.getCode().toString()) || key.equals(TypeEnum.MANY_CHOICE.getCode().toString())) {
                // 选择题
                paramsMap.put("choiceType", key);
                paramsMap.put("choiceIds", questionIds);

                List<ChoiceDO> choiceList = choiceMapper.getListByMapAnswer(paramsMap);

                studentPaperConfigDO.setQuestionDetailList(choiceList);
            } else if (key.equals(TypeEnum.JUDGEMENT.getCode().toString())) {
                // 判断题
                paramsMap.put("judeIds", questionIds);
                List<TrueFalseDO> trueFalseDOList = trueFalseMapper.getListByMapAnswer(paramsMap);
                studentPaperConfigDO.setQuestionDetailList(trueFalseDOList);

            } else if (key.equals(TypeEnum.COMPLETION.getCode().toString())) {
                // 填空题
                paramsMap.put("completionIds", questionIds);
                List<CompletionDO> completionDOList = completionMapper.getListByMapAnswer(paramsMap);
                studentPaperConfigDO.setQuestionDetailList(completionDOList);
            } else if (key.equals(TypeEnum.PROGRAMMING.getCode().toString())) {
                // 编程题
                paramsMap.put("programIds", questionIds);
                List<CodeDO> codeDOList = codeMapper.getListByMapWithAnswer(paramsMap);
                studentPaperConfigDO.setQuestionDetailList(codeDOList);
            } else {
                // 其他题
                paramsMap.put("Ids", questionIds);
                List<QuestionDO> questionDOList = questionMapper.getListByMapWithAnswer(paramsMap);
                studentPaperConfigDO.setQuestionDetailList(questionDOList);
            }
        }
        newConfigList.sort(Comparator.comparingLong(c -> Long.parseLong(c.getConfigType())));
        // 设置新的配置
        paper.setConfigList(newConfigList);
        return paper;
    }

    @Override
    public boolean submit(CommitDTO commitDTO) throws ExamException {
        // 找到学生对应的卷子
        QueryWrapper<StudentPaperDO> wrapper = new QueryWrapper<>();
        wrapper.eq("paper_id", commitDTO.getPaperId());
        StudentPaperDO paper = studentPaperMapper.selectOne(wrapper);
        if (paper == null) {
            throw new ExamException("查询不到此试卷");
        }
        // 查看当前试卷的状态，是否已经批改完毕或者正在批改
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
        //1. 发送消息
        rabbitTemplate.convertAndSend(MqConstant.SUBMIT_EXAM_QUEUE, commitDTO);
        // 更改学生试卷状态
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
