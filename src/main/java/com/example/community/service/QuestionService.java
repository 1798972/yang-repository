package com.example.community.service;

import com.example.community.dto.PageInfoDTO;
import com.example.community.dto.QuestionDTO;
import com.example.community.exception.CustomizeErrorCode;
import com.example.community.exception.CustomizeException;
import com.example.community.mapper.QuestionMapper;
import com.example.community.mapper.UserMapper;
import com.example.community.model.Question;
import com.example.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private PageDTOService pageDTOService;

    //1.每页的问题列表
    public PageInfoDTO getQuestionDTOList(Integer page, Integer size) {
        PageInfoDTO pageInfoDTO = new PageInfoDTO();


        //.查询总页数
        Integer totalCount = questionMapper.selectQuestionCounts();
        //.设置页面信息
        pageInfoDTO = pageDTOService.setAllPageInfo(totalCount, page, size);

        //页面范围在[1,总页数]
        if (page < 1) {
            page = 1;
        }
        if (page > pageInfoDTO.getTotalPage()) {
            page = pageInfoDTO.getTotalPage();
        }

        //当前页的开始索引 比如第二页就是5*1 从数据库的第五条数据开始
        //select * from questions limit 0,5; //0是起始位置 5是偏移量 这里的0要换成offset呗
        //注意页数为0时的判断
        Integer offset = page < 1 ? 0 : size * (page - 1);

        //查出所有的question
        List<Question> questionList = questionMapper.getQuestionList(offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<QuestionDTO>();

        //遍历questionList
        for (Question question : questionList) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();  //断点打在这 可以发现user中驼峰命名的属性都是null
            BeanUtils.copyProperties(question, questionDTO);
            //为每一个question添加上user对象这个属性
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageInfoDTO.setData(questionDTOList);

        return pageInfoDTO;
    }


    //2某用户的问题列表
    public PageInfoDTO myQuestionList(Long id, Integer page, Integer size) {
        PageInfoDTO userPageInfoDTO = new PageInfoDTO();
        //总条数
        Integer totalCount = questionMapper.selectMyQuestionCounts(id);
        //
        userPageInfoDTO = pageDTOService.setAllPageInfo(totalCount, page, size);
        //
        if (page < 1) {
            page = 1;
        }
        if (page > userPageInfoDTO.getTotalPage()) {
            if (userPageInfoDTO.getTotalPage() != 0) {
                page = userPageInfoDTO.getTotalPage();
            } else {
                page = 1;
            }
        }

        Integer offset = size * (page - 1);
        List<Question> myQuestionList = questionMapper.myQuestionList(id, offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<QuestionDTO>();

        for (Question question : myQuestionList) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();  //断点打在这 可以发现user中驼峰命名的属性都是null
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        userPageInfoDTO.setData(questionDTOList);

        return userPageInfoDTO;
    }

    //3某一个问题的id
    public QuestionDTO findById(Long questionId) {
        Question question = questionMapper.findById(questionId);
        if (question == null) {
            //希望传入的是一个CustomizeException实例
            //传递的时候给了CustomizeErrorCode.QUESTION_NOT_FOUND这个枚举值
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        //把question复制到questionDTO里面
        BeanUtils.copyProperties(question, questionDTO);
        User user = userMapper.findById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    //新建或者更新文章
    public void createOrUpdateQuestion(Question question) {
        if (question.getId() == null) {
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCount(0);
            question.setLikeCount(0);
            question.setCommentCount(0);
            questionMapper.createQuestion(question);
        } else {
            question.setGmtModified(System.currentTimeMillis());
            int update = questionMapper.updateQuestion(question);
            if (update != 1) {
                //没有更新
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    //增加阅读数
    public void increaseViewCount(Long questionId) {
        questionMapper.increaseViewCount(questionId);
    }

    //根据id删除某个问题
    public void deleteByQuestionId(Long questionId) {
        //先查找有没有这个问题
        if ((questionMapper.findById(questionId)) == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        } else {
            //否则问题存在
            //执行删除操作
            questionMapper.deleteByQuestionId(questionId);
        }
    }

    //根据标签查询相关问题
    public List<QuestionDTO> findAboutQuestions(Long questionId) {
        //根据问题id找到所有的标签
        Question targetQuestion = questionMapper.findById(questionId);
        String tags = targetQuestion.getTag();
        String regTag = tags.replace(',','|');
        //根据标签查找到相关的问题
        List<Question> aboutQuestions = questionMapper.findAboutQuestions(targetQuestion.getId(),regTag);

        //添加上对应的user 把aboutQuestions变成DTO形式
        List<QuestionDTO> aboutQuestionsDTO = aboutQuestions.stream().map(q -> {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q,questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());
        return aboutQuestionsDTO;
    }
}
