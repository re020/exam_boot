package com.exam.ex.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.exam.core.constant.GaConstant;
import com.exam.core.constant.NumberConstant;
import com.exam.core.constant.TypeEnum;
import com.exam.ex.dto.GaConfigDTO;
import com.exam.core.utils.StringUtils;
import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 试卷配置表，遗传算法个体
 * </p>
 *
 * @author 杨德石
 * @since 2019-04-12
 */
@TableName("ex_paper_config")
@Data
public class PaperConfigDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "config_id", type = IdType.INPUT)
    private String configId;

    /**
     * 所属试卷
     */
    private String configPaper;

    /**
     * 题目量
     */
    private Integer configQuestionNum;

    /**
     * 分值
     */
    private BigDecimal configScore = new BigDecimal(0);

    /**
     * 所属题型
     */
    private String configType;

    /**
     * 题型
     */
    @TableField(exist = false)
    private TypeDO type;

    @TableField(exist = false)
    private String typeName;

    /**
     * 知识点
     */
    private String configKnow;

    /**
     * 乐观锁
     */
    @Version
    private Integer configVersion;

    /**
     * 0删除1正常
     */
    @TableLogic
    private Integer configDelete;

    /**
     * 对应的题目
     */
    @TableField(exist = false)
    private List<PaperConfigQuestionDO> questionList;

    /**
     * 题目详细列表
     */
    @TableField(exist = false)
    private List questionDetailList = Lists.newArrayList();

    public PaperConfigDO() {
    }

    public PaperConfigDO(int questionSize) {
        this.questionDetailList = Lists.newArrayList();
        for (int i = 0; i < questionSize; i++) {
            this.questionDetailList.add(new Object());
        }
    }

    @Override
    public String toString() {
        return "PaperConfigDO{" +
                "configId='" + configId + '\'' +
                ", configPaper='" + configPaper + '\'' +
                ", configQuestionNum=" + configQuestionNum +
                ", configScore=" + configScore +
                ", configType='" + configType + '\'' +
                ", type=" + type +
                ", configKnow='" + configKnow + '\'' +
                ", configVersion=" + configVersion +
                ", configDelete=" + configDelete +
                ", questionList=" + questionList +
                ", questionDetailList=" + questionDetailList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PaperConfigDO that = (PaperConfigDO) o;
        return Objects.equals(configId, that.configId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(configId);
    }

    /* 下面是遗传算法专用的方法 */

    /**
     * 配置的难度系数
     */
    @TableField(exist = false)
    private Double configDifficulty;

    /**
     * 知识点覆盖率
     */
    @TableField(exist = false)
    private Double kpCoverage;

    /**
     * 个体适应度
     */
    @TableField(exist = false)
    private Double adaptationDegree;

    /**
     * 计算总分
     */
    public double calculationScoreGa() {
        if(this.configScore == null) {
            this.configScore = new BigDecimal(NumberConstant.ZERO);
        }
        BigDecimal score = new BigDecimal(NumberConstant.ZERO);

        if (StringUtils.isNotBlank(this.configType) && !questionDetailList.isEmpty()) {
            if (this.configType.equals(TypeEnum.ONE_CHOICE.getCode().toString()) ||
                    this.configType.equals(TypeEnum.MANY_CHOICE.getCode().toString())) {
                List<ChoiceDO> choiceDOList = questionDetailList;
                score = choiceDOList.stream().map(ChoiceDO::getChoiceScore).reduce(BigDecimal::add).get();
            } else if (this.configType.equals(TypeEnum.JUDGEMENT.getCode().toString())) {
                // 判断题
                List<TrueFalseDO> trueFalseDOList = questionDetailList;
                score = trueFalseDOList.stream().map(TrueFalseDO::getTfScore).reduce(BigDecimal::add).get();
            } else if (this.configType.equals(TypeEnum.COMPLETION.getCode().toString())) {
                // 填空题
                List<CompletionDO> completionDOList = questionDetailList;
                score = completionDOList.stream().map(CompletionDO::getCompScore).reduce(BigDecimal::add).get();
            } else if (this.configType.equals(TypeEnum.PROGRAMMING.getCode().toString())) {
                // 编程题
                List<CodeDO> codeDOList = questionDetailList;
                score = codeDOList.stream().map(CodeDO::getCodeScore).reduce(BigDecimal::add).get();
            } else {
                // 其他题
                List<QuestionDO> questionDOList = questionDetailList;
                score = questionDOList.stream().map(QuestionDO::getQuestionScore).reduce(BigDecimal::add).get();
            }
        }

        this.configScore = score;
        return this.configScore.doubleValue();
    }

    /**
     * 初始化个体
     */
    public void initPaperConfig(GaConfigDTO configDTO) {
        this.configQuestionNum = configDTO.getQuestionNum();
        this.configType = configDTO.getTypeId();
    }

    /**
     * 计算难度系数
     * 由于计算难度系数最后需要除以总分
     * 因此该方法应该在计算了总分之后再进行使用
     */
    public double calculationDifficultyGa() {
        double totalDifficulty = 0;

        if (this.configType.equals(TypeEnum.ONE_CHOICE.getCode().toString()) ||
                this.configType.equals(TypeEnum.MANY_CHOICE.getCode().toString())) {
            List<ChoiceDO> choiceDOList = questionDetailList;
            totalDifficulty += choiceDOList.stream().map(e -> e.getChoiceScore().multiply(new BigDecimal(e.getChoiceDifficulty())))
                    .reduce(BigDecimal::add).get().doubleValue();
        } else if (this.configType.equals(TypeEnum.JUDGEMENT.getCode().toString())) {
            // 判断题
            List<TrueFalseDO> trueFalseDOList = questionDetailList;
            totalDifficulty += trueFalseDOList.stream().map(e -> e.getTfScore().multiply(new BigDecimal(e.getTfDifficulty())))
                    .reduce(BigDecimal::add).get().doubleValue();
        } else if (this.configType.equals(TypeEnum.COMPLETION.getCode().toString())) {
            // 填空题
            List<CompletionDO> completionDOList = questionDetailList;
            totalDifficulty += completionDOList.stream().map(e -> e.getCompScore().multiply(new BigDecimal(e.getCompDifficulty())))
                    .reduce(BigDecimal::add).get().doubleValue();
        } else if (this.configType.equals(TypeEnum.PROGRAMMING.getCode().toString())) {
            // 编程题
            List<CodeDO> codeDOList = questionDetailList;
            totalDifficulty += codeDOList.stream().map(e -> e.getCodeScore().multiply(new BigDecimal(e.getCodeDifficulty())))
                    .reduce(BigDecimal::add).get().doubleValue();
        } else {
            // 其他题
            List<QuestionDO> questionDOList = questionDetailList;
            totalDifficulty += questionDOList.stream().map(e -> e.getQuestionScore().multiply(new BigDecimal(e.getQuestionDifficulty())))
                    .reduce(BigDecimal::add).get().doubleValue();
        }

        // 求出了总难度系数，需要除以总分
        BigDecimal score = this.getConfigScore();
        double configDiff = new BigDecimal(totalDifficulty).divide(score, NumberConstant.DEFAULT_DECIMAL_RETAIN, BigDecimal.ROUND_HALF_DOWN).doubleValue();
        this.setConfigDifficulty(configDiff);
        return configDiff;
    }

    /**
     * 计算知识点覆盖率
     * 个体和期望的知识点取交集 / 期望包含的知识点
     */
    public void setKpCoverageGa(GaConfigDTO configDTO) {
        if (this.kpCoverage == null || this.kpCoverage == 0) {
            List<String> result = Lists.newArrayList();
            result.addAll(configDTO.getKnowledgeIds());
            List<String> questionKnowIds;

            if (this.configType.equals(TypeEnum.ONE_CHOICE.getCode().toString()) ||
                    this.configType.equals(TypeEnum.MANY_CHOICE.getCode().toString())) {
                List<ChoiceDO> choiceDOList = questionDetailList;
                questionKnowIds = choiceDOList.stream().map(ChoiceDO::getChoiceKnow).distinct().collect(Collectors.toList());
            } else if (this.configType.equals(TypeEnum.JUDGEMENT.getCode().toString())) {
                // 判断题
                List<TrueFalseDO> trueFalseDOList = questionDetailList;
                questionKnowIds = trueFalseDOList.stream().map(TrueFalseDO::getTfKnow).distinct().collect(Collectors.toList());
            } else if (this.configType.equals(TypeEnum.COMPLETION.getCode().toString())) {
                // 填空题
                List<CompletionDO> completionDOList = questionDetailList;
                questionKnowIds = completionDOList.stream().map(CompletionDO::getCompKnow).distinct().collect(Collectors.toList());
            } else if (this.configType.equals(TypeEnum.PROGRAMMING.getCode().toString())) {
                // 编程题
                List<CodeDO> codeDOList = questionDetailList;
                questionKnowIds = codeDOList.stream().map(CodeDO::getCodeKnow).distinct().collect(Collectors.toList());
            } else {
                // 其他题
                List<QuestionDO> questionDOList = questionDetailList;
                questionKnowIds = questionDOList.stream().map(QuestionDO::getQuestionKnow).distinct().collect(Collectors.toList());
            }

            // 取交集
            result.retainAll(questionKnowIds);

            this.kpCoverage = result.size() * 1.0 / questionKnowIds.size();

        }
    }

    /**
     * 计算个体适应度 公式为：f=1-(1-M/N)*f1-|EP-P|*f2
     * 其中M/N为知识点覆盖率，EP为期望难度系数，P为种群个体难度系数，f1为知识点分布的权重
     * ，f2为难度系数所占权重。当f1=0时退化为只限制试题难度系数，当f2=0时退化为只限制知识点分布
     */
    public void setAdaptationDegreeGa(GaConfigDTO configDTO) {
        if (this.adaptationDegree == null || this.adaptationDegree == 0) {
            adaptationDegree = 1 - (1 - this.getKpCoverage()) * GaConstant.KNOWLEDGE_WEIGHT
                    - Math.abs(configDTO.getDifficulty() - calculationDifficultyGa()) * GaConstant.DIFFICULTY_WEIGHT;
        }
    }

    /**
     * 判断试题是否在配置中
     */
    public boolean containsQuestion(Object questionObject) {
        if (questionObject == null) {
            for (int i = 0; i < questionDetailList.size(); i++) {
                if (questionDetailList.get(i) == null) {
                    return true;
                }
            }
        } else {
            if (questionDetailList.contains(questionObject)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据下标获取题目
     *
     * @param i
     * @return
     */
    public Object findQuestion(int i) {
        return this.questionDetailList.get(i);
    }

    /**
     * 根据下标将题目保存
     *
     * @param i
     * @param question
     */
    public void saveQuestion(int i, Object question) {
        if (this.questionDetailList == null) {
            this.questionDetailList = Lists.newArrayList();
        }
        this.questionDetailList.set(i, question);
    }
}
