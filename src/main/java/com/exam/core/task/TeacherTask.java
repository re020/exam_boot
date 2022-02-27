package com.exam.core.task;

import com.exam.ex.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 教师相关定时任务
 * @Author: 杨德石
 * @Date: 2019/5/14 0014 下午 7:52
 * @Version 1.0
 */
@Component
public class TeacherTask {

    @Autowired
    private TeacherService teacherService;

    /**
     * 每年1月1日凌晨1点，将教师年龄 + 1
     */
    @Scheduled(cron = "0 0 1 1 1 ?")
    public void updateAge() {
        teacherService.updateAllAge();
    }

}
