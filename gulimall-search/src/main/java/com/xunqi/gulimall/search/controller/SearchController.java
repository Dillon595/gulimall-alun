package com.xunqi.gulimall.search.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Description:
 * @Created: with IntelliJ IDEA.
 * @author: 夏沫止水
 * @createTime: 2020-06-12 18:07
 **/

@Controller
public class SearchController {

    @GetMapping(value = "/list.html")
    public String listPage() {
        return "list";
    }

}
