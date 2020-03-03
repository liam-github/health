package com.itheima.util;

import org.apache.commons.beanutils.BeanUtils;

import java.util.Map;

/**
 * @description: 封装实体类的工具类
 * @author: liam
 * @date: 2020/1/6 0006-16:16
 * @Version: 1.0.0
 */
public class ComBeanUtil {
    public static <T> T getBean(Class<T> clazz, Map<String,Object> map) {
        T t = null;
        try {
            t = clazz.newInstance();
            BeanUtils.populate(t, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }
}