package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liam
 * @description
 * @date 2020/3/5-18:10
 * @Version 1.0.0
 */
@Service(interfaceClass = ReportService.class)
@Transactional
public class ReportServiceImpl implements ReportService {

    @Autowired
    private MemberDao memberDao;

    @Override
    public List<Integer> getMemberReport(List<String> months) {
        List<Integer> memberCount = new ArrayList<>();
        for (String month : months) {
            month = month + "-31";
            Integer count = memberDao.getMemberReport(month);
            memberCount.add(count);
        }
        return memberCount;
    }
}
