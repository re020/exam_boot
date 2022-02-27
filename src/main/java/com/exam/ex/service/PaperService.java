package com.exam.ex.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.ex.dto.GaPaperDTO;
import com.exam.core.pojo.Page;
import com.exam.ex.pojo.PaperConfigDO;
import com.exam.ex.pojo.PaperDO;

import java.util.List;

/**
 * <p>
 * 试卷表 服务类
 * </p>
 *
 * @author 杨德石
 * @since 2019-04-20
 */
public interface PaperService extends IService<PaperDO> {

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    Page<PaperDO> getByPage(Page<PaperDO> page);

    /**
     * 查询题目列表
     *
     * @param paperId
     * @return
     */
    PaperDO getQuestion(String paperId);

    /**
     * 提交组卷
     *
     * @param paperId
     * @throws Exception
     */
    void submit(String paperId) throws Exception;

    /**
     * 根据试卷id和题目id从试卷中删除题目
     *
     * @param paperId
     * @param questionId
     */
    void deleteQuestion(String paperId, String questionId);

    /**
     * 遗传算法智能组卷
     * @param paperDTO
     * @throws Exception
     */
    void gaSubmitPaper(GaPaperDTO paperDTO) throws Exception;

    /**
     * 查看每个题型的题目数
     * @param id
     * @return
     */
    List<PaperConfigDO> getTypeNum(String id);

}
