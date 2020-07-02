package com.xunqi.gulimall.order.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Description:
 * @Created: with IntelliJ IDEA.
 * @author: 夏沫止水
 * @createTime: 2020-07-02 17:00
 **/

@Controller
public class HelloController {

    @GetMapping(value = "/{page}.html")
    public String listPage(@PathVariable("page") String page) {

        return page;
    }

}
