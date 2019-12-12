package com.example.community.mapper;

import com.example.community.dto.QuestionDTO;
import com.example.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into questions (title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void createQuestion(Question question);


    //1.查询所有问题
    @Select("select * from questions limit #{offset},#{size}")
    List<Question> getQuestionList(@Param("offset") Integer offset, @Param("size") Integer size);

    //2.查询总条数
    @Select("select count(1) from questions")
    Integer selectQuestionCounts();

    //3.查询某用户的问题
    //方法中@Param("列名") 要指定  方法哪个形参--->数据库哪个列
    //   1.查询语句里  creator = #{creator} 这里是指明creator的值是在#{creator}这一列,model类的属性要跟他对应上。
    //   2.方法的形参中  @Param("creator") int id 说明id映射的是creator这一列
    @Select("select * from questions where creator = #{creator} limit #{offset},#{size}")
    List<Question> myQuestionList(@Param("creator") Long id,@Param("offset") Integer offset,@Param("size") Integer size);

    //4.查询某个用户的问题条数
    @Select("select count(1) from questions where creator = #{creator}")
    Integer selectMyQuestionCounts(@Param("creator") Long id);

    //5.根据某个问题id找到问题信息
    @Select("select * from questions where id = #{id}")
    Question findById(@Param("id") Long questionId);

    //6.更新某个问题
    @Update("update questions set title = #{title},description = #{description},gmt_modified = #{gmtModified},tag = #{tag} where id = #{id}")
    int updateQuestion(Question question);

    //7.更新阅读数
    @Update("update questions set view_count = view_count + 1 where id = #{id}")
    void increaseViewCount(@Param("id") Long id);

    //8.更新评论数
    @Update("update questions set comment_count = comment_count + 1 where id = #{id}")
    void increaseCommentCounnt(@Param("id") Long parentId);
}
