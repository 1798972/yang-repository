package com.example.community.mapper;

import com.example.community.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: Yiang37
 * @Description:
 * @Date: Create in 22:27 2019/12/9
 */
@Mapper
public interface CommentMapper {
    @Insert("insert into comment (id,parent_id,type,commentator,content,like_count,gmt_create,gmt_modified) values (#{id},#{parentId},#{type},#{commentator},#{content},#{likeCount},#{gmtCreate},#{gmtModified})")
    void insert(Comment comment);

    @Select("select * from comment where id = #{id}")
    Comment selectById(Long id);

    @Select("select * from comment where parent_id = #{parentId}")
    List<Comment> findByQuestionId(@Param("parentId") Long questionId);
}
