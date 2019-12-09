package com.example.community.dto;

import lombok.Data;

/**
 * @Author: Yiang37
 * @Description:
 * @Date: Create in 22:25 2019/12/9
 */
@Data
public class CommentDTO {
    private Long parentId;
    private String content;
    private Integer type;
}
