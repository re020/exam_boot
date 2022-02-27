package com.exam.ex.ga;

import com.exam.core.constant.GaConstant;
import com.exam.ex.dto.GaConfigDTO;
import com.exam.ex.pojo.PaperConfigDO;
import com.exam.core.utils.GaUtils;
import lombok.Data;

import java.util.List;

/**
 * 遗传算法
 *
 * @Author: 杨德石
 * @Date: 2019/5/3 0003 下午 4:16
 * @Version 1.0
 */
@Data
public class Generation {

    /**
     * 锦标赛选择法规模
     */
    private Integer tournamentSize = GaConstant.TOURNAMENT_SIZE;

    /**
     * 锦标赛选择法
     * @return
     */
    public PaperConfigDO select(Population pop) {
        Population population = new Population();
        population.initPopulation(tournamentSize);
        for (Integer i = 0; i < tournamentSize; i++) {
            population.setConfig(i, pop.getConfig((int) (Math.random() * pop.getConfigSize())));
        }
        return population.getFitness(0);
    }

    /**
     * 交叉算子
     * 采用两点交叉
     *
     * @param parent1
     * @param parent2
     * @return
     */
    public PaperConfigDO crossover(PaperConfigDO parent1, PaperConfigDO parent2, GaConfigDTO configDTO) {
        PaperConfigDO child = new PaperConfigDO(parent1.getQuestionDetailList().size());
        child.setConfigType(parent1.getConfigType());

        int s1 = (int) (Math.random() * parent1.getQuestionDetailList().size());
        int s2 = (int) (Math.random() * parent1.getQuestionDetailList().size());

        // parent1的startPos endPos之间的序列，会被遗传到下一代
        int startPos = s1 < s2 ? s1 : s2;
        int endPos = s1 > s2 ? s1 : s2;

        for (int i = startPos; i < endPos; i++) {
            child.saveQuestion(i, parent1.findQuestion(i));
        }

        // 继承parent2中未被child继承的question
        // 防止出现重复的元素
        for (int i = 0; i < startPos; i++) {
            saveChildQuestion(parent2, child, i, configDTO);
        }

        for (int i = endPos; i < parent2.getQuestionDetailList().size(); i++) {
            saveChildQuestion(parent2, child, i, configDTO);
        }

        child.calculationScoreGa();
        GaUtils.setConfigId(child);

        return child;
    }

    /**
     * 突变算子 每个个体的每个基因都有可能突变
     * 变异的原理就是从题库中抽取一道跟原题目分数一样的题目
     */
    public void mutate(PaperConfigDO config, GaConfigDTO configDTO) {
        List list;
        int index;
        for (int i = 0; i < config.getQuestionDetailList().size(); i++) {
            if (Math.random() < GaConstant.MUTATION_RATE) {
                // 进行突变，第i道
                Object tmpQuestion = config.findQuestion(i);
                // 从题库中获取和变异的题目类型一样分数相同的题目（不能重复）
                list = GaUtils.getMutateList(tmpQuestion, configDTO);
                if (list.size() > 0) {
                    // 随机获取一道
                    index = (int) (Math.random() * list.size());
                    config.saveQuestion(i, list.get(index));
                }
            }
        }
    }

    /**
     * 将亲代的题目遗传给子代
     *
     * @param parent2
     * @param child
     * @param i
     */
    private void saveChildQuestion(PaperConfigDO parent2, PaperConfigDO child, int i, GaConfigDTO configDTO) {
        if (!child.containsQuestion(parent2.findQuestion(i))) {
            child.saveQuestion(i, parent2.findQuestion(i));
        } else {
            List list = GaUtils.getMutateList(parent2.findQuestion(i), configDTO);
            child.saveQuestion(i, list.get((int) (Math.random() * list.size())));
        }
    }

    /**
     * 种群进化
     *
     * @return
     */
    public Population evolvePopulation(Population pop, GaConfigDTO configDTO) {
        Population population = new Population();
        population.initPopulation(pop.getConfigSize());
        int elitismOffset = GaConstant.ELITISM_OFFSET;
        // 精英主义
        for (int i = 0; i < elitismOffset; i++) {
            // 保留上一代最优秀个体
            PaperConfigDO fitness = pop.getFitness(i);
            population.setConfig(i, fitness);
        }

        // 种群交叉操作，从当前的种群pop 来 创建下一代种群 newPopulation
        for (int i = elitismOffset; i < pop.getConfigSize(); i++) {
            // 较优选择parent
            PaperConfigDO parent1 = select(pop);
            PaperConfigDO parent2 = select(pop);
            while (parent2.getConfigId().equals(parent1.getConfigId())) {
                parent2 = select(pop);
            }
            // 交叉
            PaperConfigDO child = crossover(parent1, parent2, configDTO);
            population.setConfig(i, child);
        }

        // 种群变异操作
        for (int i = elitismOffset; i < pop.getConfigSize(); i++) {
            PaperConfigDO configDO = population.getConfig(i);
            mutate(configDO, configDTO);
            // 计算知识点覆盖率与适应度
            configDO.setKpCoverageGa(configDTO);
            configDO.setAdaptationDegreeGa(configDTO);
            pop.setConfig(i, configDO);
        }
        return population;
    }

}
