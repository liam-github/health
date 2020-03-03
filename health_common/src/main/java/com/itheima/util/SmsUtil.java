package com.itheima.util;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.sun.jdi.PathSearchingVirtualMachine;

/**
 * @author liam
 * @description 云短信发送验证码工具类
 * @date 2020/3/2-16:46
 * @Version 1.0.0
 */
public class SmsUtil {

    public static void sendSmsCode(String phoneNumber, String code) throws ClientException {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou"
                , "LTAI4FkHe2fitSsJCuwBqV6G", "QNvn1IJSp1KkzUqHYHO08U21JwrdZZ");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", "小鱼健康");
        request.putQueryParameter("TemplateCode", "SMS_184621652");
//        request.putQueryParameter("TemplateParam", "{\"code\":\"1234\"}");
        //改用new json的方式代替字符串拼接json
        JSONObject json = new JSONObject();
        json.put("code", code);
        request.putQueryParameter("TemplateParam", json.toJSONString());

//        try {
        CommonResponse response = client.getCommonResponse(request);
        System.out.println(response.getData());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public static void main(String[] args) throws ClientException {
        sendSmsCode("18817775843","CCzHK");
    }

}
