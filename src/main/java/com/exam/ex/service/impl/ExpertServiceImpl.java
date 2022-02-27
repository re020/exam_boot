package com.exam.ex.service.impl;

import com.exam.ex.service.ExpertService;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;

/**
 * @Author: 杨德石
 * @Date: 2019/5/18 0018 下午 12:43
 * @Version 1.0
 */
@Service
public class ExpertServiceImpl implements ExpertService {

    @Override
    public void expertWord(HttpServletRequest request, HttpServletResponse response, String title, String text) {
        try {
            //word内容
            String content = "<html><body>" +
                    "<p style=\"text-align: center;\"><span style=\"font-family: 黑体, SimHei; font-size: 24px;\">"
                    + title + "</span></p>" + text + "</body></html>";
            //这里是必须要设置编码的，不然导出中文就会乱码。
            byte[] b = content.getBytes("GBK");
            //将字节数组包装到流中
            ByteArrayInputStream bais = new ByteArrayInputStream(b);

            /*
             * 关键地方
             * 生成word格式 */
            POIFSFileSystem poifs = new POIFSFileSystem();
            DirectoryEntry directory = poifs.getRoot();
            DocumentEntry documentEntry = directory.createDocument("WordDocument", bais);
            //输出文件
            request.setCharacterEncoding("utf-8");
            //导出word格式
            response.setContentType("application/msword");
            response.addHeader("Content-Disposition", "attachment;filename=" +
                    new String(title.getBytes("GB2312"), "iso8859-1") + ".doc");
            ServletOutputStream ostream = response.getOutputStream();
            poifs.writeFilesystem(ostream);
            bais.close();
            ostream.close();
            poifs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
