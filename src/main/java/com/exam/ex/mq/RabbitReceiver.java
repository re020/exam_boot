package com.exam.ex.mq;

import com.exam.core.constant.MqConstant;
import com.exam.core.constant.PaperEnum;
import com.exam.core.constant.SubmitEnum;
import com.exam.core.exception.ExamException;
import com.exam.ts.pojo.DTO.CommitDTO;
import com.exam.ts.pojo.StudentAnswerDO;
import com.exam.ts.pojo.StudentPaperDO;
import com.exam.ts.service.StudentAnswerService;
import com.exam.ts.service.StudentPaperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * rabbit消费者
 *
 * @author lth
 * @version 1.0.0
 * @date
 */
@Slf4j
@Component
@RabbitListener(queues = MqConstant.SUBMIT_EXAM_QUEUE)
public class RabbitReceiver {

    @Autowired
    private StudentAnswerService studentAnswerService;


    @RabbitHandler
    public void handlerExam(CommitDTO commitDTO) {
        // 处理考试逻辑
        log.info("处理学生答题 学生ID:{}", commitDTO.getStuId());
        StudentAnswerDO studentAnswerDO = new StudentAnswerDO();
        studentAnswerDO.setAnswerPaper(commitDTO.getPaperId());
        studentAnswerDO.setAnswerStudent(commitDTO.getStuId());
        studentAnswerService.asyncHandler(studentAnswerDO);

    }

}
