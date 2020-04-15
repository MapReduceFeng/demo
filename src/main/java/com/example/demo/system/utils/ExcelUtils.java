package com.example.demo.system.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;

public class ExcelUtils {
    public static Workbook createExcel(String title, String httpHeaders, List list) {
        Workbook workbook = new XSSFWorkbook();//声明一个工作簿
        Sheet sheet = workbook.createSheet("sheet");//创建一个Excel表单,参数为sheet的名字

        Font font = workbook.createFont();//创建字体
        font.setColor(IndexedColors.BLACK.getIndex());
        font.setFontHeightInPoints((short) 16);
        font.setFontName("Arial");
        font.setBold(true);
        CellStyle cellStyle = workbook.createCellStyle();//创建样式 标题
        cellStyle.setFont(font);

        Font font1 = workbook.createFont();
        font1.setColor(IndexedColors.WHITE.getIndex());
        font1.setFontHeightInPoints((short) 10);
        font1.setFontName("Arial");
        font1.setBold(true);
        CellStyle cellStyle1 = workbook.createCellStyle();
        cellStyle1.setFont(font1);
        cellStyle1.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());//设置前景色
        cellStyle1.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Font font2 = workbook.createFont();
        font2.setFontHeightInPoints((short) 10);
        font.setFontName("Arial");
        CellStyle cellStyle2 = workbook.createCellStyle();
        cellStyle2.setFont(font2);

        cellStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
        cellStyle.setVerticalAlignment(VerticalAlignment.TOP);//垂直居中
        cellStyle1.setAlignment(HorizontalAlignment.LEFT);//水平居中
//            cellStyle1.setVerticalAlignment(VerticalAlignment.TOP);//垂直居中
        cellStyle2.setAlignment(HorizontalAlignment.LEFT);//水平居中
//            cellStyle2.setVerticalAlignment(VerticalAlignment.TOP);//垂直居中

//            cellStyle1.setBorderBottom(BorderStyle.MEDIUM);//边框
//            cellStyle1.setBorderLeft(BorderStyle.MEDIUM);//边框
//            cellStyle1.setBorderRight(BorderStyle.MEDIUM);//边框
//            cellStyle2.setBorderBottom(BorderStyle.MEDIUM);//边框
//            cellStyle2.setBorderLeft(BorderStyle.MEDIUM);//边框
//            cellStyle2.setBorderRight(BorderStyle.MEDIUM);//边框
        String[] httpHeader = httpHeaders.split(",");
        int httpHeaderLength = httpHeader.length;
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, httpHeaderLength));//合并

        Row row0 = sheet.createRow(0);//创建一行
        row0.setHeight((short) 600);//设置行高

        Cell cell0 = row0.createCell(0); //在第一行中创建第一个单元格，并设置数据
        cell0.setCellValue(title);//设置标题
        cell0.setCellStyle(cellStyle);

        Row row1 = sheet.createRow(1);
        for (int i = 0; i <= httpHeaderLength; i++) {//创建一行列字段
            Cell cell = row1.createCell(i);
            cell.setCellStyle(cellStyle1);
            cell.setCellValue(i);
        }
        //内容
        int listSize = list.size();
        for (int i = 2; i < listSize+2; i++) {// 行
            Row row = sheet.createRow(i);
            for (int j = 0; j < 15; j++) {//列
                Cell cell = row.createCell(j);
                cell.setCellStyle(cellStyle2);
                cell.setCellValue(j);
            }
        }
        return workbook;
    }
}
