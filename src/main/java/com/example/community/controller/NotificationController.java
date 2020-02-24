package com.example.community.controller;

import com.example.community.dto.NotificationDTO;
import com.example.community.dto.PageInfoDTO;
import com.example.community.enums.NotificationTypeEnums;
import com.example.community.mapper.NotificationMapper;
import com.example.community.model.User;
import com.example.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Yiang37
 * @Date 2020/2/24 17:41
 * Description:
 */
@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")  //动态指定  确定跳转到哪个标签页面
    public String toOneNotification(HttpServletRequest request,
                                    Model model,
                                    @PathVariable(name = "id") Long id) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }

        NotificationDTO notificationDTO = notificationService.readOneNotification(id, user);
        if (NotificationTypeEnums.REPLSY_COMMENT.getType() == notificationDTO.getType() || NotificationTypeEnums.REPLAY_QUESTION.getType() == notificationDTO.getType()) {
            return "redirect:/question/" + notificationDTO.getOuterId();
        } else {
            return "redirect:/";
        }
    }
}