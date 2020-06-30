package com.xunqi.gulimall.cart.controller;

import com.xunqi.gulimall.cart.interceptor.CartInterceptor;
import com.xunqi.gulimall.cart.service.CartService;
import com.xunqi.gulimall.cart.to.UserInfoTo;
import com.xunqi.gulimall.cart.vo.CartItemVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;

/**
 * @Description:
 * @Created: with IntelliJ IDEA.
 * @author: 夏沫止水
 * @createTime: 2020-06-30 17:12
 **/

@Controller
public class CartController {

    @Resource
    private CartService cartService;

    /**
     * 去购物车页面的请求
     * 浏览器有一个cookie:user-key 标识用户的身份，一个月过期
     * 如果第一次使用jd的购物车功能，都会给一个临时的用户身份:
     * 浏览器以后保存，每次访问都会带上这个cookie；
     *
     * 登录：session有
     * 没登录：按照cookie里面带来user-key来做
     * 第一次，如果没有临时用户，自动创建一个临时用户
     *
     * @return
     */
    @GetMapping(value = "/cart.html")
    public String cartListPage() {

        //快速得到用户信息：id,user-key
        UserInfoTo userInfoTo = CartInterceptor.toThreadLocal.get();
        System.out.println(userInfoTo);

        return "cartList";
    }


    /**
     * 添加商品到购物车
     * @return
     */
    @GetMapping(value = "/addCartItem")
    public String addCartItem(@RequestParam("skuId") Long skuId,
                              @RequestParam("num") Integer num,
                              Model model) throws ExecutionException, InterruptedException {

        CartItemVo cartItemVo = cartService.addToCart(skuId,num);

        model.addAttribute("cartItem",cartItemVo);

        return "success";
    }

}
