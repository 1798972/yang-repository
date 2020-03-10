package com.example.community.mapper;

import com.example.community.model.Total;
import com.example.community.model.Wcjl;
import com.example.community.model.Xzjl;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface XXTJMapper {
    //根据身份证 查询一条新增记录
    @Select("select * from total where ysfz = #{sfz}")
    List<Total> findOneTotal(String sfz);

    //total表插入一个新身份证
    @Insert("insert into total (ysfz) values (#{sfz})")
    void insertOneSfzToTotal(String sfz);

    //插入一条外出记录
    @Insert("insert into wcjl (xm,xb,nl,sfz,sb,dhh,wldzandfxrqandfxsj,wcyy,lwrqandlwsj,wcdzandwcrqandwcsj,fxfs,mfsj) values (#{xm},#{xb},#{nl},#{sfz},#{sb},#{dhh},#{wldzAndFxrqAndFxsj},#{wcyy},#{lwrqAndLwsj},#{wcdzAndWcrqAndWcsj},#{fxfs},#{mfsj})")
    void insertOneWcjl(Wcjl wcjl);

    //插入一条新增记录
    @Insert("insert into xzjl (xm,xb,nl,sfz,sb,dhh,wldzandfxrqandfxsj,wcyy,lwrqandlwsj,fxfs,dcry,jtrs,mfsj) values (#{xm},#{xb},#{nl},#{sfz},#{sb},#{dhh},#{wldzAndFxrqAndFxsj},#{wcyy},#{lwrqAndLwsj},#{fxfs},#{dcry},#{jtrs},#{mfsj})")
    void insertOneXzjl(Xzjl tempXzjl);

    //更新一条新增记录
    @Update("update xzjl set xm=#{xm},xb=#{xb},nl=#{nl},sfz=#{sfz},sb=#{sb},dhh=#{dhh},wldzandfxrqandfxsj=#{wldzAndFxrqAndFxsj},wcyy=#{wcyy},fxfs=#{fxfs},dcry=#{dcry},jtrs=#{jtrs},mfsj=#{mfsj} where id =#{id}")
    void updateOneXzjl(Xzjl tempXzjl);

    //根据身份证查询一条新增记录
    @Select("select * from xzjl where sfz = #{sfz}")
    Xzjl findOneXzjl(String sfz);

    //根据身份证查找一条外出记录
    @Select("select * from wcjl where sfz = #{sfz}")
    Wcjl findOneWcjl(String sfz);

    //更新一条外出记录
    @Update("update wcjl set xm=#{xm},xb=#{xb},nl=#{nl},sfz=#{sfz},sb=#{sb},dhh=#{dhh},wldzandfxrqandfxsj=#{wldzAndFxrqAndFxsj},wcyy=#{wcyy},lwrqandlwsj=#{lwrqAndLwsj},wcdzandwcrqandwcsj=#{wcdzAndWcrqAndWcsj},fxfs=#{fxfs},mfsj=#{mfsj} where id =#{id}")
    void updateOneWcjl(Wcjl tempWcjl);

    //*根据今日时间查询所有外出记录
    @Select("select * from wcjl where mfsj = #{toady}")
    List<Wcjl> findToadyAllWc(String today);

    //*根据今日时间查询所有新增记录
    @Select("select * from xzjl where mfsj = #{toady}")
    List<Xzjl> findToadyAllXz(String today);

    //*根据姓名查询外出记录
    @Select("select * from wcjl where xm like '%${xm}%'")
    List<Wcjl> findWcByXm(String xm);

    //*根据姓名查询新增记录
    @Select("select * from xzjl where xm like '%${xm}%'")
    List<Xzjl> findXzByXm(String xm);
}
