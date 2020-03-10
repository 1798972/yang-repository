package com.example.community.controller;

import com.example.community.dto.WcjlDTO;
import com.example.community.model.Wcjl;
import com.example.community.model.Xzjl;
import com.example.community.service.XXTJService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Yiang37
 * @Date 2020/3/9 11:13
 * Description:
 * 与项目无关 信息统计网页所使用
 */
@Controller
public class XXTJController {
    @Autowired
    private XXTJService xxtjService;

    @GetMapping("/xxtj")
    public String toXXTJ(){
        return "xxtj";
    }

    @PostMapping("/xxtj/wcjl")
    @ResponseBody
    public String oneXX(@ModelAttribute WcjlDTO wcjlDTO){
        //先用身份证查询以前有没有记录
        boolean sfzflag = xxtjService.findSFZ(wcjlDTO.getSfz());
        if (sfzflag){
            //有记录 生成外出表即可
                //有的话直接生成 外出记录
            Wcjl tempWcjl = new Wcjl();
            tempWcjl.setXm(wcjlDTO.getXm());
            tempWcjl.setXb(wcjlDTO.getXb());
            tempWcjl.setNl(wcjlDTO.getNl());
            tempWcjl.setSfz(wcjlDTO.getSfz());
            tempWcjl.setSb(wcjlDTO.getSb());
            tempWcjl.setDhh(wcjlDTO.getDhh());
            tempWcjl.setWldzAndFxrqAndFxsj(wcjlDTO.getWldz()+wcjlDTO.getFxrq()+wcjlDTO.getFxsj());
            tempWcjl.setWcyy(wcjlDTO.getWcyy());
            tempWcjl.setLwrqAndLwsj(wcjlDTO.getLwrq()+wcjlDTO.getLwsj());
            tempWcjl.setWcdzAndWcrqAndWcsj(wcjlDTO.getWcdz()+wcjlDTO.getWcrq()+wcjlDTO.getWcsj());
            tempWcjl.setFxfs(wcjlDTO.getFxfs());
            tempWcjl.setMfsj(wcjlDTO.getMfsj());

            boolean wcFlag =  xxtjService.insetOrUpdateOneWcjl(tempWcjl);

            Xzjl tempXzjl = new Xzjl();
            tempXzjl.setXm(wcjlDTO.getXm());
            tempXzjl.setXb(wcjlDTO.getXb());
            tempXzjl.setNl(wcjlDTO.getNl());
            tempXzjl.setSfz(wcjlDTO.getSfz());
            tempXzjl.setSb(wcjlDTO.getSb());
            tempXzjl.setDhh(wcjlDTO.getDhh());
            tempXzjl.setWldzAndFxrqAndFxsj(wcjlDTO.getWldz()+wcjlDTO.getFxrq()+wcjlDTO.getFxsj());
            tempXzjl.setWcyy(wcjlDTO.getWcyy());
            tempXzjl.setLwrqAndLwsj(wcjlDTO.getLwrq()+wcjlDTO.getLwsj());
            //调查人员需要计算
            tempXzjl.setJtrs(wcjlDTO.getJtrs());
            tempXzjl.setFxfs(wcjlDTO.getFxfs());
            tempXzjl.setMfsj(wcjlDTO.getMfsj());
            boolean xzFlag = xxtjService.insetOrUpdateOneXzjl(tempXzjl);
            //直接生成了外出信息
            //有可能存在与之对应的新增信息 没有就不更新 有也要更新一下
            if (wcFlag && xzFlag){
                return "success";
            }else {
                return "error";
            }
        }else {
            //没有记录 要同时生成新增表和外出表
            //生成新增记录
            Xzjl tempXzjl = new Xzjl();
            tempXzjl.setXm(wcjlDTO.getXm());
            tempXzjl.setXb(wcjlDTO.getXb());
            tempXzjl.setNl(wcjlDTO.getNl());
            tempXzjl.setSfz(wcjlDTO.getSfz());
            tempXzjl.setSb(wcjlDTO.getSb());
            tempXzjl.setDhh(wcjlDTO.getDhh());
            tempXzjl.setWldzAndFxrqAndFxsj(wcjlDTO.getWldz()+wcjlDTO.getFxrq()+wcjlDTO.getFxsj());
            tempXzjl.setWcyy(wcjlDTO.getWcyy());
            tempXzjl.setLwrqAndLwsj(wcjlDTO.getLwrq()+wcjlDTO.getLwsj());
            //调查人员需要计算
            tempXzjl.setJtrs(wcjlDTO.getJtrs());
            tempXzjl.setFxfs(wcjlDTO.getFxfs());
            tempXzjl.setMfsj(wcjlDTO.getMfsj());
            boolean xzFlag = xxtjService.insetOrUpdateOneXzjl(tempXzjl);

            //生成外出记录
            Wcjl tempWcjl = new Wcjl();
            tempWcjl.setXm(wcjlDTO.getXm());
            tempWcjl.setXb(wcjlDTO.getXb());
            tempWcjl.setNl(wcjlDTO.getNl());
            tempWcjl.setSfz(wcjlDTO.getSfz());
            tempWcjl.setSb(wcjlDTO.getSb());
            tempWcjl.setDhh(wcjlDTO.getDhh());
            tempWcjl.setWldzAndFxrqAndFxsj(wcjlDTO.getWldz()+wcjlDTO.getFxrq()+wcjlDTO.getFxsj());
            tempWcjl.setWcyy(wcjlDTO.getWcyy());
            tempWcjl.setLwrqAndLwsj(wcjlDTO.getLwrq()+wcjlDTO.getLwsj());
            tempWcjl.setWcdzAndWcrqAndWcsj(wcjlDTO.getWcdz()+wcjlDTO.getWcrq()+wcjlDTO.getWcsj());
            tempWcjl.setFxfs(wcjlDTO.getFxfs());
            tempWcjl.setMfsj(wcjlDTO.getMfsj());
            boolean wcFlag =  xxtjService.insetOrUpdateOneWcjl(tempWcjl);

            if (xzFlag && wcFlag){
            return "success";
            }else {
                return "error";
            }
        }
    }


    //跳转后台
    @GetMapping("/htxt")
    public String toHtxt(){
        return "htxt";
    }

    //后台系统登录
    @PostMapping("/htxt")
    public String htxtLogin(@RequestParam("loginName")String name,
                            @RequestParam("loginWord")String word,
                            HttpServletRequest request,
                            Model model){
        if ("18981297851".equals(name) && "740519".equals(word)) {
            request.getSession().setAttribute("admin","admin");
            return "gljm";
        }else {
            model.addAttribute("error","账号或者密码错误!登录失败!");
            return "htxt";
        }
    }


    //*查询今日所有的信息
    @PostMapping("/gljm/findToadyAllWc")
    @ResponseBody
    public List<Wcjl> findToadyAllWc(@RequestParam("today") String today){
       List<Wcjl> wcjlList = new ArrayList<>();
       wcjlList = xxtjService.findToadyAllWc(today);
        return wcjlList;
    }

    //*查询今日所有新增
    @PostMapping("/gljm/findTodayAllXz")
    @ResponseBody
    public List<Xzjl> findToadyAllXz(@RequestParam("today") String today){
        List<Xzjl> xzjlList = new ArrayList<>();
        xzjlList = xxtjService.findToadyAllXz(today);
        return xzjlList;
    }


    //*按日期查询外出
    @PostMapping("/gljm/findWcByRq")
    @ResponseBody
    public List<Wcjl> findWcByRq(@RequestParam("rq") String rq){
        List<Wcjl> wcjlList = new ArrayList<>();
        wcjlList = xxtjService.findToadyAllWc(rq);
        return wcjlList;
    }

    //*按日期查询新增
    @PostMapping("/gljm/findXzByRq")
    @ResponseBody
    public List<Xzjl> findXzByRq(@RequestParam("rq") String rq){
        List<Xzjl> xzjlList = new ArrayList<>();
        xzjlList = xxtjService.findToadyAllXz(rq);
        return xzjlList;
    }

    //*按姓名查询外出
    @PostMapping("/gljm/findWcByXm")
    @ResponseBody
    public List<Wcjl> findWcByXm(@RequestParam("xm") String xm){
        if("".equals(xm)  || null == xm){
            return null;
        }else{
            List<Wcjl> wcjlList = new ArrayList<>();
            wcjlList = xxtjService.findWcByXm(xm);
            return wcjlList;
        }
    }

    //*按姓名查询新增
    @PostMapping("/gljm/findXzByXm")
    @ResponseBody
    public List<Xzjl> findXzByXm(@RequestParam("xm") String xm){
        if("".equals(xm)  || null == xm){
            return null;
        }else{
            List<Xzjl> xzjlList = new ArrayList<>();
            xzjlList = xxtjService.findXzByXm(xm);
            return xzjlList;
        }
    }

    //进入查询界面
    @GetMapping("/cxjm")
    public String toCxjm(){
        return "cxjm";
    }
}