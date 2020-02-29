package com.itheima.util;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.IOException;

/**
 * @author liam
 * @description POI操作exel测试类
 * @date 2020/2/29-18:29
 * @Version 1.0.0
 */
public class POITest {

    @Test
    public void readExel() {
        try {
            //创建工作簿
            XSSFWorkbook workbook = new XSSFWorkbook("C:\\Users\\liam\\Desktop\\ordersetting_template.xlsx");
            //获取工作表，既可以根据工作表的顺序获取，也可以根据工作表的名称获取
            XSSFSheet sheet = workbook.getSheetAt(0);
            //获取当前工作表最后一行的行号，行号从0开始
            int lastRowNum = sheet.getLastRowNum();
            System.out.println("lastRowNum = " + lastRowNum);
            for(int i=0;i<=lastRowNum;i++){
                //根据行号获取行对象
                XSSFRow row = sheet.getRow(i);
                short firstCellNum = row.getFirstCellNum();
                System.out.println("firstCellNum = " + firstCellNum);
                short lastCellNum = row.getLastCellNum();
                System.out.println("lastCellNum = " + lastCellNum);
                for(short j=firstCellNum;j<lastCellNum;j++){
                    String value = row.getCell(j).getStringCellValue();
                    System.out.print(j+value+",");
                }
                System.out.println();
            }
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
