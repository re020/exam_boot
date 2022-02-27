package com.exam.ts;
import java.math.BigDecimal;
import com.google.common.collect.Lists;
import com.exam.ts.pojo.StudentAnswerDO;
import com.exam.ex.pojo.CompileDO;

import com.exam.core.utils.IdWorker;
import com.exam.ex.mapper.CodeMapper;
import com.exam.ex.pojo.CodeDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author lth
 * @date 2019年11月22日 13:05
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class CodeTest {

    @Autowired
    private CodeMapper codeMapper;
    @Autowired
    private IdWorker idWorker;

    @Test
    public void addCode(){
        CodeDO codeDO = new CodeDO();
        codeDO.setCodeId(idWorker.nextId() + "");
        codeDO.setCodeTitle("编写一个应用程序，给出汉字‘你’、‘我’、‘他’在Unicode表中的位置");
        codeDO.setCodeImgs("");
        codeDO.setCodeType("5");
        codeDO.setCodeScore(new BigDecimal("10"));
        codeDO.setCodeDifficulty(2);
        codeDO.setCodeBank("1111913413874835456");
        codeDO.setCodeKnow("");
        codeDO.setCodeResolve("");
        codeDO.setCodeCompile("");
        codeDO.setCodeVersion(0);
        codeDO.setCodeDelete(0);
    }

}
