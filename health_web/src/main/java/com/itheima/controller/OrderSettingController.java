package com.itheima.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import com.itheima.util.POIUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author liam
 * @description 预约设置控制器
 * @date 2020/2/28-15:15
 * @Version 1.0.0
 */
@RestController
@RequestMapping("/orderSetting")
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;

    @RequestMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting) {

//        System.out.println(orderSetting.getOrderDate());
        orderSettingService.editNumberByDate(orderSetting);
        return Result.success(MessageConstant.ORDERSETTING_SUCCESS);
    }

    @RequestMapping("/upload")
    public Result upload(MultipartFile excelFile) {

        try {
//            1.调用工具类读取上传的excel表格
            List<String[]> rows = POIUtils.readExcel(excelFile);
//            2.表格数据不为空则继续操作
            if (CollectionUtil.isNotEmpty(rows)) {
                ArrayList<OrderSetting> orderSettings = new ArrayList<>();
                for (String[] row : rows) {
//                   判断该行数据若小于2，则不进行如下操作，继续循环
                    if (row.length < 2){
                        continue;
                    }
//                    取出每一行的数据，并封装成orderSetting对象
                    String dateStr = row[0];
                    String numberStr = row[1];

                    DateTime date = DateUtil.parse(dateStr, "yyyy/MM/dd");
                    OrderSetting orderSetting = new OrderSetting(date, Integer.valueOf(numberStr));

                    orderSettings.add(orderSetting);
//                   调用service的方法进行批量添加
                    orderSettingService.addBatch(orderSettings);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error(MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
        return Result.success(MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
    }

    @RequestMapping("/findByMonth")
    public Result findByMonth(String dateStr) {
        List<HashMap> maps = orderSettingService.findByMonth(dateStr);
        return Result.success("", maps);
    }
}


