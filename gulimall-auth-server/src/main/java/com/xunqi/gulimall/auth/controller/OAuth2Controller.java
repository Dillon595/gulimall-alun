package com.xunqi.gulimall.auth.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xunqi.common.utils.HttpUtils;
import com.xunqi.common.utils.R;
import com.xunqi.gulimall.auth.feign.MemberFeignService;
import com.xunqi.gulimall.auth.vo.MemberResponseVo;
import com.xunqi.gulimall.auth.vo.SocialUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 处理社交登录请求
 * @Created: with IntelliJ IDEA.
 * @author: 夏沫止水
 * @createTime: 2020-06-28 10:16
 **/

@Slf4j
@Controller
public class OAuth2Controller {

    @Autowired
    private MemberFeignService memberFeignService;


    @GetMapping(value = "/oauth2.0/weibo/success")
    public String weibo(@RequestParam("code") String code) throws Exception {

        Map<String, String> map = new HashMap<>();
        map.put("client_id","2077705774");
        map.put("client_secret","40af02bd1c7e435ba6a6e9cd3bf799fd");
        map.put("grant_type","authorization_code");
        map.put("redirect_uri","http://auth.gulimall.com/oauth2.0/weibo/success");
        map.put("code",code);

        //1、根据code换取access_token
        HttpResponse response = HttpUtils.doPost("https://api.weibo.com", "/oauth2/access_token", "post", new HashMap<>(), map, new HashMap<>());


        //2、处理
        if (response.getStatusLine().getStatusCode() == 200) {
            //获取到了access_token
            String json = EntityUtils.toString(response.getEntity());
            //String json = JSON.toJSONString(response.getEntity());
            SocialUser socialUser = JSON.parseObject(json, SocialUser.class);

            //知道了哪个社交用户
            //1）、当前用户如果是第一次进网站，自动注册进来（为当前社交用户生成一个会员信息，以后这个社交账号就对应指定的会员）
            //登录或者注册这个社交用户
            System.out.println(socialUser.getAccess_token());
            //调用远程服务
            R oauthLogin = memberFeignService.oauthLogin(socialUser);
            if (oauthLogin.getCode() == 0) {
                MemberResponseVo data = oauthLogin.getData("data", new TypeReference<MemberResponseVo>() {});
                log.info("登录成功：用户信息：{}",data.toString());
                //2、登录成功跳回首页
                return "redirect:http://gulimall.com";
            } else {
                return "redirect:http://auth.gulimall.com/login.html";
            }

        } else {
            return "redirect:http://auth.gulimall.com/login.html";
        }

    }

}
