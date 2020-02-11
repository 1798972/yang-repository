package com.example.community.model;

import lombok.Data;

/**
 * @Author: Yiang37
 * @Description:
 * @Date: Create in 22:30 2019/12/9
 */
@Data
public class Comment {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private String content;
    private Integer likeCount;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer commentCount;
}
