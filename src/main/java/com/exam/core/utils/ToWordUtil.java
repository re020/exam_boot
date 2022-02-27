package com.exam.core.utils;

import com.exam.ex.pojo.PaperDO;
import com.exam.ts.pojo.StudentPaperDO;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.Data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * 生成word文档的工具
 *
 * @Author: 杨德石
 * @Date: 2019/4/29 0029 下午 1:34
 * @Version 1.0
 */
@Data
public class ToWordUtil {
    /**
     * @param templatePath 模板文件位置
     */
    public ToWordUtil(String templatePath) {
        configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        configuration.setDefaultEncoding("utf-8");
        configuration.setClassForTemplateLoading(this.getClass(), templatePath);
    }

    private Configuration configuration;
    private String templateName;
    /**
     * 生成文件名
     */
    private String fileName;
    /**
     * 生成文件路径
     */
    private String filePath;

    public void createWord(PaperDO paper) throws Exception {

        Template template = null;
        try {
            template = getConfiguration().getTemplate(getTemplateName());
        } catch (IOException e) {
            e.printStackTrace();
        }

        File outFile = new File(getFilePath() + getFileName());
        Writer out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        paper = PaperUtil.replaceAllForFreemarker(paper);
        try {
            Objects.requireNonNull(template).process(paper, out);
            Objects.requireNonNull(out).close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
