package com.example.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Yiang37
 * @Description:
 * @Date: Create in 19:15 2019/11/13
 */
@Data
public class PageInfoDTO {
    //PageInfoDTO对象包含页面所承载的元素
    //包括问题列表 页码相关信息等
    private List<QuestionDTO> questions;
    private boolean showFirstPage;  //默认值false
    private boolean showPrePage;
    private boolean showNextPage;
    private boolean showEndpage;

    private Integer totalPage;
    private Integer nowPage;
    private List<Integer> hasPages = new ArrayList<Integer>();  //当前页面包含哪几页

    public void setPageInfo(Integer totalCount, Integer page, Integer size) {
        int flag = totalCount/size;

        //计算总页数
        //若刚好整除 则总页数不变
        if (totalCount%size == 0){
            totalPage = flag;
        }else {
            totalPage = flag +1 ;
        }

        this.nowPage = page;

        //显示哪几页
        hasPages.add(page);
        for (int i = 1; i <= 3; i++) {
            //头部加不加
            if (page - i > 0){
                hasPages.add(0,page-i);
            }
            //尾部加不加
            if (page + i <= totalPage){
                hasPages.add(page+i);
            }
        }

        //是否显示前一页
        //默认值是false 不是第一页的话就要显示呗
        if(page != 1){
            showPrePage = true;
        }
        //是否显示最后一页
        if (page != totalPage){
            showNextPage = true;
        }

        //是否显示第一页
        if (hasPages.contains(1) != true){
            showFirstPage = true;
        }
        //是否显示最后一页
        if (hasPages.contains(totalPage) != true){
            showEndpage = true;
        }
    }
}
