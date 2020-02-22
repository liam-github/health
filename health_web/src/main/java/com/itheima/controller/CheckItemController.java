package com.itheima.controller;

import com.itheima.service.CheckItemService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liam
 * @description 检查项控制器
 * @date 2020/2/21-22:47
 * @Version 1.0.0
 */
@RestController
@RequestMapping("/checkItem")
public class CheckItemController {

    private CheckItemService checkItemService;

}
