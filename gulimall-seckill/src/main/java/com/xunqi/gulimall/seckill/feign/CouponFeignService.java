package com.xunqi.gulimall.seckill.feign;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Description:
 * @Created: with IntelliJ IDEA.
 * @author: 夏沫止水
 * @createTime: 2020-07-09 19:33
 **/

@FeignClient("gulimall-coupon")
public interface CouponFeignService {



}
