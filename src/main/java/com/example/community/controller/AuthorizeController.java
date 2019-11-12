package com.example.community.controller;

import com.example.community.dto.AccessTokenDTO;
import com.example.community.dto.GitHubUser;
import com.example.community.mapper.UserMapper;
import com.example.community.model.User;
import com.example.community.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    //类GitHubProvider先添加@Component注解
    @Autowired
    private GitHubProvider gitHubProvider;
    @Autowired
    private UserMapper userMapper;

    //application.properties文件中的属性值进行注入
    @Value("${github.redirect.uri}")
    private String redirectUri;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response){

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();

        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);

        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);   //得到用户的token
        GitHubUser gitHubUser = gitHubProvider.getUser(accessToken);
        if(gitHubUser!=null){
            User user = new User();
            //登录成功 生成一个token
            String token = UUID.randomUUID().toString();
            //将token等信息存入user
            user.setToken(token);
            //Long-->String
            user.setAccountId(String.valueOf(gitHubUser.getId()));
//            user.setAccountId(gitHubUser.getId()+""); //性能差点
            user.setName(gitHubUser.getName());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setAvatarUrl(gitHubUser.getAvatarUrl());
            //将user存入数据库
            userMapper.insert(user);
            //添加借助token生成一个cookie
            response.addCookie(new Cookie("token",token));
            return "redirect:/";
        }else {
            return "redirect:/";
        }
    }
}
