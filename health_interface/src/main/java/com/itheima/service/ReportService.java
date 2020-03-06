package com.itheima.service;

import com.itheima.pojo.ReportData;

import java.util.List;
import java.util.Map;

/**
 * @author liam
 * @description
 * @date 2020/3/5-18:10
 * @Version 1.0.0
 */
public interface ReportService {
    List<Integer> getMemberReport(List<String> months);

    List<Map<String, Object>> getSetmealReport();

    ReportData getBusinessReportData();

}
