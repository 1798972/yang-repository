package com.example.community.dto;

import com.example.community.model.User;
import lombok.Data;

/**
 * @Author: Yiang37
 * @Description:
 * @Date: Create in 22:06 2019/12/13
 */
@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private String content;
    private User user;
    private Integer commentCount;
}
