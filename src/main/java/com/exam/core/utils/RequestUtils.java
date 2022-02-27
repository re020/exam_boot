package com.exam.core.utils;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 请求工具类
 *
 * @Author: 杨德石
 * @Date: 2019/5/14 0014 下午 4:12
 * @Version 1.0
 */
public class RequestUtils {

    private static final String GET_METHOD = "GET";

    private RequestUtils() {
    }

    public static JSONObject getRequestJsonObject(HttpServletRequest request) throws IOException {
        String json = getRequestJsonString(request);
        return JSONObject.parseObject(json);
    }

    /***
     * 获取 request 中 json 字符串的内容
     *
     * @param request
     * @return : <code>byte[]</code>
     * @throws IOException
     */
    private static String getRequestJsonString(HttpServletRequest request)
            throws IOException {
        String submitMethod = request.getMethod();
        // GET
        if (GET_METHOD.equals(submitMethod)) {
            return new String(request.getQueryString().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8).replaceAll("%22", "\"");
            // POST
        } else {
            return getRequestPostStr(request);
        }
    }

    /**
     * 描述:获取 post 请求的 byte[] 数组
     *
     * @param request
     * @return
     * @throws IOException
     */
    private static byte[] getRequestPostBytes(HttpServletRequest request)
            throws IOException {
        int contentLength = request.getContentLength();
        if (contentLength < 0) {
            return null;
        }
        byte[] buffer = new byte[contentLength];
        for (int i = 0; i < contentLength; ) {

            int readlen = request.getInputStream().read(buffer, i,
                    contentLength - i);
            if (readlen == -1) {
                break;
            }
            i += readlen;
        }
        return buffer;
    }

    /**
     * 描述:获取 post 请求内容
     *
     * @param request
     * @return
     * @throws IOException
     */
    private static String getRequestPostStr(HttpServletRequest request)
            throws IOException {
        byte[] buffer = getRequestPostBytes(request);
        String charEncoding = request.getCharacterEncoding();
        if (charEncoding == null) {
            charEncoding = "UTF-8";
        }
        return new String(buffer, charEncoding);
    }

}
