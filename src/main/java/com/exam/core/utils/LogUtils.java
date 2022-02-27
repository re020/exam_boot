package com.exam.core.utils;

import com.exam.ex.mapper.PaperLogMapper;
import com.exam.ex.pojo.PaperDO;
import com.exam.ex.pojo.PaperLogDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 组卷日志相关工具类
 * @Author: 杨德石
 * @Date: 2019/5/10 0010 下午 4:22
 * @Version 1.0
 */
@Component
public class LogUtils {

    private static IdWorker idWorker;
    private static PaperLogMapper paperLogMapper;

    /**
     * 保存提交组卷日志
     * @param paper
     */
    public static void saveLog(PaperDO paper) {
        PaperLogDO logDO = new PaperLogDO();
        logDO.setPlId(idWorker.nextId()+"");
        logDO.setPlTeacher(paper.getPaperCreateBy());
        logDO.setPlTitle(paper.getPaperTitle());
        logDO.setPlCollege(paper.getPaperCollege());
        logDO.setPlDifficulty(paper.getPaperDifficulty());
        logDO.setPlScore(paper.getPaperScore());
        logDO.setPlTime(paper.getPaperUpdateTime());
        paperLogMapper.insert(logDO);
    }

    @Autowired
    public void setIdWorker(IdWorker idWorker) {
        LogUtils.idWorker = idWorker;
    }

    @Autowired
    public void setPaperLogMapper(PaperLogMapper paperLogMapper) {
        LogUtils.paperLogMapper = paperLogMapper;
    }
}
