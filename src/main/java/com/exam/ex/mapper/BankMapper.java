package com.exam.ex.mapper;

import com.exam.ex.pojo.BankDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exam.core.pojo.Page;
import com.exam.ex.vo.BankVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 题库表 Mapper 接口
 * </p>
 *
 * @author 杨德石
 * @since 2019-03-28
 */
public interface BankMapper extends BaseMapper<BankDO> {

    /**
     * 分页查询
     * @param page
     * @return
     */
    List<BankDO> getListByPage(Page<BankDO> page);

    /**
     * 查询总数
     * @param page
     * @return
     */
    Integer getCountByPage(Page<BankDO> page);

    /**
     * 获取每个题库的试卷数
     * @param paramsMap
     * @return
     */
    List<BankVO> getBankPaperNum(Map<String, Object> paramsMap);

    /**
     * 获取题库中选择题数量
     * @param paramsMap
     * @return
     */
    List<BankVO> getBankChoiceNum(Map<String, Object> paramsMap);

    /**
     * 判断题数量
     * @param paramsMap
     * @return
     */
    List<BankVO> getBankTfNum(Map<String, Object> paramsMap);

    /**
     * 填空题数量
     * @param paramsMap
     * @return
     */
    List<BankVO> getBankCompNum(Map<String, Object> paramsMap);

    /**
     * 编程题数量
     * @param paramsMap
     * @return
     */
    List<BankVO> getBankCodeNum(Map<String, Object> paramsMap);

    /**
     * 简答题数量
     * @param paramsMap
     * @return
     */
    List<BankVO> getBankQuestionNum(Map<String, Object> paramsMap);
}
