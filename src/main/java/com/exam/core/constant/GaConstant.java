package com.exam.core.constant;

/**
 * 遗传算法中需要用到的常量
 * @Author: 杨德石
 * @Date: 2019/5/2 0002 下午 9:08
 * @Version 1.0
 */
public class GaConstant {

    private GaConstant() {}

    /**
     * 知识点分布权重
     */
    public static final double KNOWLEDGE_WEIGHT = 0.2;

    /**
     * 难度系数分布权重
     */
    public static final double DIFFICULTY_WEIGHT = 0.8;

    /**
     * 变异率
     */
    public static final double MUTATION_RATE = 0.085;

    /**
     * 精英数
     */
    public static final Integer ELITISM_OFFSET = 1;

    /**
     * 种群默认大小
     */
    public static final Integer POPULATION_SIZE = 20;

    /**
     * 锦标赛选择法默认规模
     */
    public static final Integer TOURNAMENT_SIZE = 5;

    /**
     * 最大进化次数
     */
    public static final Integer MAX_EVOLVE = 100;

    /**
     * 适应度大小
     * 误差范围允许在0.02
     */
    public static final double DEFAULT_ADAPTATION_DEGREE = 0.98;
}
