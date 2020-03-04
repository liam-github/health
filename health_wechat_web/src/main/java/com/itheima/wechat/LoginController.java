package com.itheima.wechat;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import com.itheima.util.JedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * @author liam
 * @description 登录控制器
 * @date 2020/3/3-16:16
 * @Version 1.0.0
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Reference
    private MemberService memberService;

    @Autowired
    private JedisUtils jedisUtils;

    @RequestMapping("/check")
    public Result check(@RequestBody Map map) {
        String telephone = (String) map.get("telephone");
        String validateCode = (String) map.get("validateCode");

        //1.校验验证码
        String redisCode = jedisUtils.get(telephone+ RedisMessageConstant.SENDTYPE_LOGIN);
        if (redisCode == null || ! redisCode.equals(validateCode)) {
            //验证码不存在或者验证码不匹配，则返回提示信息
            return Result.error(MessageConstant.VALIDATECODE_ERROR);
        }

        //2.判断该用户是否会员
        Member member = memberService.findByTelephone(telephone);
        if (member == null) {
            //没有该用户则创建用户并进行注册
            member = new Member();
            member.setPhoneNumber(telephone);
            member.setRegTime(new Date());
            member = memberService.add(member);
        }

        //3.将用户信息存在session域对象中，表示已经登录
        String token = UUID.randomUUID().toString();
        jedisUtils.setex(token,60*60*24*15, JSON.toJSONString(member));
        return Result.success(MessageConstant.LOGIN_SUCCESS,token);
    }
}
