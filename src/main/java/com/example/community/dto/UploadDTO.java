package com.example.community.dto;

import lombok.Data;

/**
 * @Author Yiang37
 * @Date 2020/2/25 20:37
 * Description:
 */
@Data
public class UploadDTO {
//    {
//        success : 0 | 1,           // 0 表示上传失败，1 表示上传成功
//                message : "提示的信息，上传成功或上传失败及错误信息等。",
//            url     : "图片地址"        // 上传成功时才返回
//    }
    private int success;
    private String message;
    private String url;
}