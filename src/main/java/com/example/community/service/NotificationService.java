package com.example.community.service;

import com.example.community.dto.NotificationDTO;
import com.example.community.dto.PageInfoDTO;
import com.example.community.dto.QuestionDTO;
import com.example.community.enums.NotificationStatusEnums;
import com.example.community.enums.NotificationTypeEnums;
import com.example.community.exception.CustomizeErrorCode;
import com.example.community.exception.CustomizeException;
import com.example.community.mapper.NotificationMapper;
import com.example.community.mapper.UserMapper;
import com.example.community.model.Notification;
import com.example.community.model.Question;
import com.example.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author Yiang37
 * @Date 2020/2/17 14:44
 * Description:
 */
@Service
public class NotificationService {

    @Autowired
    NotificationMapper notificationMapper;
    @Autowired
    PageDTOService pageDTOService;
    @Autowired
    UserMapper userMapper;

    //查出所有的通知 转换成PageInfoDTO
    // PageInfoDTO中有个属性包含NotificationDTO
    // private List<T> data;
    public PageInfoDTO myRepliesList(Long id, Integer page, Integer size) {
        PageInfoDTO<NotificationDTO> notificationPageInfoDTO;
        //查找收到的通知的总条数
        Integer totalCount = notificationMapper.selectMyNotificationCounts(id);
        notificationPageInfoDTO = pageDTOService.setAllPageInfo(totalCount, page, size);
        if (page < 1) {
            page = 1;
        }
        if (page > notificationPageInfoDTO.getTotalPage()) {
            if (notificationPageInfoDTO.getTotalPage() != 0) {
                page = notificationPageInfoDTO.getTotalPage();
            } else {
                page = 1;
            }
        }
        Integer offset = size * (page - 1);

        //查询出Notification的列表
        List<Notification> myNotificationList = notificationMapper.myNotificationList(id, offset, size);
        if (myNotificationList.size() == 0){
            return notificationPageInfoDTO;
        }
        List<NotificationDTO> notificationDTO = new ArrayList<>();
        //转换成NotificationDTO的列表
        for (Notification notification : myNotificationList) {
            NotificationDTO tempNotificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,tempNotificationDTO);
            tempNotificationDTO.setTypeName(NotificationTypeEnums.nameOfType(notification.getType()));
            notificationDTO.add(tempNotificationDTO);
        }


        notificationPageInfoDTO.setData(notificationDTO);
        return notificationPageInfoDTO;
    }

    //查处用户所有未读的消息
    public Long unReadCount(Long userId) {
        Long unreadCount = notificationMapper.selectUnreadCount(userId);
        return unreadCount;
    }

    //阅读某一条通知
    public NotificationDTO readOneNotification(Long id, User user) {
        Notification notification = notificationMapper.selectNotificationById(id);
        if(notification == null){
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if(!notification.getReceiver().equals(user.getId())){
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_ERROR);
        }
        notification.setStatus(NotificationStatusEnums.READ.getStatus());
        notificationMapper.updateReadStatus(notification.getId());
        //读取
        NotificationDTO tempNotificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification,tempNotificationDTO);
        tempNotificationDTO.setTypeName(NotificationTypeEnums.nameOfType(notification.getType()));
        return tempNotificationDTO;
    }
}