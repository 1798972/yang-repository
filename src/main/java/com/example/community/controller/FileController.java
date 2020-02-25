package com.example.community.controller;

import com.example.community.dto.UploadDTO;
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
    public UploadDTO uploadFile(HttpServletRequest request){
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        //MultipartFile 这个类一般是用来接受前台传过来的文件
        MultipartFile file = multipartHttpServletRequest.getFile("editormd-image-file");

        //进行文件的上传操作
        try {
            qCloudProvider.upload(file.getInputStream() != null ? file.getInputStream()  : null,file.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //返回成功的json
        UploadDTO uploadDTO = new UploadDTO();
        uploadDTO.setSuccess(1);
        uploadDTO.setMessage("成功！");
        uploadDTO.setUrl("/img/vxImage.png");

        return uploadDTO;
    }
}