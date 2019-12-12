package com.example.community.service;

import com.example.community.enums.CommentTypeEnums;
import com.example.community.exception.CustomizeErrorCode;
import com.example.community.exception.CustomizeException;
import com.example.community.mapper.CommentMapper;
import com.example.community.mapper.QuestionMapper;
import com.example.community.model.Comment;
import com.example.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: Yiang37
 * @Description:
 * @Date: Create in 22:27 2019/12/9
 */
@Service
public class CommentService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private CommentMapper commentMapper;

    @Transactional
    public void insert(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0){

            //CustomizeExceptionHandler中做处理
            throw new CustomizeException(CustomizeErrorCode.TARGET_PATH_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentTypeEnums.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        if (comment.getType() == CommentTypeEnums.COMMENT.getType()){
            //回复的是评论
            Comment dbComment = commentMapper.selectById(comment.getId());
            if (dbComment == null){
             throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
                commentMapper.insert(comment);
        }

        else {
            //回复的是问题
           Question question =  questionMapper.findById(comment.getParentId());
           if (question == null){
               throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
           }
               commentMapper.insert(comment);
               //评论数加1
//                question.setCommentCount(1);
               questionMapper.increaseCommentCounnt(comment.getParentId());
        }
    }
}
