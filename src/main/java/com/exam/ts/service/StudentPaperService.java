package com.exam.ts.service;

import com.exam.core.constant.CoreConstant;
import com.exam.core.constant.SelectEnum;
import com.exam.core.pojo.Page;
import com.exam.core.utils.ShiroUtils;
import com.exam.ex.pojo.PaperDO;
import com.exam.ex.pojo.TeacherDO;
import com.exam.ts.pojo.StudentPaperDO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.ts.pojo.DTO.ExPaperDTO;

import java.util.List;

/**
 * <p>
 * 学生试卷表 服务类
 * </p>
 *
 * @author 杨德石
 * @since 2019-05-24
 */
public interface StudentPaperService extends IService<StudentPaperDO> {


    /**
     * 遗传算法智能批量组卷
     * @param exPaperDTO
     * @throws Exception
     */
    void gaExam(ExPaperDTO exPaperDTO) throws Exception;

    /**
     * 根据考试id得到试卷列表
     */
    Page<StudentPaperDO> listById(String id);
    /**
     * 获得学生试卷的答题情况
     */
    StudentPaperDO getPaperInfo(String id);

    /**
     * 对该学生成绩进行提交，根据各个配置计算出总的成绩
     */
    void correctSubmit(String paperId);

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    Page<PaperDO> getByPage(Page<PaperDO> page);

}
