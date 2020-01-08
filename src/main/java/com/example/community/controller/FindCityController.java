package com.example.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: Yiang37
 * @Description:
 * @Date: Create in 23:44 2020/1/8
 */

@Controller
public class FindCityController {
    @GetMapping("/csid")
    public String findCity(){
        return "csid";
    }
}
