package com.example.community.exception;

import lombok.Data;

/**
 * @Author: Yiang37
 * @Description:
 * @Date: Create in 22:29 2019/12/8
 */
@Data
public class CustomizeException extends RuntimeException {
    //c 没有写get方法!!! 返回的默认值null！！！！！
    private String message;
    private Integer code;
    //1.带参数构造 但是传递的内容得自己打
    //eg: new CustomizeException("页面找不到了")
//    public  CustomizeException(String message){
//        this.message = message;
//    }

    //2.传递的是个枚举
    public CustomizeException(ICustomizeErrorCode errorCode){
        this.message =  errorCode.getMessage();
        this.code = errorCode.getCode();
    }

}
