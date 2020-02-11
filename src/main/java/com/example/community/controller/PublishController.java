package com.example.community.controller;

import com.example.community.dto.QuestionDTO;
import com.example.community.model.Question;
import com.example.community.model.User;
import com.example.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/publish")  //get方式发送的publish请求
    public String publish(HttpServletRequest request,
                          Model model) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录！请先登录！");
            return "publish";
        }
        return "publish";
    }


    //<input type="hidden" name="questionId" th:value="${questionId}">
    // @RequestParam中的value值对应的标签的name
    // 单变量时可以简写 eg:@RequestParam("str")String yang 就是将请求中name为str的参数映射到形参yang上面
    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String des,  //将控件name为description的参数映射到方法的形参上
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "questionId", required = false) Long questionId,
            HttpServletRequest request,
            Model model
    ) {

        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            model.addAttribute("error", "用户未登录！不能发布问题！");
            return "publish";
        }

        //model中添加三个键  这样才能回显啊
        model.addAttribute("title", title);
        model.addAttribute("description", des);
        model.addAttribute("tag", tag);

        if (title == null || title.equals("")) {
            model.addAttribute("error", "标题不能为空！ ");
            return "publish";
        }

        if (des == null || des.equals("")) {
            model.addAttribute("error", "问题描述不能为空！");
            return "publish";
        }
        if (tag == null || tag.equals("")) {
            model.addAttribute("error", "标签不能为空！");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(des);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        question.setId(questionId);
        questionService.createOrUpdateQuestion(question);
//        questionMapper.createQuestion(question);
        return "redirect:/";
    }

    //重新编辑问题信息
    @GetMapping("/publish/{questionId}")
    public String edit(@PathVariable("questionId") Long questionId,
                       Model model) {

        QuestionDTO questionDTO = questionService.findById(questionId);
        //model中添加三个键  这样才能回显啊
        model.addAttribute("title", questionDTO.getTitle());
        model.addAttribute("description", questionDTO.getDescription());
        model.addAttribute("tag", questionDTO.getTag());

        //添加一个id用于验证是否已经存question
        model.addAttribute("questionId", questionDTO.getId());
        return "publish";
    }
}
