package com.example.community.advice;

import com.example.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Yiang37
 * @Description:
 * @Date: Create in 21:42 2019/12/8
 */
@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request, Throwable e, Model model){

        if (e instanceof CustomizeException){
            //e:  CustomizeException extends RuntimeException
            //System.out.println(e.toString());
            //com.example.community.exception.CustomizeException
            System.out.println(e.getMessage());
            model.addAttribute("message",e.getMessage());
        }else {
            model.addAttribute("message","服务器出错了,稍后再试试吧...");
        }

        return new ModelAndView("error");
    }
}
