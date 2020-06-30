package com.xunqi.gulimall.cart.service;

import com.xunqi.gulimall.cart.vo.CartItemVo;

import java.util.concurrent.ExecutionException;

/**
 * @Description:
 * @Created: with IntelliJ IDEA.
 * @author: 夏沫止水
 * @createTime: 2020-06-30 17:06
 **/
public interface CartService {

    /**
     * 添加至购物车
     * @param skuId
     * @param num
     * @return
     */
    CartItemVo addToCart(Long skuId, Integer num) throws ExecutionException, InterruptedException;
}
