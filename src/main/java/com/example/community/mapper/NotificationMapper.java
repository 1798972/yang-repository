package com.example.community.mapper;

import com.example.community.model.Notification;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author Yiang37
 * @Date 2020/2/11 19:12
 * Description:
 */

@Mapper
public interface NotificationMapper {
    //插入一条通知
    @Insert("insert into notification (notifier,receiver,outerId,type,gmt_create,status,notifier_name,outer_title) values (#{notifier},#{receiver},#{outerId},#{type},#{gmtCreate},#{status},#{notifierName},#{outerTitle})")
    void insert(Notification notification);

    //查询某个用户所有的通知数量
    @Select("select count(1) from notification where receiver = #{receiver}")
    Integer selectMyNotificationCounts(@Param("receiver") Long id);

    //分页查询某个用户的通知
    @Select("select * from notification where receiver = #{receiver} order by id desc limit #{offset},#{size}")
    List<Notification> myNotificationList(@Param("receiver") Long id, @Param("offset") Integer offset, @Param("size") Integer size);

    //根据用户id查询所有未读的消息
    @Select("select count(1) from notification where receiver = #{receiver} and status = 0")
    Long selectUnreadCount(@Param("receiver") Long userId);

    //根据id查找某一条通知
    @Select("select * from notification where id = #{id} ")
    Notification selectNotificationById(Long id);

    //更新某个通知的状态
    @Update("update notification set status = 1 where id = #{id}")
    void updateReadStatus(Long id);
}