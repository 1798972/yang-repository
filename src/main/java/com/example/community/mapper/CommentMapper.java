package com.example.community.mapper;

import com.example.community.model.Comment;
import org.apache.ibatis.annotations.*;

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

    //根据问题id查找评论 按时间倒序查询
    @Select("select * from comment where parent_id = #{parentId} order by gmt_modified desc")
    List<Comment> findByQuestionId(@Param("parentId") Long questionId);

    //根据评论id查找二级评论
    @Select("select * from comment where parent_id = #{parentId} and type = 2 order by gmt_modified desc")
    List<Comment> findByCommentId(@Param("parentId") Long commentId);

    //更新子评论数
    @Update("update comment set comment_count = comment_count + 1 where id = #{id}")
    void increase2CommentCounnt(@Param("id") Long parentId);

    //找到父级id
    @Select("select parent_id from comment where id = #{id}")
    Long findParentId(Long parentId);

    //找到父级评论的内容
    @Select("select content from comment where id = #{id}")
    String findParentCommentContent(Long parentId);
}
