package com.exam.ex.ga;

import com.alibaba.fastjson.JSONObject;
import com.exam.core.constant.GaConstant;
import com.exam.ex.dto.GaConfigDTO;
import com.exam.core.exception.ExamException;
import com.exam.ex.pojo.PaperConfigDO;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

/**
 * 遗传算法测试类
 *
 * @Author: 杨德石
 * @Date: 2019/5/3 0003 下午 5:22
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GaTest {

    @Test
    public void testGenerateConfig() throws ExamException {
        List<String> knowIds = Lists.newArrayList();
        knowIds.add("1117389471029940224");
        knowIds.add("1117389727968808960");

        PaperConfigDO result = new PaperConfigDO();
        GaConfigDTO configDTO = new GaConfigDTO();
        configDTO.setBankId("1111913413874835456");
        configDTO.setTotalScore(new BigDecimal(20));
        configDTO.setTypeId("1");
        configDTO.setQuestionNum(10);
        configDTO.setDifficulty(2);
        configDTO.setKnowledgeIds(knowIds);

        int count = 0;
        int runCount = GaConstant.MAX_EVOLVE;
        double expand = GaConstant.DEFAULT_ADAPTATION_DEGREE;

        // 初始化种群
        Population population = new Population();
        population.initPopulation(GaConstant.POPULATION_SIZE, configDTO);
        System.out.println("初次适应度：" + population.getFitness(0).getAdaptationDegree() + "，知识点覆盖率为：" + population.getFitness(0).getKpCoverage());

        Generation generation = new Generation();
        while(count < runCount && population.getFitness(0).getAdaptationDegree() < expand) {
            count++;
            population = generation.evolvePopulation(population, configDTO);
            System.out.println("第 " + count + " 次进化，适应度为： " + population.getFitness(0).getAdaptationDegree() + "，知识点覆盖率为：" + population.getFitness(0).getKpCoverage());
            System.out.println("配置为：" + JSONObject.toJSONString(population.getFitness(0)));
        }
        System.out.println("进化次数： " + count);
        System.out.println(population.getFitness(0).getAdaptationDegree());
        result = population.getFitness(0);
        System.out.println("最终的配置为：" + JSONObject.toJSONString(result));
    }

}
