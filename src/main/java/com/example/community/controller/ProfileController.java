package com.example.community.controller;

import com.example.community.dto.PageInfoDTO;
import com.example.community.mapper.QuestionMapper;
import com.example.community.mapper.UserMapper;
import com.example.community.model.Question;
import com.example.community.model.User;
import com.example.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Yiang37
 * @Description:
 * @Date: Create in 21:09 2019/11/24
 */
@Controller
public class ProfileController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionService questionService;

    //注意  @PathVariable  eg：/profile/{id}
    //      @RequestParam  eg：/profile?id=20
    //区别就在于那个是不是显示的在写 = 号


    @GetMapping("/profile/{action}")  //动态指定action 确定跳转到哪个标签页面
    public String profile(HttpServletRequest request,
                          Model model,
                          @PathVariable(name = "action")String action,
                          @RequestParam(name = "page",defaultValue = "1")Integer page,
                          @RequestParam(name = "size",defaultValue = "5")Integer size
                          ){

        User user = (User)request.getSession().getAttribute("user");
        if (user == null){
            //请求转发 这个时候交给IndexController处理 没有数据会报错
//            return "index";
            //直接重定向
            return "redirect:/";
        }

        //处理标签的跳转
        if ("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的问题");
        }else if("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
        }

        PageInfoDTO userPageinfo = questionService.myQuestionList(user.getId(),page,size);
        model.addAttribute("userPageinfo",userPageinfo);
        return "profile";
    }

//    @GetMapping("/logout")
    @RequestMapping(method = RequestMethod.GET,value = "/logout")
    public String logOut(HttpServletRequest request,
                         HttpServletResponse response){
        //1.移除session
        request.getSession().removeAttribute("user");
        //2.移除cookie 替换掉token即可
        Cookie out = new Cookie("token",null);
        out.setMaxAge(0);
        response.addCookie(out);
        return "redirect:/";
    }
}
