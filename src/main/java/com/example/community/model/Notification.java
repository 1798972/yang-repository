package com.example.community.model;

import lombok.Data;

/**
 * @Author Yiang37
 * @Date 2020/2/11 19:21
 * Description:
 */
@Data
public class Notification {
    private Long id;
    private Long notifier;
    private Long receiver;
    private Long outerId;
    private Integer type;
    private Long gmtCreate;
    private Integer status;
}