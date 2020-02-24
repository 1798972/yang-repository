package com.example.community.service;

import com.example.community.dto.CommentDTO;
import com.example.community.enums.CommentTypeEnums;
import com.example.community.enums.NotificationStatusEnums;
import com.example.community.enums.NotificationTypeEnums;
import com.example.community.exception.CustomizeErrorCode;
import com.example.community.exception.CustomizeException;
import com.example.community.mapper.CommentMapper;
import com.example.community.mapper.NotificationMapper;
import com.example.community.mapper.QuestionMapper;
import com.example.community.mapper.UserMapper;
import com.example.community.model.Comment;
import com.example.community.model.Notification;
import com.example.community.model.Question;
import com.example.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: Yiang37
 * @Description:
 * @Date: Create in 22:27 2019/12/9
 */
@Service
public class CommentService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NotificationMapper notificationMapper;

    @Transactional
    public void insert(Comment comment, User commentUser) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {

            //CustomizeExceptionHandler中做处理
            throw new CustomizeException(CustomizeErrorCode.TARGET_PATH_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentTypeEnums.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        //回复的是评论
        if (comment.getType() == CommentTypeEnums.COMMENT.getType()) {
            //父级评论
            Comment dbComment = commentMapper.selectById(comment.getParentId());
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);

            //增加评论数 前面插入的是子评论
            //增加的评论数 是添加在它的父评论上
            Comment parentComment = new Comment();
            parentComment.setId(comment.getParentId());
            parentComment.setCommentCount(1);
            commentMapper.increase2CommentCounnt(parentComment.getId());

            Long parentId = commentMapper.findParentId(comment.getParentId());
            //如果是评论 在通知时要查找的问题:评论的父级评论对应的问题id
            Question targetQuestion = questionMapper.findById(parentId);
            if (targetQuestion == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }

            //查找父级评论的内容
            String outerTitle = commentMapper.findParentCommentContent(comment.getParentId());

            //评论时创建通知
            createNotify(comment, dbComment.getCommentator(), commentUser.getName(), outerTitle, NotificationTypeEnums.REPLSY_COMMENT, targetQuestion.getId());

        } else {
        //回复的是问题
            //父级问题
            Question question = questionMapper.findById(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            //评论数加1
            //question.setCommentCount(1);
            questionMapper.increaseCommentCounnt(comment.getParentId());

            //回复问题时创建通知
            createNotify(comment,question.getCreator(), commentUser.getName(), question.getTitle(), NotificationTypeEnums.REPLAY_QUESTION, question.getId());
        }
    }

    private void createNotify(Comment comment, Long receiver, String notifierName, String outerTitle, NotificationTypeEnums notificationType, Long outerId) {
        //对通知的操作
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(notificationType.getType());
        //父评论的id
        notification.setOuterId(outerId);
        //评论人
        notification.setNotifier(comment.getCommentator());
        notification.setStatus(NotificationStatusEnums.UNREAD.getStatus());
        //接收者
        notification.setReceiver(receiver);
        notification.setNotifierName(notifierName);
        notification.setOuterTitle(outerTitle);
        notificationMapper.insert(notification);
    }

    //根据问题id找到所有评论
    public List<CommentDTO> findByQuestionId(Long questionId) {
        List<Comment> comments = commentMapper.findByQuestionId(questionId);
        if (comments.size() == 0) {
            return new ArrayList<>();
        }

        //获取去重复的评论人
        //eg;十条评论 有五条是同一个用户 为了避免重复 所以用不着拿出十个用户
        //Set的使用 lambda表达式的使用
        Set<Long> commentator = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());

        //所有的userId
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(commentator);

        //根据userId集合查出所有的user
        List<User> users = new ArrayList<>();
        User tempUser;
        for (Long userId : userIds) {
            tempUser = userMapper.findById(userId);
            users.add(tempUser);
        }

        //遍历Questions下的所有comments
        //如果comment的userId跟user的userId相等 则说明这个comment是这个user评论的
        //时间复杂度n平方  不合适
//        for (Comment comment : comments) {
//            for (User user : users) {
//            }
//        }

        //得到userId以及user的Map
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        //转换comment为commentDTO 即加上user属性
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOS;
    }

    //根据评论id找到所有二级评论
    public List<CommentDTO> findByCommentId(Long commentId) {
        List<Comment> comments = commentMapper.findByCommentId(commentId);
        if (comments.size() == 0) {
            return new ArrayList<>();
        }

        //获取去重复的评论人
        //eg;十条评论 有五条是同一个用户 为了避免重复 所以用不着拿出十个用户
        //Set的使用 lambda表达式的使用
        Set<Long> commentator = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());

        //所有的userId
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(commentator);

        //根据userId集合查出所有的user
        List<User> users = new ArrayList<>();
        User tempUser;
        for (Long userId : userIds) {
            tempUser = userMapper.findById(userId);
            users.add(tempUser);
        }

        //遍历Questions下的所有comments
        //如果comment的userId跟user的userId相等 则说明这个comment是这个user评论的
        //时间复杂度n平方  不合适
//        for (Comment comment : comments) {
//            for (User user : users) {
//            }
//        }

        //得到userId以及user的Map
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        //转换comment为commentDTO 即加上user属性
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOS;
    }
}
