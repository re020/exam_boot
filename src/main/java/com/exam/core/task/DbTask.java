package com.exam.core.task;

import com.exam.core.utils.DbUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 和数据库相关的定时任务
 * @Author: 杨德石
 * @Date: 2019/5/7 0007 下午 5:09
 * @Version 1.0
 */
@Component
public class DbTask {

    /**
     * 每天晚上10点和中午12点备份数据库到项目目录下
     */
    @Scheduled(cron = "0 0 12,22 * * ?")
    public void backupDb() {
        DbUtils.backup();
    }

}
