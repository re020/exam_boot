package com.exam.ex.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 智能组卷的DTO对象
 * @Author: 杨德石
 * @Date: 2019/5/2 0002 下午 8:45
 * @Version 1.0
 */
@Data
public class GaConfigDTO implements Serializable {

    /**
     * 知识点id列表
     */
    private List<String> knowledgeIds;

    /**
     * 该配置的总分
     */
    private BigDecimal totalScore;

    /**
     * 该配置的题型
     */
    private String typeId;

    /**
     * 该配置的题目数
     */
    private Integer questionNum;

    /**
     * 该配置的难度
     */
    private Integer difficulty;

    /**
     * 题库
     */
    private String bankId;

    @Override
    public String toString() {
        return "GaConfigDTO{" +
                "knowledgeIds=" + knowledgeIds +
                ", totalScore=" + totalScore +
                ", typeId='" + typeId + '\'' +
                ", questionNum=" + questionNum +
                ", difficulty=" + difficulty +
                '}';
    }
}
