package com.example.community.enums;

public enum NotificationStatusEnums {
    //未读0 已读1
    UNREAD(0),READ(1);

    private int status;

    public int getStatus() {
        return status;
    }

    NotificationStatusEnums(int status) {
        this.status = status;
    }
}
