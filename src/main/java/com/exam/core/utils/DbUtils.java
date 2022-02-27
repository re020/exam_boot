package com.exam.core.utils;

import com.exam.core.constant.CharConstant;
import com.exam.core.constant.DbConstant;
import com.exam.core.constant.ExtConstant;
import com.exam.core.constant.OtherConstant;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * 数据库相关工具类
 * @author 杨德石
 * @date
 */
public class DbUtils {

    private DbUtils() {
    }

    /**
     * 备份
     */
    public static void backup() {
        try {
            Runtime rt = Runtime.getRuntime();

            // 调用 调用mysql的安装目录的命令
            // 三个常量分别是用户名、密码、数据库名
            Process child = rt
                    .exec(" mysqldump -h localhost -u" + DbConstant.DATABASE_USER + " -p" + DbConstant.DATABASE_PASS + " " + DbConstant.DATABASE_NAME);
            // 设置导出编码为utf-8。这里必须是utf-8
            // 把进程执行中的控制台输出信息写入.sql文件，即生成了备份文件。注：如果不对控制台信息进行读出，则会导致进程堵塞无法运行
            // 控制台的输出信息作为输入流
            InputStream in = child.getInputStream();

            InputStreamReader xx = new InputStreamReader(in, StandardCharsets.UTF_8);
            // 设置输出流编码为utf-8。这里必须是utf-8，否则从流中读入的是乱码

            String inStr;
            // 这里的常量是空字符串""
            StringBuffer sb = new StringBuffer(CharConstant.CHAR_NULL_STRING);
            String outStr;
            // 组合控制台输出信息字符串
            BufferedReader br = new BufferedReader(xx);
            while ((inStr = br.readLine()) != null) {
                sb.append(inStr + "\r\n");
            }
            outStr = sb.toString();

            // 要用来做导入用的sql目标文件：
            // 这里就是导出的目录+文件名+后缀，文件名我用199701010101这样的格式
            String filename = DbConstant.BACK_UP_PATH + DateUtils.newDateByFormat(OtherConstant.FILE_DEFAULT_DATETIME_FORMATTER) + ExtConstant.SQL_EXT;
            FileOutputStream fout = new FileOutputStream(filename);
            OutputStreamWriter writer = new OutputStreamWriter(fout, StandardCharsets.UTF_8);
            writer.write(outStr);
            writer.flush();
            in.close();
            xx.close();
            br.close();
            writer.close();
            fout.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 还原
     * 参数和备份其实是 一样的
     */
    public static void restore() {
        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime
                    .exec(" mysql.exe -hlocalhost -u" + DbConstant.DATABASE_USER + " -p" + DbConstant.DATABASE_PASS + " --default-character-set=utf8 "
                            + DbConstant.DATABASE_NAME);
            OutputStream outputStream = process.getOutputStream();
            String filename = DbConstant.BACK_UP_PATH + DateUtils.newDateByFormat(OtherConstant.FILE_DEFAULT_DATETIME_FORMATTER) + ExtConstant.SQL_EXT;
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_8));
            String str;
            StringBuffer sb = new StringBuffer();
            while ((str = br.readLine()) != null) {
                sb.append(str + "\r\n");
            }
            str = sb.toString();
            OutputStreamWriter writer = new OutputStreamWriter(outputStream,
                    StandardCharsets.UTF_8);
            writer.write(str);
            writer.flush();
            outputStream.close();
            br.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
