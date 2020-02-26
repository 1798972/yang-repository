package com.example.community.controller;

import com.example.community.dto.PageInfoDTO;
import com.example.community.mapper.UserMapper;
import com.example.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size,
                        @RequestParam(name = "search", required = false) String search
                        ) {
        //获取问题列表
        PageInfoDTO pageInfo = questionService.getQuestionDTOList(page, size, search);
        model.addAttribute("search",search);
        model.addAttribute("pageInfo", pageInfo);

        return "index";
    }
}
