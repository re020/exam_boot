package com.exam.ex.controller;

import com.exam.core.constant.CoreConstant;
import com.exam.core.constant.ExtConstant;
import com.exam.core.utils.Result;
import com.exam.core.utils.StringUtils;
import com.google.common.collect.Maps;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * 文件上传Controller
 *
 * @version 1.0
 * @author: 杨德石
 * @date: 2019/3/28 0028 下午 9:07
 */
@RestController
public class UploadController {

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Result upload(MultipartFile file) {
        String name = UUID.randomUUID().toString().replaceAll("-", "");
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        if (StringUtils.isNotPictureExt(ext)) {
            // 后缀不是图片，就默认赋值为图片后缀名
            ext = ExtConstant.PNG_EXT;
        }
        String fileName = name + "." + ext;
        try {
            file.transferTo(new File(CoreConstant.IMG_URL + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String fileServerName = CoreConstant.SERVER_FILE_URL + fileName;

        Map<String, Object> data = Maps.newHashMap();
        data.put("fileUrl", fileServerName);
        return Result.ok("上传成功", data);
    }

}
