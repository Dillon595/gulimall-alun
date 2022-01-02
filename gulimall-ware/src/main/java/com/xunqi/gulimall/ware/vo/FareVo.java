package com.xunqi.gulimall.ware.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description:
 * @Created: with IntelliJ IDEA.
 * @author: 阿伦啊
 * @createTime: 2021-07-03 20:07
 **/

@Data
public class FareVo {

    private MemberAddressVo address;

    private BigDecimal fare;

}


