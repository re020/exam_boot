package com.exam.core.constant;

/**
 * 核心常量类
 * 用来放一些、比较重要的常量
 * @version 1.0
 * @author: 杨德石
 * @date: 2019/4/24 0024 下午 8:05
 */
public class CoreConstant {

    private CoreConstant() {}


    /**
     * 图片上传路径
     */
    public final static String IMG_URL = "E:\\【最新】大学生创新创业训练计划项目结项通知\\结题材料\\photo\\";

    /**
     * 试卷保存路径
     */
    public final static String PAPER_URL = "E:\\【最新】大学生创新创业训练计划项目结项通知\\结题材料\\photo\\";

    /**
     * freemarker模板所在的目录
     */
    public static final String TEMPLATE_FOLD = "/static/";

    /**
     * freemarker模板名
     */
    public static final String TEMPLATE_FILE_NAME = "普通导出模板，格式正确但不支持富文本.ftl";

    /**
     * 服务器文件地址
     */
    public final static String SERVER_FILE_URL = "http://localhost:8080/file/";

    /**
     * redis过期时间
     */
    public final static Long REDIS_TIMEOUT = 3600000L;

    /**
     * 分页每页默认显示条数
     */
    public final static Integer CURRENT_COUNT = 10;


}
