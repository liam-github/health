package com.itheima.controller;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.entity.Result;
import com.itheima.service.ReportService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author liam
 * @description 报表控制器
 * @date 2020/3/5-17:11
 * @Version 1.0.0
 */
@RestController
@RequestMapping("/report")
public class ReportController {

    @Reference
    private ReportService reportService;

    /**
     * 获取过去12个月的会员增长情况
     * @date 2020/3/5-21:15
     * @param []
     * @return com.itheima.entity.Result
     */ 
    @RequestMapping("/getMemberReport")
    public Result getMemberReport() {
        Date now = new Date();
        DateTime begin = DateUtil.offsetMonth(now, -12);
        //List<DateTime> dateTimes = DateUtil.rangeToList(begin, now, DateField.MONTH);
        List<String> months = new ArrayList<>();
        for (int i = 0; i <= 12; i++) {
            DateTime nexMonth = DateUtil.offsetMonth(begin, i);
            months.add(nexMonth.toString("yyyy-MM"));
        }

        List<Integer> memberCount = reportService.getMemberReport(months);

        HashMap<String, Object> map = new HashMap<>();
        map.put("months", months);
        map.put("memberCount", memberCount);
        return Result.success("", map);
    }

    /**
     * 获取套餐预约占比情况
     * @date 2020/3/5-21:19
     * @param []
     * @return com.itheima.entity.Result
     */ 
    @RequestMapping("/getSetmealReport")
    public Result getSetmealReport() {


        return null;
    }
}
