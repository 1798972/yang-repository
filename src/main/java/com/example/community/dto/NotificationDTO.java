package com.example.community.dto;

import com.example.community.model.User;
import lombok.Data;

/**
 * @Author Yiang37
 * @Date 2020/2/17 14:45
 * Description:在回复页面的数据模型 xx回复了你的评论/回复xx
 */
@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private Long notifier;
    private String notifierName;
    private String outerTitle;
    private Long outerId;
    private String typeName;
    private Integer type;
}