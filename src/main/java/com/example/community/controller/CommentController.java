package com.example.community.controller;

import com.example.community.dto.CommentClickDTO;
import com.example.community.dto.ResultDTO;
import com.example.community.exception.CustomizeErrorCode;
import com.example.community.model.Comment;
import com.example.community.model.User;
import com.example.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Yiang37
 * @Description:
 * @Date: Create in 22:23 2019/12/9
 */
@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    //ResponseBody用于对象格式转换
    @ResponseBody
    @RequestMapping("/comment")
    public Object post(@RequestBody CommentClickDTO commentClickDTO,
                       HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        if (user == null){
            //返回给浏览器一个json
            //$.ajax(
            //       success:function (response)
            //       ）中做处理
            return ResultDTO.errorOf(CustomizeErrorCode.NOT_LOGIN);
        }

        //回复内容为空
        if (StringUtils.isEmpty(commentClickDTO.getContent())){
            return ResultDTO.errorOf(CustomizeErrorCode.COMMENT_ISEMPTY);
        }

        Comment comment = new Comment();

        comment.setParentId(commentClickDTO.getParentId());
        comment.setType(commentClickDTO.getType());
        comment.setContent(commentClickDTO.getContent());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0);

        commentService.insert(comment);
        return ResultDTO.okOf();
    }
}
