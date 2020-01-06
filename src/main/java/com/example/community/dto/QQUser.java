package com.example.community.dto;

import lombok.Data;

@Data
public class QQUser {
    private String nickname;
    private String msg;
    //头像
    private String figureurl2;  //fastjson可以自动把avatar_url转换成驼峰型变量名 让其符合java命名规则
}
