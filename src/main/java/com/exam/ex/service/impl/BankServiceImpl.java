package com.exam.ex.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.core.constant.CoreConstant;
import com.exam.core.constant.SelectEnum;
import com.exam.ex.mapper.BankMapper;
import com.exam.ex.pojo.BankDO;
import com.exam.core.pojo.Page;
import com.exam.ex.pojo.TeacherDO;
import com.exam.ex.service.BankService;
import com.exam.core.utils.ShiroUtils;
import com.exam.ex.vo.BankVO;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 题库表 服务实现类
 * </p>
 *
 * @author 杨德石
 * @since 2019-03-28
 */
@Service
public class BankServiceImpl extends ServiceImpl<BankMapper, BankDO> implements BankService {

    @Autowired
    private BankMapper bankMapper;

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    @Override
    public Page<BankDO> getListByPage(Page<BankDO> page) {
        // 处理参数
        page.filterParams();
        // 处理角色
        TeacherDO loginTeacher = ShiroUtils.getLoginTeacher();
        if(!SelectEnum.SELECT_ALL.getCode().equals(loginTeacher.getTeacherOrg())) {
            // 不是查询所有，就只查询自己学院的
            page.getParams().put("orgCollege", loginTeacher.getTeacherCollege());
        }
        // 设置每页显示条数
        if (page.getCurrentCount() == null) {
            page.setCurrentCount(CoreConstant.CURRENT_COUNT);
        }
        // 计算索引
        Integer index = (page.getCurrentPage() - 1) * page.getCurrentCount();
        page.setIndex(index);
        // 查询每页数据
        List<BankDO> list = bankMapper.getListByPage(page);
        page.setList(list);
        Integer totalCount = bankMapper.getCountByPage(page);
        page.setTotalCount(totalCount);
        // 计算总页数
        page.setTotalPage((int) Math.ceil((page.getTotalCount() * 1.0) / page.getCurrentCount()));
        return page;
    }

    /**
     * 获取试卷基本信息
     *
     * @return
     */
    @Override
    public List<BankVO> getBankInfo() {
        Map<String, Object> paramsMap = Maps.newHashMap();
        TeacherDO loginTeacher = ShiroUtils.getLoginTeacher();
        if(!SelectEnum.SELECT_ALL.getCode().equals(loginTeacher.getTeacherOrg())) {
            // 不是查询所有，就只查询自己学院的
            paramsMap.put("orgCollege", loginTeacher.getTeacherCollege());
        }
        List<BankVO> bankVOList = bankMapper.getBankPaperNum(paramsMap);

        List<BankVO> choiceNumList = bankMapper.getBankChoiceNum(paramsMap);
        List<BankVO> tfNumList = bankMapper.getBankTfNum(paramsMap);
        List<BankVO> compNumList = bankMapper.getBankCompNum(paramsMap);
        List<BankVO> codeNumList = bankMapper.getBankCodeNum(paramsMap);
        List<BankVO> questionNumList = bankMapper.getBankQuestionNum(paramsMap);

        for (int i = 0; i < choiceNumList.size(); i++) {
            Integer choiceNum = choiceNumList.get(i).getQuestionNum();
            Integer tfNum = tfNumList.get(i).getQuestionNum();
            Integer compNum = compNumList.get(i).getQuestionNum();
            Integer codeNum = codeNumList.get(i).getQuestionNum();
            Integer questionNum = questionNumList.get(i).getQuestionNum();
            bankVOList.get(i).setQuestionNum(choiceNum + tfNum + compNum + codeNum + questionNum);
        }

        return bankVOList;
    }
}
