package com.scnu.lwg.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Kin
 * @description test api
 * @email kinsanities@sina.com
 * @time 2021/4/24 7:23 下午
 */
@RestController
@RequestMapping(value = "/lwg/index")
public class IndexController{

    @RequestMapping()
    public String index(){
        return "success";
    }

}
