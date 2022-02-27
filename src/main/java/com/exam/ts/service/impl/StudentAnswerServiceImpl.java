package com.exam.ts.service.impl;

import com.exam.core.utils.IdWorker;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exam.core.constant.MqConstant;
import com.exam.core.constant.PaperEnum;
import com.exam.core.constant.TypeEnum;
import com.exam.core.exception.ExamException;
import com.exam.core.pojo.Page;
import com.exam.core.utils.ShiroUtils;
import com.exam.ex.mapper.ChoiceMapper;
import com.exam.ex.mapper.CodeMapper;
import com.exam.ex.mapper.CompletionMapper;
import com.exam.ex.mapper.QuestionMapper;
import com.exam.ex.mapper.TrueFalseMapper;
import com.exam.ex.pojo.ChoiceDO;
import com.exam.ex.pojo.CodeDO;
import com.exam.ex.pojo.CompletionDO;
import com.exam.ex.pojo.QuestionDO;
import com.exam.ex.pojo.StudentDO;
import com.exam.ex.pojo.TrueFalseDO;
import com.exam.ex.service.ChoiceService;
import com.exam.ts.mapper.StudentPaperConfigMapper;
import com.exam.ts.mapper.StudentPaperMapper;
import com.exam.ts.pojo.StudentAnswerDO;
import com.exam.ts.mapper.StudentAnswerMapper;
import com.exam.ts.pojo.StudentPaperConfigDO;
import com.exam.ts.pojo.StudentPaperDO;
import com.exam.ts.pojo.DTO.AnswerDTO;
import com.exam.ts.pojo.DTO.TopicDTO;
import com.exam.ts.service.StudentAnswerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.ts.service.StudentPaperConfigScoreService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 学生做题答案表 服务实现类
 * </p>
 *
 * @author 杨德石
 * @since 2019-05-24
 */
@Slf4j
@Service
public class StudentAnswerServiceImpl extends ServiceImpl<StudentAnswerMapper, StudentAnswerDO> implements StudentAnswerService {

    @Autowired
    private StudentPaperConfigMapper studentPaperConfigMapper;
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
    private StudentPaperMapper studentPaperMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private StudentAnswerMapper studentAnswerMapper;
    @Autowired
    private ChoiceService choiceService;
    @Autowired
    private StudentPaperConfigScoreService studentPaperConfigScoreService;
    @Autowired
    private IdWorker idWorker;


    /**
     * 通过mq异步处理
     * 1. 先发送消息
     * 2. 发送消息成功，更新提交状态，发送失败，返回提交失败
     *
     * @param studentAnswerDO
     * @return boolean
     * @author lth
     */
    @Override
    public boolean submit(StudentAnswerDO studentAnswerDO) {

        //1. 发送消息
        rabbitTemplate.convertAndSend(MqConstant.SUBMIT_EXAM_QUEUE, studentAnswerDO);
        // 更改学生试卷状态
        StudentPaperDO studentPaperDO = new StudentPaperDO();
        studentPaperDO.setPaperId(studentAnswerDO.getAnswerPaper());
        studentPaperDO.setPaperFlag(PaperEnum.LOADING.getCode());
        studentPaperDO.setPaperStudent(studentAnswerDO.getAnswerStudent());
        return studentPaperMapper.updateById(studentPaperDO) > 0;
    }

    /**
     * 功能描述  计算成绩等
     *
     * @return void
     * @author lth
     */
    @Override
    public void asyncHandler(StudentAnswerDO stuAnswerDO) {
        long start = System.currentTimeMillis();
        // 查找该学生的做过的题
        List<StudentAnswerDO> studentAnswerDOS = studentAnswerMapper.selectcofigList(stuAnswerDO);
        //TODO async
        log.info("studentDo : [{}]", studentAnswerDOS);
        // 将题目进行分类 计算
        Map<Integer, List<StudentAnswerDO>> classify = Maps.newHashMap();
        studentAnswerDOS
                .stream()
                .collect(Collectors.groupingBy(StudentAnswerDO::getType, Collectors.toList()))
                .forEach((type, temp) -> {
                    // 匹配类型
                    TypeEnum subjectType = TypeEnum.match(type);
                    // 进行筛选
                    if (subjectType != null) {
                        switch (subjectType) {
                            case ONE_CHOICE:
                                classify.put(TypeEnum.ONE_CHOICE.getCode(), temp);
                                break;
                            case MANY_CHOICE:
                                classify.put(TypeEnum.MANY_CHOICE.getCode(), temp);
                                break;
                            case JUDGEMENT:
                                classify.put(TypeEnum.JUDGEMENT.getCode(), temp);
                                break;
                            case COMPLETION:
                                classify.put(TypeEnum.COMPLETION.getCode(), temp);
                                break;
                            case PROGRAMMING:
                                classify.put(TypeEnum.PROGRAMMING.getCode(), temp);
                                break;
                            case OTHER:
                                classify.put(TypeEnum.OTHER.getCode(), temp);
                                break;
                            default:
                                break;
                        }
                    }
                });
        // 非客观题的由老师进行修改 客观题  统计选择 多项选择 判断
        if (classify.containsKey(TypeEnum.ONE_CHOICE.getCode())) {
            choiceService.statisticsOneChoiceOrJudgmentAnswer(classify.get(TypeEnum.ONE_CHOICE.getCode()));
        }
        if (classify.containsKey(TypeEnum.MANY_CHOICE.getCode())) {
            choiceService.statisticsAnswer(classify.get(TypeEnum.MANY_CHOICE.getCode()));
        }
        if (classify.containsKey(TypeEnum.JUDGEMENT.getCode())) {
            choiceService.statisticsOneChoiceOrJudgmentAnswer(classify.get(TypeEnum.JUDGEMENT.getCode()));
        }
        // 暂且改完客观题,主观题老师改

        // 统计分数
        BigDecimal grades = studentPaperConfigScoreService.getGradesByPaperAndStu(stuAnswerDO.getAnswerPaper(), stuAnswerDO.getAnswerStudent());

        StudentPaperDO studentPaperDO = new StudentPaperDO();
        studentPaperDO.setPaperId(stuAnswerDO.getAnswerPaper());
        studentPaperDO.setPaperStudent(stuAnswerDO.getAnswerStudent());
        studentPaperDO.setPaperScore(grades);
        studentPaperDO.setPaperFlag(PaperEnum.LOADING.getCode());
        studentPaperMapper.updateById(studentPaperDO);

        log.debug("提交答卷，学生ID：{}，耗时：{}ms", stuAnswerDO.getAnswerStudent(), System.currentTimeMillis() - start);
    }

    @Override
    public void saveIssue(StudentAnswerDO answerDO) {
        StudentDO loginStudent = ShiroUtils.getLoginStudent();
        // 查询数据库中是否存在过数据
        QueryWrapper<StudentAnswerDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("answer_student", loginStudent.getStuId())
                .eq("answer_paper", answerDO.getAnswerPaper())
                .eq("answer_conf", answerDO.getAnswerConf())
                .eq("answer_question", answerDO.getAnswerQuestion());
        StudentAnswerDO one = studentAnswerMapper.selectOne(queryWrapper);
        // 不存在 进行保存
        if (one == null) {
            answerDO.setAnswerId(idWorker.nextId() + "");
            answerDO.setAnswerStudent(loginStudent.getStuId());
            studentAnswerMapper.insert(answerDO);
        } else {
            one.setAnswerContent(answerDO.getAnswerContent());
        }
    }


    /**
     * 功能描述  选择下一题
     *
     * @return topicDto
     * @author lth
     */
    private TopicDTO topicNext(String number, String answerConf) throws ExamException {
        // 查询学生试卷配置
        StudentPaperConfigDO stuPaperConfigDO = studentPaperConfigMapper.selectById(answerConf);
        String type = stuPaperConfigDO.getConfigType();
        // 根据题型 序号查找下一题
        // 为选择题 选择下一题
        TopicDTO topicDTO = new TopicDTO();
        Integer index = Integer.valueOf(number + "1");
        if (type.equals(TypeEnum.ONE_CHOICE.getCode().toString()) || type.equals(TypeEnum.MANY_CHOICE.getCode().toString())) {
            Page<ChoiceDO> page = new Page<>();
            page.setIndex(index);
            page.setCurrentCount(1);
            ChoiceDO choiceDO = choiceMapper.getListByPage(page).get(0);
            topicDTO.setChoiceDO(choiceDO);
        } else if (type.equals(TypeEnum.JUDGEMENT.getCode().toString())) {
            // 判断题
            Page<TrueFalseDO> page = new Page<>();
            page.setIndex(index);
            page.setCurrentCount(1);
            TrueFalseDO trueFalseDO = trueFalseMapper.getListByPage(page).get(0);
            topicDTO.setTrueFalseDO(trueFalseDO);
        } else if (type.equals(TypeEnum.COMPLETION.getCode().toString())) {
            // 填空题
            Page<CompletionDO> page = new Page<>();
            page.setIndex(index);
            page.setCurrentCount(1);
            CompletionDO completionDO = completionMapper.getListByPage(page).get(0);
            topicDTO.setCompletionDO(completionDO);
        } else if (type.equals(TypeEnum.PROGRAMMING.getCode().toString())) {
            // 编程题
            Page<CodeDO> page = new Page<>();
            page.setIndex(index);
            page.setCurrentCount(1);
            CodeDO codeDO = codeMapper.getListByPage(page).get(0);
            topicDTO.setCodeDO(codeDO);
        } else {
            // 其他题
            Page<QuestionDO> page = new Page<>();
            page.setIndex(index);
            page.setCurrentCount(1);
            QuestionDO questionDO = questionMapper.getListByPage(page).get(0);
            topicDTO.setQuestionDO(questionDO);
        }
        return topicDTO;
    }
}
