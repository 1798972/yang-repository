package com.example.community.service;

import com.example.community.dto.PageInfoDTO;
import com.example.community.dto.QuestionDTO;
import com.example.community.mapper.QuestionMapper;
import com.example.community.mapper.UserMapper;
import com.example.community.model.Question;
import com.example.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;

    //得到问题每页的问题列表
    public PageInfoDTO getQuestionDTOList(Integer page, Integer size) {
        PageInfoDTO pageInfoDTO = new PageInfoDTO();
        Integer totalCount = questionMapper.selectQuestionCounts();
        pageInfoDTO.setPageInfo(totalCount,page,size);
        if(page < 1 ){
            page = 1;
        }
        if(page > pageInfoDTO.getTotalPage()){
            page =  pageInfoDTO.getTotalPage();
        }

        //当前页的开始索引 比如第二页就是5*1 从数据库的第五条数据开始
        //select * from questions limit 0,5; //0是起始位置 5是偏移量 这里的0要换成offset呗
        Integer offset = size*(page-1);
        //查出所有的question
        List<Question> questionList =  questionMapper.getQuestionList(offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<QuestionDTO>();

        //遍历questionList
        for (Question question : questionList) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();  //断点打在这 可以发现user中驼峰命名的属性都是null 
            BeanUtils.copyProperties(question,questionDTO);
            //为每一个question添加上user对象这个属性
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageInfoDTO.setQuestions(questionDTOList);

        return pageInfoDTO;
    }
}
