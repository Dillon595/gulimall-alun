package com.xunqi.gulimall.seckill.service;

/**
 * @Description:
 * @Created: with IntelliJ IDEA.
 * @author: 夏沫止水
 * @createTime: 2020-07-09 19:29
 **/
public interface SeckillService {

    /**
     * 上架三天需要秒杀的商品
     */
    void uploadSeckillSkuLatest3Days();

}
