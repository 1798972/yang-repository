package com.example.community.dto;

import lombok.Data;

@Data
public class GitHubUser {
    private String name;
    private Long id;
    private String bio;
    private String avatarUrl;  //fastjson可以自动把avatar_url转换成驼峰型变量名 让其符合java命名规则
}
