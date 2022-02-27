package com.exam;

import com.exam.core.utils.MathUtils;
import com.exam.ex.mapper.ChoiceMapper;
import com.exam.ex.pojo.ChoiceAnswerDO;
import com.exam.ex.pojo.ChoiceDO;
import com.exam.ts.mapper.StudentAnswerMapper;
import com.exam.ts.mapper.StudentPaperMapper;
import com.exam.ts.pojo.StudentAnswerDO;
import com.exam.ts.pojo.StudentPaperDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest

public class ExamBootApplicationTests {

    @Autowired
    private StudentAnswerMapper studentAnswerMapper;
    @Autowired
    private StudentPaperMapper studentPaperMapper;


    @Autowired
    private ChoiceMapper choiceMapper;
//
//    @Test
//    public void test(){
//        StudentAnswerDO studentAnswerDO = new StudentAnswerDO();
//        studentAnswerDO.setAnswerStudent("1114880900740530176");
//        studentAnswerDO.setAnswerPaper("1240840105852170240");
//        List<StudentAnswerDO> studentAnswerDOS = studentAnswerMapper.selectcofigList(studentAnswerDO);
//        studentAnswerDOS.forEach(s-> System.out.println(s));
//
//    }

    @Test
    public void test2() {
//        Object[] ids =
//            choiceMapper.getMutListByIds()
        String tre = "ABC";
        String stu = "AB";
        BigDecimal grade =
                MathUtils.getGrade(stu, tre);
        System.out.println(grade);
    }
}