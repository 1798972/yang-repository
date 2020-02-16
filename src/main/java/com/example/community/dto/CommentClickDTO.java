package com.example.community.dto;

import lombok.Data;

/**
 * @Author: Yiang37
 * @Description:
 * @Date: Create in 22:25 2019/12/9
 */

//CommentClickDTO 点击评论时传递的评论内容
@Data
public class CommentClickDTO {
    //文章的id
    private Long parentId;
    private String content;
    //1是对文章的评论 2是对评论的评论
    private Integer type;
}
