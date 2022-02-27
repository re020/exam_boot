package com.exam.ts.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.core.constant.ResultEnum;
import com.exam.core.pojo.Page;
import com.exam.core.utils.IdWorker;
import com.exam.core.utils.Result;
import com.exam.core.utils.ShiroUtils;
import com.exam.ex.pojo.StudentDO;
import com.exam.ts.mapper.ExamMapper;
import com.exam.ts.mapper.ExamStudentMapper;
import com.exam.ts.pojo.ExamDO;
import com.exam.ts.pojo.ExamStudentDO;
import com.exam.ts.pojo.DTO.StudentDTO;
import com.exam.ts.service.ExamStudentService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 考试-学生对应表 服务实现类
 * </p>
 *
 * @author 杨德石
 * @since 2019-05-24
 */
@Service
public class ExamStudentServiceImpl extends ServiceImpl<ExamStudentMapper, ExamStudentDO> implements ExamStudentService {

    @Autowired
    private ExamStudentMapper examStudentMapper;
    @Autowired
    private ExamMapper examMapper;
    @Autowired
    private IdWorker idWorker;

    /**
     * 校验后添加
     *
     * @param examStudentDO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result checkAndSave(ExamStudentDO examStudentDO) {
        // 根据考试id和学生id，查询未结束的考试中是否有这个学生
        ExamStudentDO exam = examStudentMapper.getByPojo(examStudentDO);
        // 如果有，就返回并且提示，没有就保存
        if (exam != null) {
            return Result.build(ResultEnum.ERROR.getCode(), "该考生已在本场考试中！");
        }
        // 添加
        examStudentDO.setStId(idWorker.nextId() + "");
        examStudentMapper.insert(examStudentDO);
        return Result.ok("添加成功！");
    }

    /**
     * 批量添加
     *
     * @param examId
     * @param studentIds
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveList(String examId, List<String> studentIds) {
        // 先根据试卷id和学生id列表查询出对应的数据
        List<ExamStudentDO> examStudentDoList = examStudentMapper.getByExamIdAndStudentIds(examId, studentIds);
        // 获取学生id列表
        List<String> oldIds = examStudentDoList.stream().map(ExamStudentDO::getStStu).collect(Collectors.toList());
        // 将已有的从参数中清除
        studentIds.removeAll(oldIds);
        List<ExamStudentDO> list = Lists.newArrayList();
        for (String studentId : studentIds) {
            ExamStudentDO examStudentDO = new ExamStudentDO();
            examStudentDO.setStId(idWorker.nextId() + "");
            examStudentDO.setStExam(examId);
            examStudentDO.setStStu(studentId);
            list.add(examStudentDO);
        }

        // 批量保存
        this.saveBatch(list);
    }

    /**
     * 查询本场考试的所有学生
     *
     * @param examId
     * @return
     */
    @Override
    public List<ExamStudentDO> getListByExam(String examId) {
        return examStudentMapper.getListByExam(examId);
    }

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    @Override
    public Page<ExamStudentDO> getByPage(Page<ExamStudentDO> page) {
        page.filterParams();
        // 计算索引
        Integer index = (page.getCurrentPage() - 1) * page.getCurrentCount();
        page.setIndex(index);
        // 查询每页数据
        List<ExamStudentDO> list = examStudentMapper.getListByPage(page);
        page.setList(list);
        Integer totalCount = examStudentMapper.getCountByPage(page);
        page.setTotalCount(totalCount);
        // 计算总页数
        page.setTotalPage((int) Math.ceil((page.getTotalCount() * 1.0) / page.getCurrentCount()));
        return page;
    }

    /**
     * 查询学生的考试
     *
     * @param page
     * @return
     */
    @Override
    public Page<ExamDO> getList(Page<ExamDO> page) {
        page.filterParams();
        // 计算索引
        Integer index = (page.getCurrentPage() - 1) * page.getCurrentCount();
        page.setIndex(index);
        Map<String, Object> params = page.getParams();
        StudentDO loginStudent = ShiroUtils.getLoginStudent();
        params.put("stuId", loginStudent.getStuId());
        // 查询每页数据
        List<ExamDO> list = examMapper.getListByStu(page);
        page.setList(list);
        Integer totalCount = examMapper.getCountByPage(page);
        page.setTotalCount(totalCount);
        // 计算总页数
        page.setTotalPage((int) Math.ceil((page.getTotalCount() * 1.0) / page.getCurrentCount()));
        return page;
    }


}
