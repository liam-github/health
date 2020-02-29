package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import com.itheima.util.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author liam
 * @description 套餐控制器
 * @date 2020/2/27-16:32
 * @Version 1.0.0
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/upload")
    public Result upload(MultipartFile imgFile) {
        Map<String,String> map = null;
        try {
//        1.获取原文件名
            String originalFilename = imgFile.getOriginalFilename();
//        2.获取后缀名
            int index = originalFilename.lastIndexOf(".");
            String suffix = originalFilename.substring(index);
//        3.生成新的随机文件名
            String newFileName = UUID.randomUUID().toString() + suffix;
//        4.调用七牛工具类上传文件
            QiniuUtils.upload(imgFile.getBytes(),newFileName);
//        5.将文件名称返回前端
            String filePath = "http://q6b1yxec5.bkt.clouddn.com/"+ newFileName;
            map = new HashMap<>();
            map.put("filePath",filePath);
            map.put("newFileName",newFileName);
//        6.将上传成功的图片名称存入redis
            Jedis jedis = jedisPool.getResource();
            jedis.sadd(RedisConstant.SETMEAL_PIC_RESOURCES, newFileName);
//            返回前端结果
            return Result.success(MessageConstant.PIC_UPLOAD_SUCCESS, map);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error(MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal) {
        setmealService.add(setmeal);
//        将添加套餐成功的图片名称存入Redis
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, setmeal.getImg());
        return Result.success(MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        return setmealService.findPage(queryPageBean);
    }
}
