package com.itheima.controller;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.HotSetmeal;
import com.itheima.pojo.ReportData;
import com.itheima.service.ReportService;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author liam
 * @description 报表控制器
 * @date 2020/3/5-17:11
 * @Version 1.0.0
 */
@RestController
@RequestMapping("/report")
public class ReportController {

    @Reference
    private ReportService reportService;

    /**
     * 获取过去12个月的会员增长情况
     * @date 2020/3/5-21:15
     * @param []
     * @return com.itheima.entity.Result
     */ 
    @RequestMapping("/getMemberReport")
    public Result getMemberReport() {
        Date now = new Date();
        DateTime begin = DateUtil.offsetMonth(now, -12);
        //List<DateTime> dateTimes = DateUtil.rangeToList(begin, now, DateField.MONTH);
        List<String> months = new ArrayList<>();
        for (int i = 0; i <= 12; i++) {
            DateTime nexMonth = DateUtil.offsetMonth(begin, i);
            months.add(nexMonth.toString("yyyy-MM"));
        }

        List<Integer> memberCount = reportService.getMemberReport(months);

        HashMap<String, Object> map = new HashMap<>();
        map.put("months", months);
        map.put("memberCount", memberCount);
        return Result.success("", map);
    }

    /**
     * 获取套餐预约占比情况
     * @date 2020/3/5-21:19
     * @param []
     * @return com.itheima.entity.Result
     */ 
    @RequestMapping("/getSetmealReport")
    public Result getSetmealReport() {

        List<Map<String,Object>> list = reportService.getSetmealReport();
        return Result.success("",list);
    }

    @RequestMapping("/getBusinessReportData")
    public Result getBusinessReportData() {
        ReportData reportData = reportService.getBusinessReportData();
        return Result.success("", reportData);
    }

    @RequestMapping("/exportBusinessReport")
    public void exportBusinessReport(HttpServletRequest request, HttpServletResponse response) {
        try {
            //1.获取统计数据
            ReportData report = reportService.getBusinessReportData();
            List<HotSetmeal> hotSetmeals = report.getHotSetmeal();

            //2.根据模板新建Excel对象
            String realPath = request.getSession().getServletContext().getRealPath("template");
            XSSFWorkbook workbook = new XSSFWorkbook(realPath + File.separator + "report_template.xlsx");
            XSSFSheet sheet0 = workbook.getSheetAt(0);

            //3.填充数据
            sheet0.getRow(2).getCell(5).setCellValue(report.getReportDate());

            sheet0.getRow(4).getCell(5).setCellValue(report.getTodayNewMember());
            sheet0.getRow(4).getCell(7).setCellValue(report.getTotalMember());
            sheet0.getRow(5).getCell(5).setCellValue(report.getThisWeekNewMember());
            sheet0.getRow(5).getCell(7).setCellValue(report.getThisMonthNewMember());

            sheet0.getRow(7).getCell(5).setCellValue(report.getTodayOrderNumber());
            sheet0.getRow(7).getCell(7).setCellValue(report.getTodayVisitsNumber());
            sheet0.getRow(8).getCell(5).setCellValue(report.getThisWeekOrderNumber());
            sheet0.getRow(8).getCell(7).setCellValue(report.getThisWeekVisitsNumber());
            sheet0.getRow(9).getCell(5).setCellValue(report.getThisMonthOrderNumber());
            sheet0.getRow(9).getCell(7).setCellValue(report.getThisMonthVisitsNumber());

            int i = 12;
            for (HotSetmeal hotSetmeal : hotSetmeals) {
                sheet0.getRow(i).getCell(4).setCellValue(hotSetmeal.getName());
                sheet0.getRow(i).getCell(5).setCellValue(hotSetmeal.getSetmealCount());
                //注意：bigDecimal数据在表格中不能存放，要转换成double
                sheet0.getRow(i).getCell(6).setCellValue(hotSetmeal.getProportion().doubleValue());
                i++;
            }

            //4.设置响应数据并返回
            // 数据类型
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            // 文件名,以附件形式
            response.setHeader("Content-Disposition","attachment;filename="
                    +java.net.URLEncoder.encode("运营数据报表", "UTF-8")+".xlsx");
            // 向浏览器写回数据
            ServletOutputStream out = response.getOutputStream();
            workbook.write(out);
            out.flush();
            out.close();
            workbook.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
