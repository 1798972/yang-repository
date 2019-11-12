package com.example.community.service;

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

    public List<QuestionDTO> getQuestionDTOList() {
        //查出所有的question
        List<Question> questionList =  questionMapper.getQuestionList();
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
        return questionDTOList;
    }
}
