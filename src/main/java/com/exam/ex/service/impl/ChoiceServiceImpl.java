package com.exam.ex.service.impl;

import com.alibaba.druid.sql.visitor.functions.Right;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.core.constant.CoreConstant;
import com.exam.core.constant.QuestionEnum;
import com.exam.core.constant.ResultEnum;
import com.exam.core.utils.MathUtils;
import com.exam.ex.dto.GaConfigDTO;
import com.exam.ex.mapper.ChoiceMapper;
import com.exam.ex.pojo.ChoiceAnswerDO;
import com.exam.ex.pojo.ChoiceDO;
import com.exam.core.pojo.Page;
import com.exam.ex.service.ChoiceAnswerService;
import com.exam.ex.service.ChoiceService;
import com.exam.core.utils.IdWorker;
import com.exam.core.utils.Result;
import com.exam.core.utils.StringUtils;
import com.exam.ts.pojo.StudentAnswerDO;
import com.exam.ts.pojo.StudentPaperConfigObjScoreDO;
import com.exam.ts.pojo.StudentPaperConfigQuestionDO;
import com.exam.ts.pojo.StudentPaperConfigScoreDO;
import com.exam.ts.service.StudentPaperConfigObjScoreService;
import com.exam.ts.service.StudentPaperConfigQuestionService;
import com.exam.ts.service.StudentPaperConfigScoreService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 选择题表 服务实现类
 * </p>
 *
 * @author 杨德石
 * @since 2019-03-28
 */
@Slf4j
@Service
public class ChoiceServiceImpl extends ServiceImpl<ChoiceMapper, ChoiceDO> implements ChoiceService {

    @Autowired
    private ChoiceMapper choiceMapper;
    @Autowired
    private ChoiceAnswerService choiceAnswerService;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private StudentPaperConfigQuestionService studentPaperConfigQuestionService;
    @Autowired
    private StudentPaperConfigObjScoreService studentPaperConfigObjScoreService;

    @Autowired
    private StudentPaperConfigScoreService studentPaperConfigScoreService;

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    @Override
    public Page<ChoiceDO> getByPage(Page<ChoiceDO> page) {
        // 处理参数
        page.filterParams();
        // 设置每页显示条数
        if (page.getCurrentCount() == null) {
            page.setCurrentCount(CoreConstant.CURRENT_COUNT);
        }
        // 计算索引
        Integer index = (page.getCurrentPage() - 1) * page.getCurrentCount();
        page.setIndex(index);
        // 查询每页数据
        List<ChoiceDO> list = choiceMapper.getListByPage(page);
        // 过滤正确答案选项
        list.forEach(e -> {
            List<String> numberList = e.getChoiceAnswer().stream().filter(ChoiceAnswerDO::getAnswerTrue)
                    .map(ChoiceAnswerDO::getAnswerNumber).collect(Collectors.toList());
            e.setChoiceTrue(StringUtils.join(numberList, ", "));
        });
        page.setList(list);
        Integer totalCount = choiceMapper.getCountByPage(page);
        page.setTotalCount(totalCount);
        // 计算总页数
        page.setTotalPage((int) Math.ceil((page.getTotalCount() * 1.0) / page.getCurrentCount()));
        return page;
    }

    /**
     * 添加或修改单选题
     *
     * @param choice
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result addOrUpdateOneChoice(ChoiceDO choice) {
        // 添加选择题，然后获取选择题的选项进行添加
        List<ChoiceAnswerDO> answerList = choice.getChoiceAnswer();
        // 判断是否添加了选项
        if (answerList.isEmpty()) {
            return Result.build(ResultEnum.ERROR.getCode(), "请添加选项！");
        }
        // 判断是否有多个正确答案
        List<Boolean> trueList = answerList.stream().map(ChoiceAnswerDO::getAnswerTrue).collect(Collectors.toList());
        int count = Collections.frequency(trueList, true);
        if (count != 1) {
            // 不止一个正确答案或者没有正确答案
            return Result.build(ResultEnum.ERROR.getCode(), "单项选择题必须只有一个正确答案！");
        }

        // 判断是修改还是添加
        return updateChoice(choice, answerList);
    }

    /**
     * 添加或修改多选题
     *
     * @param choice
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result addOrUpdateManyChoice(ChoiceDO choice) {
        // 添加选择题，然后获取选择题的选项进行添加
        List<ChoiceAnswerDO> answerList = choice.getChoiceAnswer();
        // 判断是否添加了选项
        if (answerList.isEmpty()) {
            return Result.build(ResultEnum.ERROR.getCode(), "请添加选项！");
        }
        // 判断是否有多个正确答案
        List<Boolean> trueList = answerList.stream().map(ChoiceAnswerDO::getAnswerTrue).collect(Collectors.toList());
        int count = Collections.frequency(trueList, true);
        if (count < 1) {
            // 没有正确答案
            return Result.build(ResultEnum.ERROR.getCode(), "多项选择题必须有正确答案！");
        }

        return updateChoice(choice, answerList);
    }

    /**
     * 随机查询列表（遗传算法专用）
     *
     * @param configDTO
     * @return
     */
    @Override
    public List<ChoiceDO> getGaList(GaConfigDTO configDTO) {
        return choiceMapper.getGaList(configDTO);
    }

    /**
     * 遗传算法专用变异查询
     *
     * @return
     */
    @Override
    public List<ChoiceDO> getMutateList(ChoiceDO choiceDO, GaConfigDTO configDTO) {
        return choiceMapper.getMutateList(choiceDO, configDTO);
    }

    /**
     * 统计多项选择题成绩
     *
     * @return
     */
    @Override
    public void statisticsAnswer(List<StudentAnswerDO> studentAnswerDOS) {
        String paper = studentAnswerDOS.get(0).getAnswerPaper();
        String config = studentAnswerDOS.get(0).getAnswerConf();
        String stuId = studentAnswerDOS.get(0).getAnswerStudent();
        Object[] objects = studentAnswerDOS.stream().map(StudentAnswerDO::getAnswerQuestion).distinct().toArray();
        log.info("问题Id的集合:{}", objects);
        // 根据题目的id 找到正确答案的numbers,编号为字符串 然后比对答案
        List<ChoiceDO> list =
                choiceMapper.getMutListByIds(objects);
        log.info("当前的题目集合:{}", list);
        // 可以利用流去统计分数
        List<BigDecimal> grade = Lists.newArrayList();
        studentAnswerDOS.forEach(tmpTopic -> {
            // 根据答案判断对错
            log.info("开始答案判断对错：{}", tmpTopic);
            list.forEach(tmpAnswer -> {
                if (tmpAnswer.getChoiceId().equals(tmpTopic.getAnswerQuestion())) {
                    BigDecimal g = getGrade(tmpAnswer, tmpTopic);
                    BigDecimal multiply = tmpAnswer.getChoiceScore().multiply(g);
                    log.info("该id:{}  该题得分:{}", tmpAnswer.getChoiceId(), multiply);
                    grade.add(multiply);
                    // 保存到学生-试卷-每个题型-客观题得分表
                    StudentPaperConfigObjScoreDO studentPaperConfigObjScoreDO = new StudentPaperConfigObjScoreDO();
                    studentPaperConfigObjScoreDO.setQsId(idWorker.nextId() + "");
                    studentPaperConfigObjScoreDO.setQsQuestion(tmpAnswer.getChoiceId());
                    studentPaperConfigObjScoreDO.setQsConfig(tmpTopic.getAnswerConf());
                    studentPaperConfigObjScoreDO.setQsScore(multiply);
                    studentPaperConfigObjScoreDO.setQsStudent(tmpTopic.getAnswerStudent());
                    studentPaperConfigObjScoreService.save(studentPaperConfigObjScoreDO);

                    // 更改学生试卷配置-题目表状态
                    QueryWrapper<StudentPaperConfigQuestionDO> configQuestion = new QueryWrapper<>();
                    configQuestion.eq("question_config", tmpTopic.getAnswerConf());
                    configQuestion.eq("question_id", tmpAnswer.getChoiceId());
                    StudentPaperConfigQuestionDO configQuestionDO = studentPaperConfigQuestionService.getOne(configQuestion);
                    configQuestionDO.setQuestionState(QuestionEnum.CORRECTED.getCode());
                    studentPaperConfigQuestionService.updateById(configQuestionDO);
                }
            });
        });
        // 记录该题型总的分数
        StudentPaperConfigScoreDO scoreDO = new StudentPaperConfigScoreDO();
        scoreDO.setScId(idWorker.nextId() + "");
        scoreDO.setScPaper(paper);
        scoreDO.setScConfig(config);
        scoreDO.setScScore(grade.stream().reduce(BigDecimal.ZERO, BigDecimal::add));
        scoreDO.setScStudent(stuId);

        log.info("记录该config总的分数:{}", scoreDO);
        studentPaperConfigScoreService.save(scoreDO);

        log.info("记录完成");
    }

    /**
     * 统计多选题成绩
     *
     * @return
     */
    private BigDecimal getGrade(ChoiceDO tmpAnswer, StudentAnswerDO tmpTopic) {
        String trueAnswer = tmpAnswer.getChoiceTrue();
        String studentAnswer = tmpTopic.getAnswerContent();
        String stringTrue = org.apache.commons.lang3.StringUtils.split(trueAnswer, '#').toString();
        String stringStudent = org.apache.commons.lang3.StringUtils.split(studentAnswer, '#').toString();

        return MathUtils.getGrade(stringStudent, stringTrue);
    }

    /**
     * 统计选择题成绩
     *
     * @return
     */
    @Override
    public void statisticsOneChoiceOrJudgmentAnswer(List<StudentAnswerDO> studentAnswerDOS) {
        log.info("记录单选题分数");
        String paper = studentAnswerDOS.get(0).getAnswerPaper();
        String config = studentAnswerDOS.get(0).getAnswerConf();
        String stuId = studentAnswerDOS.get(0).getAnswerStudent();
        Object[] objects = studentAnswerDOS.stream().map(StudentAnswerDO::getAnswerQuestion).distinct().toArray();
        log.info("问题Id的集合:{}", objects);
        // 根据题目的id 找到正确答案的字母 然后比对答案
        List<ChoiceDO> list =
                choiceMapper.getListByIds(objects);
        log.info("当前的题目集合:{}", list);
        // 可以利用流去统计分数
        List<BigDecimal> grade = Lists.newArrayList();
        studentAnswerDOS.forEach(tmpTopic -> {
            log.info("tmpTopic:{}", tmpTopic);
            // 根据答案判断正误
            list.forEach(tmpAnswer -> {
                if (tmpAnswer.getChoiceId().equals(tmpTopic.getAnswerQuestion())) {
                    log.info("该id:{}  该题得分:{}", tmpAnswer.getChoiceId());
                    BigDecimal score = new BigDecimal(0);
                    // 答案正确
                    if (tmpAnswer.getChoiceTrue().equalsIgnoreCase(tmpTopic.getAnswerContent())) {
                        score = tmpAnswer.getChoiceScore();
                    }
                    // 添加入grade
                    grade.add(score);
                    // 保存到学生-试卷-每个题型-客观题得分表
                    StudentPaperConfigObjScoreDO studentPaperConfigObjScoreDO = new StudentPaperConfigObjScoreDO();
                    studentPaperConfigObjScoreDO.setQsId(idWorker.nextId() + "");
                    studentPaperConfigObjScoreDO.setQsQuestion(tmpAnswer.getChoiceId());
                    studentPaperConfigObjScoreDO.setQsConfig(tmpTopic.getAnswerConf());
                    studentPaperConfigObjScoreDO.setQsScore(score);
                    studentPaperConfigObjScoreDO.setQsStudent(tmpTopic.getAnswerStudent());
                    studentPaperConfigObjScoreService.save(studentPaperConfigObjScoreDO);

                    // 更改学生试卷配置-题目表状态
                    QueryWrapper<StudentPaperConfigQuestionDO> configQuestion = new QueryWrapper<>();
                    configQuestion.eq("question_config", tmpTopic.getAnswerConf());
                    configQuestion.eq("question_id", tmpAnswer.getChoiceId());
                    StudentPaperConfigQuestionDO configQuestionDO = studentPaperConfigQuestionService.getOne(configQuestion);
                    configQuestionDO.setQuestionState(QuestionEnum.CORRECTED.getCode());
                    studentPaperConfigQuestionService.updateById(configQuestionDO);
                }
            });
        });
        // 记录该题型总的分数
        StudentPaperConfigScoreDO scoreDO = new StudentPaperConfigScoreDO();
        scoreDO.setScId(idWorker.nextId() + "");
        scoreDO.setScPaper(paper);
        scoreDO.setScConfig(config);
        scoreDO.setScScore(grade.stream().reduce(BigDecimal.ZERO, BigDecimal::add));
        scoreDO.setScStudent(stuId);

        log.info("记录该config总的分数:{}", scoreDO);
        studentPaperConfigScoreService.save(scoreDO);

        log.info("记录完成");
    }

    @Transactional(rollbackFor = Exception.class)
    protected Result updateChoice(ChoiceDO choice, List<ChoiceAnswerDO> answerList) {
        if (StringUtils.isBlank(choice.getChoiceId())) {
            // 补全属性
            choice.setChoiceId(idWorker.nextId() + "");
            // 获取id，给选项每一项的题目id赋值
            String choiceId = choice.getChoiceId();
            answerList.forEach(e -> {
                e.setAnswerChoice(choiceId);
                e.setAnswerId(idWorker.nextId() + "");
            });
            choiceMapper.insert(choice);
            choiceAnswerService.saveBatch(answerList);
            return Result.ok("添加成功！");
        } else {

            // 删除旧选项
            choiceAnswerService.deleteOldAnswer(choice);

            // 更新
            choiceMapper.updateById(choice);
            choiceAnswerService.updateBatchById(answerList);

            // 查询所有的答案
            String choiceId = choice.getChoiceId();
            QueryWrapper<ChoiceAnswerDO> wrapper = new QueryWrapper<ChoiceAnswerDO>()
                    .eq("answer_choice", choiceId);

            List<ChoiceAnswerDO> answerDOList = choiceAnswerService.list(wrapper);

            // 删除所有更新成功的答案，剩下的就是新增的，批量添加一下
            answerList.removeAll(answerDOList);

            answerList.forEach(e -> {
                e.setAnswerId(idWorker.nextId() + "");
                e.setAnswerChoice(choiceId);
            });

            choiceAnswerService.saveBatch(answerList);

            return Result.ok("修改成功！");
        }
    }

}
