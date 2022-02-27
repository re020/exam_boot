package com.exam.core.task;

import com.exam.ex.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 学生相关定时任务
 * @Author: 杨德石
 * @Date: 2019/5/14 0014 下午 7:52
 * @Version 1.0
 */
@Component
public class StudentTask {

    @Autowired
    private StudentService studentService;

    /**
     * 每年1月1日凌晨1点30，将学生年龄 + 1
     */
    @Scheduled(cron = "0 30 1 1 1 ?")
    public void updateAge() {
        studentService.updateAllAge();
    }

}
