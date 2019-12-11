package com.example.community.enums;

/**
 * @Author: Yiang37
 * @Description:
 * @Date: Create in 21:18 2019/12/11
 */
public enum CommentTypeEnums {
    QUESTION(1),
    COMMENT(2);

    private Integer type;

    public Integer getType() {
        return type;
    }

    CommentTypeEnums(Integer type){
        this.type = type;
    }
}
