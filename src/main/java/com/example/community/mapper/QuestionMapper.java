package com.example.community.mapper;

import com.example.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into questions (title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void createQuestion(Question question);

    //方法中@Param("列名") 总要指定  方法哪个形参--->数据库哪个列  吧
    @Select("select * from questions limit #{offset},#{size}")
    List<Question> getQuestionList(@Param("offset") Integer offset, @Param("size") Integer size);

    //查询总条数
    @Select("select count(1) from questions")
    Integer selectQuestionCounts();
}
