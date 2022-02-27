package com.exam.ex.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: 杨德石
 * @Date: 2019/5/18 0018 下午 12:43
 * @Version 1.0
 */
public interface ExpertService {

    void expertWord(HttpServletRequest request, HttpServletResponse response, String title, String text);

}
