package com.itheima.wechat;

import com.aliyuncs.exceptions.ClientException;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.util.JedisUtils;
import com.itheima.util.SmsUtil;
import com.itheima.util.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author liam
 * @description 验证码控制器
 * @date 2020/3/2-18:41
 * @Version 1.0.0
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    @Autowired
    private JedisUtils jedisUtils;

    @RequestMapping("/send4Order")
    public Result send4Order(String telephone) {
        try {
            //1.获取验证码
            Integer code = ValidateCodeUtils.generateValidateCode(4);
            //2.将验证码短信发送给客户（阿里云服务，收费）
//            SmsUtil.sendSmsCode(telephone,code);
            //3.将验证码存入redis
//            Jedis jedis = jedisPool.getResource();
//            //该方法的第二个参数是这个字段保存在redis数据库中多少秒后将自动删除
//            jedis.setex(telephone + RedisMessageConstant.SENDTYPE_ORDER, 60 * 5 * 10, code);
//            jedis.close();
            jedisUtils.setex(telephone + RedisMessageConstant.SENDTYPE_ORDER,60*5*10,code.toString());

        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        return Result.success(MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }

    @RequestMapping("/send4Login")
    public Result send4Login(String telephone) {
        try {
            //1.获取验证码
            Integer code = ValidateCodeUtils.generateValidateCode(4);
            //2.将验证码短信发送给客户（阿里云服务，收费）
//            SmsUtil.sendSmsCode(telephone,code);
            //3.将验证码存入redis
//            Jedis jedis = jedisPool.getResource();
//            //该方法的第二个参数是这个字段保存在redis数据库中多少秒后将自动删除
//            jedis.setex(telephone + RedisMessageConstant.SENDTYPE_ORDER, 60 * 5 * 10, code);
//            jedis.close();
            jedisUtils.setex(telephone + RedisMessageConstant.SENDTYPE_LOGIN,60*5*10,code.toString());

        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        return Result.success(MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }
}
