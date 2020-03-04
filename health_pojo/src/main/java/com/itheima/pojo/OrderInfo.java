package com.itheima.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liam
 * @description 订单信息实体类
 * @date 2020/3/2-19:06
 * @Version 1.0.0
 */
public class OrderInfo implements Serializable {
    private String validateCode;
    private int setmealId;
    private Date orderDate;
    private String orderType;
    private Member member;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    public int getSetmealId() {
        return setmealId;
    }

    public void setSetmealId(int setmealId) {
        this.setmealId = setmealId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
