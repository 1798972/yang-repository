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
    private Long parentId;
    private String content;
    private Integer type;
}
