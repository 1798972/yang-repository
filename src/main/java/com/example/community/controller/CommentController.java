package com.example.community.controller;

import com.example.community.dto.CommentDTO;
import com.example.community.mapper.CommentMapper;
import com.example.community.model.Comment;
import com.example.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: Yiang37
 * @Description:
 * @Date: Create in 22:23 2019/12/9
 */
@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;
    @RequestMapping("/comment")
    public Object post(@RequestBody CommentDTO commentDTO){
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setType(commentDTO.getType());
        comment.setContext(commentDTO.getContent());
        commentService.insert(comment);
        return null;
    }
}
