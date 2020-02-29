package com.itheima.job;

import cn.hutool.core.collection.CollectionUtil;
import com.itheima.constant.RedisConstant;
import com.itheima.util.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Collection;
import java.util.Set;

/**
 * @author liam
 * @description 清理图片的任务
 * @date 2020/2/27-21:10
 * @Version 1.0.0
 */
@Component
public class ClearImageJob {

    @Autowired
    private JedisPool jedisPool;

    public void clearImages(){
//        1.获取redis链接
        Jedis jedis = jedisPool.getResource();
//        2.比较两个集合的差值，获取脏图片名称
        Set<String> set = jedis.sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        //3.循环删除
        if (CollectionUtil.isNotEmpty(set)) {
            for (String pic : set) {
                //删除七牛网图片
                QiniuUtils.delete(pic);
                //删除redis缓存
                jedis.srem(RedisConstant.SETMEAL_PIC_RESOURCES, pic);
            }
        }

        //此处第3步可以用set.toArray()方法，将set转换成数组，然后进行批量删除操作

//        4.关闭资源
        jedis.close();
    }
}
