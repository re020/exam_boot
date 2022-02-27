package com.exam.ex.controller;

import com.exam.ex.service.BankService;
import com.exam.core.utils.Result;
import com.exam.ex.vo.BankVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户控制台数据
 * @Author: 杨德石
 * @Date: 2019/5/9 0009 下午 6:25
 * @Version 1.0
 */
@RestController
@RequestMapping("/console")
public class ConsoleController {

    @Autowired
    private BankService bankService;

    /**
     * 查看每个题库的基本信息
     * @return
     */
    @RequestMapping(value = "/bankInfo", method = RequestMethod.GET)
    public Result bankPaperNum() {
        List<BankVO> bankVOList = bankService.getBankInfo();
        return Result.ok(bankVOList);
    }

}
