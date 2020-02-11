package com.example.community.exception;

/**
 * @Author: Yiang37
 * @Description:
 * @Date: Create in 22:44 2019/12/8
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode {

    //enum！！！！
    //1.枚举的内容写在最前面！
    QUESTION_NOT_FOUND(2001, "要找的问题不在了，换一个试试？"),
    TARGET_PATH_NOT_FOUND(2002, "未选中任何问题或者评论进行回复！"),
    NOT_LOGIN(2003, "用户未登录，请先登录！"),
    SYSTEM_ERROR(2004, "服务器冒烟了，请稍后再试！"),
    TYPE_PARAM_WRONG(2005, "评论类型错误或者评论不存在！"),
    COMMENT_NOT_FOUND(2006, "你要找的评论不存在！"),
    COMMENT_ISEMPTY(2007, "回复的内容为空！");

    //成员变量message的依靠枚举值传递
    private String message;
    private Integer code;


    //构造方法而已
    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
