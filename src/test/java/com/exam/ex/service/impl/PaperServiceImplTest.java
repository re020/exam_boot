package com.exam.ex.service.impl;

import com.exam.core.constant.CoreConstant;
import com.exam.ex.pojo.PaperDO;
import com.exam.ex.service.PaperConfigService;
import com.exam.ex.service.PaperService;
import com.exam.core.utils.ToWordUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @version 1.0
 * @author: 杨德石
 * @date: 2019/4/24 0024 下午 5:31
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class PaperServiceImplTest {

    @Autowired
    private PaperService paperService;
    @Autowired
    private PaperConfigService paperConfigService;

    /**
     * 生成试卷
     */
    @Test
    public void getQuestion() throws Exception {
//        String paperId = "1129257735157456896";
//        PaperDO paper = paperService.getQuestion(paperId);
//
//        ToWordUtil toWordUtil = new ToWordUtil(CoreConstant.TEMPLATE_FOLD);
//        toWordUtil.setTemplateName(CoreConstant.TEMPLATE_FILE_NAME);
//        String filename = paper.getPaperTitle()+".doc";
//        toWordUtil.setFileName(filename);
//        toWordUtil.setFilePath(CoreConstant.PAPER_URL);
//        toWordUtil.createWord(paper);

    }
}