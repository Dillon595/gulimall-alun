package com.xunqi.gulimall.order.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Created: with IntelliJ IDEA.
 * @author: 阿伦啊
 * @createTime: 2021-07-08 22:52
 **/

@Component
@ConfigurationProperties(prefix = "wx")
@Data
public class WxAccountConfig {

    private String appId;

    private String mchId;

    private String mchKey;

    private String notifyUrl;

    private String returnUrl;

}
