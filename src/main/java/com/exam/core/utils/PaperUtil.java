package com.exam.core.utils;

import com.exam.core.constant.CharConstant;
import com.exam.core.constant.TypeEnum;
import com.exam.ex.pojo.ChoiceAnswerDO;
import com.exam.ex.pojo.ChoiceDO;
import com.exam.ex.pojo.CodeAnswerDO;
import com.exam.ex.pojo.CodeDO;
import com.exam.ex.pojo.CompletionAnswerDO;
import com.exam.ex.pojo.CompletionDO;
import com.exam.ex.pojo.PaperConfigDO;
import com.exam.ex.pojo.PaperDO;
import com.exam.ex.pojo.QuestionAnswerDO;
import com.exam.ex.pojo.QuestionDO;
import com.exam.ex.pojo.TrueFalseDO;

import java.util.List;

/**
 * 用来操作试卷的工具类
 * @Author: 杨德石
 * @Date: 2019/4/30 0030 下午 8:33
 * @Version 1.0
 */
public class PaperUtil {
    private PaperUtil() {}

    /**
     * 获取试卷中题目的题干、答案、解析
     * 将题目中的符号替换为指定的符号
     */
    public static PaperDO replaceQuestion(PaperDO paper, String sourceString, String targetString) {
        List<PaperConfigDO> configList = paper.getConfigList();
        for (PaperConfigDO paperConfigDO : configList) {
            List questionList = paperConfigDO.getQuestionDetailList();
            for (Object object : questionList) {
                if(TypeEnum.ONE_CHOICE.getCode().toString().equals(paperConfigDO.getConfigType()) ||
                    TypeEnum.MANY_CHOICE.getCode().toString().equals(paperConfigDO.getConfigType())) {
                    // 是选择题
                    ChoiceDO choice = (ChoiceDO) object;
                    choice.setChoiceTitle(choice.getChoiceTitle().replaceAll(sourceString, targetString));
                    String choiceResolve = choice.getChoiceResolve();
                    if(StringUtils.isNotBlank(choiceResolve)) {
                        choice.setChoiceResolve(choiceResolve.replaceAll(sourceString, targetString));
                    }
                    List<ChoiceAnswerDO> choiceAnswer = choice.getChoiceAnswer();
                    for (ChoiceAnswerDO answer : choiceAnswer) {
                        answer.setAnswerContent(answer.getAnswerContent().replaceAll(sourceString, targetString));
                        String answerResolve = answer.getAnswerResolve();
                        if(StringUtils.isNotBlank(answerResolve)) {
                            answer.setAnswerResolve(answerResolve.replaceAll(sourceString, targetString));
                        }
                    }
                    choice.setChoiceAnswer(choiceAnswer);
                } else if (TypeEnum.JUDGEMENT.getCode().toString().equals(paperConfigDO.getConfigType())) {
                    // 判断题
                    TrueFalseDO trueFalse = (TrueFalseDO) object;
                    trueFalse.setTfTitle(trueFalse.getTfTitle().replaceAll(sourceString, targetString));
                    String tfResolve = trueFalse.getTfResolve();
                    if(StringUtils.isNotBlank(tfResolve)) {
                        trueFalse.setTfResolve(tfResolve.replaceAll(sourceString, targetString));
                    }
                } else if (TypeEnum.COMPLETION.getCode().toString().equals(paperConfigDO.getConfigType())) {
                    // 填空题
                    CompletionDO completion = (CompletionDO) object;
                    completion.setCompTitle(completion.getCompTitle().replaceAll(sourceString, targetString));
                    String compResolve = completion.getCompResolve();
                    if(StringUtils.isNotBlank(compResolve)) {
                        completion.setCompResolve(compResolve.replaceAll(sourceString, targetString));
                    }
                    List<CompletionAnswerDO> answerList = completion.getAnswerList();
                    for (CompletionAnswerDO answer : answerList) {
                        answer.setAnswerContent(answer.getAnswerContent().replace(sourceString, targetString));
                        String answerResolve = answer.getAnswerResolve();
                        if(StringUtils.isNotBlank(answerResolve)) {
                            answer.setAnswerResolve(answerResolve.replaceAll(sourceString, targetString));
                        }
                    }
                    completion.setAnswerList(answerList);
                } else if (TypeEnum.PROGRAMMING.getCode().toString().equals(paperConfigDO.getConfigType())) {
                    // 编程题
                    CodeDO code = (CodeDO) object;
                    code.setCodeTitle(code.getCodeTitle().replaceAll(sourceString, targetString));
                    String codeResolve = code.getCodeResolve();
                    if(StringUtils.isNotBlank(codeResolve)) {
                        code.setCodeResolve(codeResolve.replaceAll(sourceString, targetString));
                    }
                    List<CodeAnswerDO> answerList = code.getAnswerList();
                    for (CodeAnswerDO answer : answerList) {
                        answer.setAnswerProblem(answer.getAnswerProblem().replaceAll(sourceString, targetString));
                        String answerContent = answer.getAnswerContent();
                        if(StringUtils.isNotBlank(answerContent)) {
                            answer.setAnswerContent(answerContent.replaceAll(sourceString, targetString));
                        }
                        String answerResolve = answer.getAnswerResolve();
                        if(StringUtils.isNotBlank(answerResolve)) {
                            answer.setAnswerResolve(answerResolve.replaceAll(sourceString, targetString));
                        }
                    }
                    code.setAnswerList(answerList);
                } else {
                    // 其他题
                    QuestionDO question = (QuestionDO) object;
                    question.setQuestionTitle(question.getQuestionTitle().replaceAll(sourceString, targetString));
                    String questionResolve = question.getQuestionResolve();
                    if(StringUtils.isNotBlank(questionResolve)) {
                        question.setQuestionResolve(questionResolve.replaceAll(sourceString, targetString));
                    }
                    List<QuestionAnswerDO> answerList = question.getAnswerList();
                    for (QuestionAnswerDO answer : answerList) {
                        answer.setAnswerProblem(answer.getAnswerProblem().replaceAll(sourceString, targetString));
                        String answerContent = answer.getAnswerContent();
                        if(StringUtils.isNotBlank(answerContent)) {
                            answer.setAnswerContent(answerContent.replaceAll(sourceString, targetString));
                        }
                        String answerResolve = answer.getAnswerResolve();
                        if(StringUtils.isNotBlank(answerResolve)) {
                            answer.setAnswerResolve(answerResolve.replaceAll(sourceString, targetString));
                        }
                    }
                    question.setAnswerList(answerList);
                }
            }
        }
        return paper;
    }

    /**
     * 替换所有的回车和换行
     */
    public static PaperDO replaceAllLine(PaperDO paper, String targetString) {
        paper = replaceQuestion(paper, CharConstant.JAVA_ENTER_LINE, targetString);
        // 替换所有的回车
        paper = replaceQuestion(paper, CharConstant.JAVA_ENTER, targetString);
        // 替换所有的换行
        paper = replaceQuestion(paper, CharConstant.JAVA_LINE, targetString);
        return paper;
    }

    /**
     * 替换掉freemarker中的特殊字符
     */
    public static PaperDO replaceAllForFreemarker(PaperDO paper) {
        paper = replaceQuestion(paper, CharConstant.AMP, CharConstant.AMP_TRANSFER);
        paper = replaceQuestion(paper, CharConstant.LT, CharConstant.LT_TRANSFER);
        paper = replaceQuestion(paper, CharConstant.GT, CharConstant.GT_TRANSFER);
        paper = replaceAllLine(paper, CharConstant.FREEMARKER_LINE);
        return paper;
    }

}
