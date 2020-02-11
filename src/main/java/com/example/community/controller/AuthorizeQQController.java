package com.example.community.controller;

import com.example.community.dto.AccessTokenDTO;
import com.example.community.dto.QQUser;
import com.example.community.model.User;
import com.example.community.provider.QQProvider;
import com.example.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @Author: Yiang37
 * @Description:
 * @Date: Create in 12:51 2019/12/25
 */

@Controller
public class AuthorizeQQController {

    @Autowired
    private QQProvider qqProvider;
    @Autowired
    private UserService userService;


    //application.properties文件中的属性值进行注入
    @Value("${qq.redirect.uri}")
    private String redirectUri;
    @Value("${qq.client.id}")
    private String clientId;
    @Value("${qq.client.secret}")
    private String clientSecret;
//    @Value("${qq.authorization.code}")
//    private String authorizationCode;

    //回传http://www.yang37.cn/qqcallback?code=DA90B4981C73FFE7395998F0FE775BFC&state=1
    @RequestMapping("/qqcallback")
    public String qqLogin(@RequestParam("code") String code,
                          @RequestParam(name = "state") String state,
                          HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);

        //得到token
        String accessToken = qqProvider.getQQAccessToken(accessTokenDTO);
        //根据token获取openID
        String openId = qqProvider.getOpenId(accessToken);
        //根据openID得到QQ用户
        QQUser qqUser = qqProvider.getQQUser(openId, accessToken, accessTokenDTO);

        if (qqUser != null) {
            //qq用户存在 为网站用户设置信息
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setAccountId(openId);
            user.setName(qqUser.getNickname());
            user.setAvatarUrl(qqUser.getFigureurl2());
            //得到用户 判断用户是否存在
            userService.insertOrUpdate(user);
            response.addCookie(new Cookie("token", token));
            return "redirect:/";
        } else {
            System.out.println("登录失败！！");
            return "redirect:/";
        }
    }
}