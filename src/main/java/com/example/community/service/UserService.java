package com.example.community.service;

import com.example.community.mapper.UserMapper;
import com.example.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public void insertOrUpdate(User user) {
        //根据AccountId查找到的数据库中的dbUser
        User dbUser = userMapper.finfByAccountId(user.getAccountId());
        if(dbUser == null){
        //1.插入
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else {
        //2.更新
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setName(user.getName());
            dbUser.setToken(user.getToken());
//            System.out.println(user);
            userMapper.update(user);
        }
    }
}
