package com.example.community.service;

import com.example.community.mapper.XXTJMapper;
import com.example.community.model.Total;
import com.example.community.model.Wcjl;
import com.example.community.model.Xzjl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Yiang37
 * @Date 2020/3/9 16:34
 * Description:
 * 信息统计service
 */

@Service
public class XXTJService {
    @Autowired
    XXTJMapper xxtjMapper;

    //查询有没有身份证
    public boolean findSFZ(String sfz) {
        Total dbTotal = xxtjMapper.findOneTotal(sfz);
        //没有记录
        return dbTotal != null;
    }

    //插入一条外出记录
    public boolean insetOrUpdateOneWcjl(Wcjl tempWcjl) {
        //插入时判断身份证有没有记录
        Wcjl dbWcjl = xxtjMapper.findOneWcjl(tempWcjl.getSfz());
        if (dbWcjl == null) {
            xxtjMapper.insertOneWcjl(tempWcjl);
        } else {
            //有的话是做更新
            tempWcjl.setId(dbWcjl.getId());
            xxtjMapper.updateOneWcjl(tempWcjl);

            //更新了外出表信息 还要看看新增表信息变不变
        }
        return true;
    }


    //插入一条新增记录
    public boolean insetOrUpdateOneXzjl(Xzjl tempXzjl) {
        Xzjl dbXzjl = xxtjMapper.findOneXzjl(tempXzjl.getSfz());
        if (dbXzjl == null){
            //新增
            //先计算社别
            String tempSb = tempXzjl.getSb();
            switch (tempSb) {
                case "先锋村一社":
                    tempXzjl.setDcry("谢道福");
                    break;
                case "先锋村二社":
                    tempXzjl.setDcry("杨绍楚");
                    break;
                case "先锋村三社":
                    tempXzjl.setDcry("杨静志");
                    break;
                case "先锋村四社":
                    tempXzjl.setDcry("刘少勇");
                    break;
                case "先锋村五社":
                    tempXzjl.setDcry("吴文碧");
                    break;
                default:
                    tempXzjl.setDcry("范清香");
                    break;
            }


            //总表里插入一个新身份证
            xxtjMapper.insertOneSfzToTotal(tempXzjl.getSfz());
            xxtjMapper.insertOneXzjl(tempXzjl);
            return true;
        }else {
            //先计算社别
            String tempSb = tempXzjl.getSb();
            switch (tempSb) {
                case "先锋村一社":
                    tempXzjl.setDcry("谢道福");
                    break;
                case "先锋村二社":
                    tempXzjl.setDcry("杨绍楚");
                    break;
                case "先锋村三社":
                    tempXzjl.setDcry("杨静志");
                    break;
                case "先锋村四社":
                    tempXzjl.setDcry("刘少勇");
                    break;
                case "先锋村五社":
                    tempXzjl.setDcry("吴文碧");
                    break;
                default:
                    tempXzjl.setDcry("范清香");
                    break;
            }
            //更新
            tempXzjl.setId(dbXzjl.getId());
            xxtjMapper.updateOneXzjl(tempXzjl);
            return true;
        }

    }

    //*根据今日时间查询所有外出记录
    public List<Wcjl> findToadyAllWc(String today) {
        List<Wcjl> wcjlList = xxtjMapper.findToadyAllWc(today);
        return wcjlList;
    }
    //*根据今日时间查询所有新增记录
    public List<Xzjl> findToadyAllXz(String today) {
        List<Xzjl> xzjlList = xxtjMapper.findToadyAllXz(today);
        return xzjlList;
    }

    //*根据姓名查询外出
    public List<Wcjl> findWcByXm(String xm) {
        List<Wcjl> wcjlList = xxtjMapper.findWcByXm(xm);
        return wcjlList;
    }

    //*根据姓名查询新增
    public List<Xzjl> findXzByXm(String xm) {
        List<Xzjl> xzjlList = xxtjMapper.findXzByXm(xm);
        return xzjlList;
    }
}