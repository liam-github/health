package com.itheima.service;

import java.util.List;

/**
 * @author liam
 * @description
 * @date 2020/3/5-18:10
 * @Version 1.0.0
 */
public interface ReportService {
    List<Integer> getMemberReport(List<String> months);
}
