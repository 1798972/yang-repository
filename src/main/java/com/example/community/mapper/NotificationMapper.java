package com.example.community.mapper;

import com.example.community.model.Notification;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author Yiang37
 * @Date 2020/2/11 19:12
 * Description:
 */

@Mapper
public interface NotificationMapper {
    @Insert("")
    void insert(Notification notification);
}