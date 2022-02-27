package com.exam.core.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exam.core.constant.ExamEnum;
import com.exam.ts.mapper.ExamMapper;
import com.exam.ts.pojo.ExamDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author lth
 */

@Component
@Slf4j
public class PaperTask {

    @Autowired
    private ExamMapper examMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 每分钟检查一次
     */
    @Scheduled(cron = "0 * * * * ?")
    public void scan() throws ParseException {
        // 当前时间
        Date date = new Date();
        // 查询库里是否有即将开始的考试
        QueryWrapper<ExamDO> queryWrapper = new QueryWrapper<>();
        List<ExamDO> examDOS = examMapper.selectList(queryWrapper);
        if (examDOS.size() == 0) {
            // 没有则返回
            log.info("exam list scan : size = 0");
            return;
        }
        log.info("exam list scan :{}", examDOS.size());
        for (ExamDO examDO : examDOS) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startDate = sdf.parse(examDO.getExamDate());
            Date endDate = DateUtils.addMinutes(startDate, examDO.getExamTime());
            // 考试时间结束了
            if (date.after(endDate)) {
                examDO.setExamState(ExamEnum.ENDED.getCode());
                examMapper.updateById(examDO);
                log.info("更新了考试id为:{}的考试状态",examDO.getExamId());
            }
        }

    }

}
