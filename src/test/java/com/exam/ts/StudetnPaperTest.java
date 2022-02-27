package com.exam.ts;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exam.core.constant.DeleteEnum;
import com.exam.core.constant.QuestionEnum;
import com.exam.core.pojo.Page;
import com.exam.core.utils.DateUtils;
import com.exam.core.utils.IdWorker;
import com.exam.ex.mapper.PaperMapper;
import com.exam.ex.mapper.TypeMapper;
import com.exam.ex.pojo.TypeDO;
import com.exam.ts.mapper.ExamLogMapper;
import com.exam.ts.mapper.ExamMapper;
import com.exam.ts.mapper.StudentPaperConfigMapper;
import com.exam.ts.mapper.StudentPaperConfigQuestionMapper;
import com.exam.ts.mapper.StudentPaperConfigScoreMapper;
import com.exam.ts.mapper.StudentPaperMapper;
import com.exam.ts.pojo.DTO.StudentDTO;
import com.exam.ts.pojo.ExamDO;
import com.exam.ts.pojo.ExamLogDO;
import com.exam.ts.pojo.PO.LogPO;
import com.exam.ts.pojo.StudentAnswerDO;
import com.exam.ts.pojo.StudentPaperConfigDO;
import com.exam.ts.pojo.StudentPaperConfigQuestionDO;
import com.exam.ts.pojo.StudentPaperDO;
import com.exam.ts.service.ExamLogService;
import com.exam.ts.service.StudentPaperConfigService;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lth
 * @date 2019年10月22日 18:53
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class StudetnPaperTest {

    @Autowired
    TypeMapper typeMapper;
    @Autowired
    StudentPaperConfigQuestionMapper  studentPaperConfigQuestionMapper;
    @Autowired
    StudentPaperMapper studentPaperMapper;
    @Autowired
    PaperMapper paperMapper;
    @Autowired
    StudentPaperConfigMapper studentPaperConfigMapper;
    @Autowired
    StudentPaperConfigScoreMapper studentPaperConfigScoreMapper;
    @Autowired
    private StudentPaperConfigService studentPaperConfigService;
    @Autowired
    private ExamLogService examLogService;
    @Autowired
    private ExamLogMapper examLogMapper;
    @Autowired
    private ExamMapper examMapper;
    @Autowired
    private IdWorker idWorker;

    @Test
    public void test(){
        QueryWrapper<StudentPaperConfigDO> wrapper = new QueryWrapper<>();
        wrapper.select("config_id")
            .eq("config_paper","1163449743360241664")
            .eq("config_delete", DeleteEnum.NOT_DELETE.getCode());

        List<StudentPaperConfigDO> studentPaperConfigDOS = studentPaperConfigMapper.selectList(wrapper);
        for (StudentPaperConfigDO studentPaperConfigDO : studentPaperConfigDOS) {
            System.out.println(studentPaperConfigDO);
        }
    }

    /**
     * 测试StudentPaperConfigScoreMapper.getScoresByIds()方法
     */
    @Test
    public void testScore(){
        ArrayList<String> list = Lists.newArrayList();
        list.add("3");
        list.add("4");

        BigDecimal scoresByIds = studentPaperConfigScoreMapper.getScoresByIds("21", list);
        System.out.println(scoresByIds);
    }

    /**
     * 测试countOfPaper("1169443264873463808");
     */
    @Test
    public void testStudent(){
        LogPO logPO = studentPaperMapper.countOfPaper("1169443264873463808");
        System.out.println(logPO);
    }


    @Test
    public void getQuestionNum(){
        List<StudentPaperConfigDO> questionNum = studentPaperConfigService.getQuestionNum("1126353572622770176","1114880900740530176");
        System.out.println(questionNum);
    }

    @Test
    public void insert(){
        for(int i = 0; i < 20; i++){
            ExamLogDO examLogDO = new ExamLogDO();
            examLogDO.setLogId(idWorker.nextId() + "");
            examLogDO.setLogDate(DateUtils.newDate());
            examLogDO.setLogTime(60);
            examLogDO.setLogRoom("1123" + i);
            examLogDO.setLogStudentNum(2 + i);
            examLogDO.setLogPaperTitle("asdd" + i);
            examLogDO.setLogPass(new BigDecimal("0"));
            examLogDO.setLogCreateBy("123");
            examLogDO.setLogVersion(1);
            examLogDO.setLogDelete(1);
            examLogMapper.insert(examLogDO);
        }
    }

    @Test
    public void list(){
        Page<ExamLogDO> page = new Page<>();
        page.setCurrentCount(10);
        List<ExamLogDO> listByPage = examLogMapper.getListByPage(page);
        System.out.println(JSONObject.toJSON(listByPage));

    }



}