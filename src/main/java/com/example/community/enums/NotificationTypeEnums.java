package com.example.community.enums;

/**
 * @Author Yiang37
 * @Date 2020/2/11 19:15
 * Description: 通知类型的枚举
 */

//枚举的使用
public enum NotificationTypeEnums {
    REPLAY_QUESTION(1,"问题"),
    REPLSY_COMMENT(2,"评论");

    private int type;
    private String name;

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    NotificationTypeEnums(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public static String nameOfType(int type){
        for (NotificationTypeEnums notificationTypeEnum : NotificationTypeEnums.values()) {
            if (notificationTypeEnum.getType() == type){
                return notificationTypeEnum.getName();
            }
        }
        return "";
    }
}