package com.example.community.controller;

import com.example.community.dto.QuestionDTO;
import com.example.community.mapper.UserMapper;
import com.example.community.model.Question;
import com.example.community.model.User;
import com.example.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;
    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model){

        //登录首页时判断有无用户cookie
        Cookie[] cookies = request.getCookies();
        if (cookies!=null){
            for (Cookie cookie : cookies) {
                if("token".equals(cookie.getName())){
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }
        //因为普通的qust里面只有creater 这个属性对应的只是一个user的id
        //列表中要显示头像 就应该有一个question中包含着一个user对象
        //所以就有了dto中的questioDTO  返回一个QuestionDTOList
        List<QuestionDTO> questionDTOList = questionService.getQuestionDTOList();
        for (QuestionDTO questionDTO : questionDTOList) {
            questionDTO.setDescription("123456");
        }
        model.addAttribute("questionDtoList",questionDTOList);

        return "index";
    }
}
