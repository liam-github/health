package com.itheima.util;

import com.itheima.constant.RedisMessageConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author liam
 * @description jedis工具类封装获取和关闭jedis的步骤
 * @date 2020/3/2-18:34
 * @Version 1.0.0
 */
@Component
public class JedisUtils {

    @Autowired
    private JedisPool jedisPool;

    public void setex(String key,int second, String code) {
        Jedis jedis = jedisPool.getResource();
        //该方法的第二个参数是这个字段保存在redis数据库中多少秒后将自动删除
        jedis.setex(key, second, code);
        jedis.close();
    }

    public String get(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Long del(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0L;
    }
}
