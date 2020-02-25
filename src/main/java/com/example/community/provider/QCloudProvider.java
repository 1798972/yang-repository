package com.example.community.provider;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;


/**
 * @Author Yiang37
 * @Date 2020/2/25 21:10
 * Description: 腾讯云对象存储
 */
@Component
public class QCloudProvider {

    @Value("${qcloud.secretid}")
    private String secretid;
    @Value("${qcloud.secretkey}")
    private String secretkey;

    //桶的名字
    String bucketName = "yang-cdisk-1258124344";
    /*// 指定要上传到 COS 上对象键
    static String key = "mydemo.jpg";*/

    // 简单文件上传, 最大支持 5 GB, 适用于小文件上传, 建议 20M以下的文件使用该接口
    //file里面填写本地图片的位置 我这里是相对项目的位置，在项目下有src/test/demo.jpg这张图片
    public String upload(InputStream inputStream, String fileName) {
        // 1 初始化用户身份信息
        COSCredentials cred = new BasicCOSCredentials(secretid, secretkey);
        // 2 设置bucket的区域
        ClientConfig clientConfig = new ClientConfig(new Region("ap-chengdu"));
        // 3 生成cos客户端
        COSClient cosClient = new COSClient(cred, clientConfig);

        //InputStream处理文件
        File localFile = new File("");
        //名字的处理
        String targetName ;
        String[] path = fileName.split("//.");
        if (path.length > 1) {
            targetName = UUID.randomUUID().toString() + "." + path[path.length - 1];
        }else {
            return null;
        }
        //动态生成对象键名
        String key = targetName;
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        String etag = putObjectResult.getETag();  // 获取文件的 etag
        System.out.println("信息----------"+etag);
        return etag;
    }

    // 设置要下载到的本地路径
//    public void download() {
//        File downFile = new File("src/test/medemo.jpg");
//        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
//        ObjectMetadata downObjectMeta = cosClient.getObject(getObjectRequest, downFile);
//    }

    // 指定要删除的 bucket 和对象键
//    public void del() {
//        cosClient.deleteObject(bucketName, key);
//    }
}