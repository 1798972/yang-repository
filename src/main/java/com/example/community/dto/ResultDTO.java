package com.example.community.dto;

import com.example.community.exception.CustomizeErrorCode;
import com.example.community.exception.CustomizeException;
import lombok.Data;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @Author: Yiang37
 * @Description:
 * @Date: Create in 16:01 2019/12/10
 */
@Data
public class ResultDTO<T> {
    private Integer code;
    private String message;
    private T data;

    public static ResultDTO errorOf(Integer code, String message) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizeErrorCode notLogin) {
        return errorOf(notLogin.getCode(), notLogin.getMessage());
    }

    public static ResultDTO errorOf(CustomizeException e) {
        return ResultDTO.errorOf(e.getCode(), e.getMessage());
    }

    //请求成功
    public static ResultDTO okOf() {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功！");
        return resultDTO;
    }

    //请求成功
    public static <T> ResultDTO okOf(T t) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功！");
        resultDTO.setData(t);
        return resultDTO;
    }
}
