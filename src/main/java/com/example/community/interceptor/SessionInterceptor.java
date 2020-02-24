package com.example.community.interceptor;

import com.example.community.mapper.UserMapper;
import com.example.community.model.User;
import com.example.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Yiang37
 * @Description: 拦截器的配置
 * @Date: Create in 22:15 2019/11/25
 */

@Service
public class SessionInterceptor implements HandlerInterceptor {
    @Autowired
    UserMapper userMapper;
    @Autowired
    NotificationService notificationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //用户的session处理
        //1.获取cookies
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                //2.遍历cookies 找到名为token的cookie
                if ("token".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    //3.根据token 找到user
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        //4.如果user存在 就在session中存入user
                            request.getSession().setAttribute("user", user);
                            //在session中存入通知信息
                            Long unreadMessage = notificationService.unReadCount(user.getId());
                            request.getSession().setAttribute("unreadMessage",unreadMessage);
                    }
                    break;
                }
            }
        }
        //true 处理完继续执行
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}

