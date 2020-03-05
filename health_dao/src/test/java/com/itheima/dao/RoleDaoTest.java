package com.itheima.dao;

import com.itheima.pojo.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext-*.xml")
public class RoleDaoTest {

    @Autowired
    private RoleDao roleDao;

    @Test
    public void findByUserId() {
        Set<Role> roles = roleDao.findByUserId(2);
        System.out.println("roles = " + roles);
    }

}