package com.example.community.controller;

import com.example.community.dto.AccessTokenDTO;
import com.example.community.dto.GitHubUser;
import com.example.community.mapper.UserMapper;
import com.example.community.model.User;
import com.example.community.provider.GitHubProvider;
import com.example.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


//解析github用户
@Controller
public class AuthorizeController {

    //类GitHubProvider先添加@Component注解
    @Autowired
    private GitHubProvider gitHubProvider;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;

    //application.properties文件中的属性值进行注入
    @Value("${github.redirect.uri}")
    private String redirectUri;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;

    //点击登录链接之后会回传一个地址
    //      https://localhost:8089/callback?code=ec043f38116846104634&state=1
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response){


        //对回传的地址进行处理
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();

        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        //得到用户的token
        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);
//        System.out.println("用户token："+accessToken);
        //通过token得到用户
        GitHubUser gitHubUser = gitHubProvider.getUser(accessToken);
//        System.out.println("github用户:"+gitHubUser);
        if(gitHubUser!=null){
            User user = new User();
            //token用于识别用户身份
            //1.数据库查询到accountId 如果登录的用户accountId已经存在 说明已经登录过了
                //更新他的token
            //2.否则是第一次登录 做插入操作
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            //登录github后获取的用户的accountId是唯一的
            user.setAccountId(String.valueOf(gitHubUser.getId()));
            user.setName(gitHubUser.getName());
            user.setAvatarUrl(gitHubUser.getAvatarUrl());
            //得到用户 判断用户是否存在
            userService.insertOrUpdate(user);
            response.addCookie(new Cookie("token",token));

            return "redirect:/";
        }else {
            System.out.println("登录失败！！");
            return "redirect:/";
        }
    }
}
