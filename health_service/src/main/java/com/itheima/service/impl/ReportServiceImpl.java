package com.itheima.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.dao.SetmealDao;
import com.itheima.pojo.ReportData;
import com.itheima.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private SetmealDao setmealDao;

    @Autowired
    private OrderDao orderDao;

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

    @Override
    public List<Map<String, Object>> getSetmealReport() {

        return setmealDao.getSetmealReport();
    }

    @Override
    public ReportData getBusinessReportData() {
        //1.取得当天、本周第一天、本周最后一天、本月第一天、本月最后一天
        String format = "yyyy-MM-dd";
        DateTime today = new DateTime();
        String todayStr = today.toString(format);
        String beginOfWeek = DateUtil.beginOfWeek(today).toString(format);
        String endOfWeek = DateUtil.endOfWeek(today).toString(format);
        String beginOfMonth = DateUtil.beginOfMonth(today).toString(format);
        String endOfMonth = DateUtil.endOfMonth(today).toString(format);

        //2.构造reportData对象，设置报告日期
        ReportData report = new ReportData();
        report.setReportDate(todayStr);

        //3.查询当天注册会员数
        report.setTodayNewMember(memberDao.findMemberCountByDate(todayStr));
        //4.查询总会员数
        report.setTotalMember(memberDao.findMemberTotalCount());
        //5.查询本周新增会员数
        report.setThisWeekNewMember(memberDao.findMemberCountAfterDate(beginOfWeek));
        //6.本月新增会员数
        report.setThisMonthNewMember(memberDao.findMemberCountAfterDate(beginOfMonth));
        //7.当天预约数量
        report.setTodayOrderNumber(orderDao.findOrderCountByDate(todayStr));
        //8.当天到症数量
        report.setTodayVisitsNumber(orderDao.findVisitsCountByDate(todayStr));
        //9.本周预约数量
        report.setThisWeekOrderNumber(orderDao.findOrderCountBetweenDate(beginOfWeek,endOfWeek));
        //10.本周到症
        report.setThisWeekVisitsNumber(orderDao.findVisitsCountAfterDate(beginOfWeek));
        //11.本月预约
        report.setThisMonthOrderNumber(orderDao.findOrderCountBetweenDate(beginOfMonth,endOfMonth));
        //12.本月到诊
        report.setThisMonthVisitsNumber(orderDao.findVisitsCountAfterDate(beginOfMonth));
        //13.热门套餐
        report.setHotSetmeal(orderDao.findHotSetmeal());

        return report;
    }
}
