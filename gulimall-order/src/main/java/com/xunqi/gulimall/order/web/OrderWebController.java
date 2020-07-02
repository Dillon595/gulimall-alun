package com.xunqi.gulimall.order.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Description:
 * @Created: with IntelliJ IDEA.
 * @author: 夏沫止水
 * @createTime: 2020-07-02 18:35
 **/

@Controller
public class OrderWebController {


    @GetMapping(value = "/toTrade")
    public String toTrade() {

        return "confirm";
    }

}
