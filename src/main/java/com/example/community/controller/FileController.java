package com.example.community.controller;

import com.example.community.dto.UploadDTO;
import com.example.community.exception.CustomizeErrorCode;
import com.example.community.exception.CustomizeException;
import com.example.community.provider.FileProvider;
import com.example.community.provider.QCloudProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * @Author Yiang37
 * @Date 2020/2/25 20:35
 * Description:用于处理使用编辑器中完成文件上传的操作
 */
@Controller
public class FileController {
    @Autowired
    private QCloudProvider qCloudProvider;

    @ResponseBody
    @PostMapping("/file/upload")
    public UploadDTO uploadFile(HttpServletRequest request) throws IOException {
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        //MultipartFile 这个类一般是用来接受前台传过来的文件
        //在spring上传文件中，一般都使用了MultipartFile来接收
        MultipartFile file = multipartHttpServletRequest.getFile("editormd-image-file");

        //进行文件的上传操作
        try {
            //转换成File
            File targetFile = FileProvider.multipartFileToFile(file);
            String url = qCloudProvider.upload(targetFile != null ? targetFile : null, file.getOriginalFilename());
            FileProvider.delteTempFile(targetFile);

            //返回成功的json
            UploadDTO uploadDTO = new UploadDTO();
            uploadDTO.setSuccess(1);
            uploadDTO.setMessage("上传成功！");
            uploadDTO.setUrl(url);

            return uploadDTO;
        } catch (Exception e) {
            throw new CustomizeException(CustomizeErrorCode.UPLOAD_ERROR);
        }
    }
}