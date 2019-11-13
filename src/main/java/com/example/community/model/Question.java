package com.example.community.model;

import lombok.Data;

@Data
public class Question {
//    tittle,description,gmt_create,gmt_modified,creator,tag
    private Integer id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private String tag;
    private Integer creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
}