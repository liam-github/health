package com.itheima.util;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.Test;

/**
 * @author liam
 * @description
 * @date 2020/2/26-19:55
 * @Version 1.0.0
 */
public class QiniuTest {

    @Test
    public void upload() {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.huadong());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);

        String accessKey = "RVxtzRRGK4v1oAFv0NclmPIS3gfdwG9d3ZnnOXL0";
        String secretKey = "niYubft5gC5BJbsLzunQNMnOn5ynCtgtK6JwdPG9";
        String bucket = "liam-health";
        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "G:\\照片\\DCIM\\1020404A\\DSC_0006.JPG";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = "2.jpg";
        //...生成上传凭证，然后准备上传
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            System.out.println(response.bodyString());
            //解析上传成功的结果
//            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);

            DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }
}
