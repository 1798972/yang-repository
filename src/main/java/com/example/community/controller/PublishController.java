package com.example.community.controller;

import com.example.community.mapper.QuestionMapper;
import com.example.community.mapper.UserMapper;
import com.example.community.model.Question;
import com.example.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    UserMapper userMapper;

    @GetMapping("/publish")  //get方式发送的publish请求
    public String publish(){
        return "publish";
    }


    // @RequestParam中的value对应的标签的name
    // 用于将请求中的参数 绑定到方法的形参上
    // 单变量时可以简写 eg:@RequestParam("str")String yang 就是将请求中name为str的参数映射到形参yang上面
    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title")String title,
            @RequestParam("description")String des,  //将控件name为description的参数映射到方法的形参上
            @RequestParam(value = "tag")String tag,
            HttpServletRequest request,
            Model model
    ){
        //model中添加三个键  这样才能回显啊
        model.addAttribute("title",title);
        model.addAttribute("description",des);
        model.addAttribute("tag",tag);

        System.out.println("文章信息:"+title+"--"+des+"--"+tag);

        if(title == null || title.equals("")){
            model.addAttribute("error","标题不能为空！ ");
            return "publish";
        }

        if(des == null || des.equals("")){
             model.addAttribute("error","问题描述不能为空！");
            return "publish";
        }
        if(tag == null || tag.equals("")){
             model.addAttribute("error","标签不能为空！");
            return "publish";
        }
        User user = null;
        //得到user
        Cookie[] cookies = request.getCookies();
        if (cookies!=null){
            for (Cookie cookie : cookies) {
                if("token".equals(cookie.getName())){
                    String token = cookie.getValue();
                    user = userMapper.findByToken(token);
                    if (user != null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }
        if(user == null){
            model.addAttribute("error","用户未登录！");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(des);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        questionMapper.createQuestion(question);
        return "redirect:/";
    }
}
