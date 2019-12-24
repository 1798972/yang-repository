package com.example.community.controller;

import com.example.community.exception.CustomizeErrorCode;
import com.example.community.exception.CustomizeException;
import com.example.community.mapper.QuestionMapper;
import com.example.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author: Yiang37
 * @Description:
 * @Date: Create in 11:04 2019/12/16
 */
@Controller
public class DeleteQuestionController{

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionService questionService;
    //注意  @PathVariable  eg：/profile/{id}
    //      @RequestParam  eg：/profile?id=20
    @RequestMapping("/deletequestion/{questionId}")
    public String deleteQuestion(@PathVariable("questionId")Long questionId){
        questionService.deleteByQuestionId(questionId);
        return "redirect:/";
    }
}
