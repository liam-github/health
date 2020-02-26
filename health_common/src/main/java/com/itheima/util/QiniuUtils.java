package com.itheima.util;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

/**
 * @author liam
 * @description 文件上传七牛工具类
 * @date 2020/2/26-19:27
 * @Version 1.0.0
 */
public class QiniuUtils {


    static String accessKey = "RVxtzRRGK4v1oAFv0NclmPIS3gfdwG9d3ZnnOXL0";
    static String secretKey = "niYubft5gC5BJbsLzunQNMnOn5ynCtgtK6JwdPG9";
    static String bucket = "liam-health";

    public static void upload(byte[] data, String key) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.huadong());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);

        //...生成上传凭证，然后准备上传
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(data, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
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

    public static void delete(String fileName) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.huadong());
        String key = fileName;
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
    }
}
