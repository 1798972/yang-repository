package com.example.community.service;

import com.example.community.dto.PageInfoDTO;
import com.example.community.mapper.QuestionMapper;
import com.example.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Yiang37
 * @Description:
 * @Date: Create in 10:30 2019/11/25
 */

@Service
public class PageDTOService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;

    public PageInfoDTO setAllPageInfo(Integer totalCount, Integer page, Integer size) {
        PageInfoDTO pageInfoDTO = new PageInfoDTO();
        //pages存储当前页面的页码
        List<Integer> pages = new ArrayList<Integer>();


        //1.计算总页数
        int flag = totalCount/size;  //flag = 0/5
        //若刚好整除 则总页数不变
        if (totalCount%size == 0){
            pageInfoDTO.setTotalPage(flag);
        }else {
            pageInfoDTO.setTotalPage(flag+1);
        }
//        System.out.println(pageInfoDTO.getTotalPage());
        //2.设置当前页面
        pageInfoDTO.setNowPage(page);

        //3.添加当前页的页码
        pages.add(page);

        //4.设置前面显示3页 后面显示3页
            //以当前第五页为例 234 5 678
            //page是当前页面
        for (int i = 1; i <= 3; i++) {
            //头部加不加
            if (page - i > 0){
                pages.add(0,page-i);
            }
            //尾部加不加
            if (page + i <= pageInfoDTO.getTotalPage()){
                pages.add(page+i);
            }
        }

        //5.添加所有页的页码
        pageInfoDTO.setHasPages(pages);

        if(totalCount == 0){
//            pageInfoDTO.setShowFirstPage(false);
//            pageInfoDTO.setShowEndpage(false);
//            pageInfoDTO.setShowNextPage(false);
//            pageInfoDTO.setShowPrePage(false);
            pageInfoDTO.setQuestions(null);
        }else {
            //6.是否显示前一页
            //默认值是false 不是第一页的话就要显示呗
            if(page != 1){
                pageInfoDTO.setShowPrePage(true);
            }
            //是否显示最后一页
            if (page != pageInfoDTO.getTotalPage()){
                pageInfoDTO.setShowNextPage(true);
            }

            //7.是否显示第一页
            if (pages.contains(1) != true){  //不包含第一页就显示呗
                pageInfoDTO.setShowFirstPage(true);
            }
            //是否显示最后一页
            if (pages.contains(pageInfoDTO.getTotalPage()) != true){
                pageInfoDTO.setShowEndpage(true);
            }
        }

        return pageInfoDTO;
    }
}
