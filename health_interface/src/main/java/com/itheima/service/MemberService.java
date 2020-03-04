package com.itheima.service;

import com.itheima.pojo.Member;

/**
 * @author liam
 * @description
 * @date 2020/3/3-17:10
 * @Version 1.0.0
 */
public interface MemberService {

    Member findByTelephone(String telephone);

    Member add(Member member);
}
