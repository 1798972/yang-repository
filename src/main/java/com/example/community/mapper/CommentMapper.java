package com.example.community.mapper;

import com.example.community.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: Yiang37
 * @Description:
 * @Date: Create in 22:27 2019/12/9
 */
@Mapper
public interface CommentMapper {
    @Insert("insert into comment (id,parent_id,type,commentator,content,like_count,gmt_create,gmt_modified) values (#{id},#{parentId},#{type},#{commentator},#{content},#{likeCount},#{gmtCreate},#{gmtModified})")
    void insert(Comment comment);
}
