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

import javax.servlet.http.HttpServletRequest;
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
                           HttpServletRequest request){

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
            user.setToken(UUID.randomUUID().toString());
            //Long-->String
            user.setAccountId(String.valueOf(gitHubUser.getId()));
//            user.setAccountId(gitHubUser.getId()+""); //性能差点
            user.setName(gitHubUser.getName());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
            //登录成功
            System.out.println("得到user对象:"+gitHubUser);
            request.getSession().setAttribute("gitHubUser",gitHubUser);
            return "redirect:/";
        }else {
            return "redirect:/";
        }
    }
}
