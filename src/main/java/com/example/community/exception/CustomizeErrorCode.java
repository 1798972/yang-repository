package com.example.community.exception;

/**
 * @Author: Yiang37
 * @Description:
 * @Date: Create in 22:44 2019/12/8
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode {

    //enum！！！！
    //1.枚举的内容写在最前面！
    QUESTION_NOT_FOUND("要找的问题不在了，换一个试试？");

    //成员变量message的依靠枚举值传递
    private String message;
    //构造方法而已
    CustomizeErrorCode(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
