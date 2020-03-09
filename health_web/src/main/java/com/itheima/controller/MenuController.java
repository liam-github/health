package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.entity.Result;
import com.itheima.pojo.Menu;
import com.itheima.service.MenuService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author liam
 * @description 菜单控制器
 * @date 2020/3/9-14:01
 * @Version 1.0.0
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Reference
    private MenuService menuService;

    @RequestMapping("/findAll")
    @PreAuthorize("hasAuthority('MENU_QUERY')")
    public Result findAll() {
        List<Menu> menuList = menuService.findAll();
        return Result.success("",menuList);
    }

}
