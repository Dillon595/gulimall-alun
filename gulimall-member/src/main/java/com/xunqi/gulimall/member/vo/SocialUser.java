package com.xunqi.gulimall.member.vo;

import lombok.Data;

/**
 * @Description: 社交用户信息
 * @Created: with IntelliJ IDEA.
 * @author: 阿伦啊
 * @createTime: 2021-06-28 11:04
 **/

@Data
public class SocialUser {

    private String access_token;

    private String remind_in;

    private long expires_in;

    private String uid;

    private String isRealName;

}
