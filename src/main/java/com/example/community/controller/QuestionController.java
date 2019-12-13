package com.example.community.controller;

import com.example.community.dto.CommentDTO;
import com.example.community.dto.QuestionDTO;
import com.example.community.service.CommentService;
import com.example.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @Author: Yiang37
 * @Description:
 * @Date: Create in 10:49 2019/11/28
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;
    @GetMapping("/question/{questionId}")
    String questionDetail(@PathVariable("questionId")Long questionId,
                         Model model){
        //1.得到文问题id problem_id
        //2.根据问题id查找得到问题数据
            //在查看问题之前 让阅读数加1
        questionService.increaseViewCount(questionId);

        //QuestionDTO中有新加的User属性
        QuestionDTO questionDTO = questionService.findById(questionId);
        List<CommentDTO> comments = commentService.findByQuestionId(questionId);
            model.addAttribute("question",questionDTO);
            model.addAttribute("comments",comments);
//        System.out.println(comments);
        return "question";
    }
}
