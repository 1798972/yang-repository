package com.example.community.controller;

import com.example.community.dto.CommentClickDTO;
import com.example.community.dto.CommentDTO;
import com.example.community.dto.ResultDTO;
import com.example.community.exception.CustomizeErrorCode;
import com.example.community.model.Comment;
import com.example.community.model.User;
import com.example.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: Yiang37
 * @Description:
 * @Date: Create in 22:23 2019/12/9
 */
@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    //对文章的评论 在ajax操作中指定了类型
    //ResponseBody用于对象格式转换
    //对于Controller方法返回的对象，转换为指定格式的数据如：json,xml等，通过Response响应给客户端。
    @ResponseBody
    @RequestMapping("/comment")
    public Object post(@RequestBody CommentClickDTO commentClickDTO,
                       HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");

        //返回给浏览器一个json
        //$.ajax(
        //       success:function (response)
        //       ）中做处理
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NOT_LOGIN);
        }

        //回复内容为空
        if (StringUtils.isEmpty(commentClickDTO.getContent())) {
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

    //对评论的评论
    @ResponseBody   //返回json格式时需要这个
    @RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
    public ResultDTO<List<CommentDTO>> comments(@PathVariable(name = "id")Long id){
        //@PathVariable 可以将请求中的/{xxx}部分 映射到方法的形参中
        List<CommentDTO> CommentDTOs = commentService.findByCommentId(id);
        return ResultDTO.okOf(CommentDTOs);
    }
}
