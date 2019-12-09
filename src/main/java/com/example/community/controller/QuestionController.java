package com.example.community.controller;

import com.example.community.dto.QuestionDTO;
import com.example.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: Yiang37
 * @Description:
 * @Date: Create in 10:49 2019/11/28
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{questionId}")
    String questionDetail(@PathVariable("questionId")Integer questionId,
                         Model model){
        //1.得到文问题id problem_id
        //2.根据问题id查找得到问题数据
            //在查看问题之前 让阅读数加1
        questionService.increaseViewCount(questionId);
        QuestionDTO questionDTO = questionService.findById(questionId);
            model.addAttribute("question",questionDTO);

        return "question";
    }
}
