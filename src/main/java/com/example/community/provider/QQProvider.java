package com.example.community.provider;

import com.alibaba.fastjson.JSON;
import com.example.community.dto.AccessTokenDTO;
import com.example.community.dto.QQUser;
import lombok.Data;
import okhttp3.*;
import org.springframework.stereotype.Component;

@Component
public class QQProvider {

    //1.得到QQ用户的token
    public String getQQAccessToken(AccessTokenDTO accessTokenDTO){
        String appid = accessTokenDTO.getClient_id();
        String appkey = accessTokenDTO.getClient_secret();
        String redirectURI = accessTokenDTO.getRedirect_uri();
        String code = accessTokenDTO.getCode();
        String asStr ="https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id=" + appid + "&client_secret="+ appkey + "&redirect_uri=" + redirectURI + "&code=" + code;

        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        //RequestBody对象转换成json对象
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url(asStr)
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            //access_token=0A75E58421FF7A72A50AECF6C5483689&expires_in=7776000&refresh_token=A9BA2A4FE54DCD51B5E16BADDE5C316E
            String token =  string.split("&")[0].split("=")[1];
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //2.根据token获取openID
    public String getOpenId(String accessToken){
        String openStr = "https://graph.qq.com/oauth2.0/me?access_token=" + accessToken;

        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        //RequestBody对象转换成json对象
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessToken));

        Request request = new Request.Builder()
                .url(openStr)
                .post(body)
                .build();
        try {
            @Data
            class QQCallBackDTO{
                private String clientId;
                private String openId;
            }
            Response response = client.newCall(request).execute();
            //callback( {"client_id":"101838917","openid":"E70AF3616B2163031C52093AB2D66E91"} );
            String string = response.body().string();
            //json格式的字符串变为实体类对象
            QQCallBackDTO qqCallBackDTO = JSON.parseObject(string, QQCallBackDTO.class);
            String openId = qqCallBackDTO.getOpenId();
            return openId;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //3.根据openId得到QQ用户信息
    public QQUser getQQUser(String openId,String access_token,AccessTokenDTO accessTokenDTO)
    {
        String appid = accessTokenDTO.getClient_id();
        String userStr ="https://graph.qq.com/user/get_user_info?access_token=" + access_token+"&oauth_consumer_key=" + appid + "&openid=" + openId;

        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        //RequestBody对象转换成json对象
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url(userStr)
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            //json字符串转实体对象
            QQUser qqUser = JSON.parseObject(string,QQUser.class);
            return qqUser;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
