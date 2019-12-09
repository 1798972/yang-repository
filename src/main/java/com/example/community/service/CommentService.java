package com.example.community.service;

import com.example.community.mapper.CommentMapper;
import com.example.community.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Yiang37
 * @Description:
 * @Date: Create in 22:27 2019/12/9
 */
@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    public void insert(Comment comment) {
        commentMapper.insert(comment);
    }
}
