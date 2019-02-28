package com.xzbd.mail.utils.excelUtils;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class BiExcelUtils2007 {

    /**
     * 创建工作表
     *
     * @return
     */
    public static XSSFWorkbook createXSSFWorkbook() {
        XSSFWorkbook workbook = new XSSFWorkbook();
        return workbook;
    }

    /**
     * 创建制定名称的sheet
     *
     * @param workbook
     * @param sheetName
     * @return
     */
    public static XSSFSheet createSheet(XSSFWorkbook workbook, String sheetName) {
        XSSFSheet sheet = workbook.createSheet(sheetName);
        return sheet;
    }

    /**
     * 不指定sheet名称
     *
     * @param workbook
     * @return
     */
    public static XSSFSheet createSheet(XSSFWorkbook workbook) {
        XSSFSheet sheet = workbook.createSheet();
        return sheet;
    }

    /**
     * 将Excel文件保存到指定目录
     *
     * @param workbook
     * @param filePath
     * @throws IOException
     */
    public static void writeFileInTempDir(XSSFWorkbook workbook, String filePath) throws IOException {
        FileOutputStream fout = null;
        // 新建一输出流
        try {
            fout = new FileOutputStream(filePath);
            // 存盘
            workbook.write(fout);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(fout)) {
                // 结束关闭
                fout.flush();
                fout.close();
            }
        }

    }

    /**
     * 在指定行创建列名
     *
     * @param sheet
     * @param titleName 列名
     * @param rowNum    指定行
     * @return
     */
    public static XSSFSheet setColumTitle(XSSFSheet sheet, String[] titleName, int rowNum) {
        if (Objects.isNull(titleName))
            throw new RuntimeException("列名不能为空，您传入的列名为： " + titleName);
        int cellNum = titleName.length;
        XSSFRow row = createRow(sheet, rowNum, cellNum);
        for (int i = 0; i < cellNum; i++) {
            row.getCell(i).setCellValue(setCellValue(titleName[i]));
        }
        return sheet;
    }

    /**
     * 创建行
     *
     * @param appSheet     当前sheet
     * @param curRowNum    要创建的行的行号
     * @param cellNumInRow 要创建行的单元格
     * @return
     */
    public static XSSFRow createRow(XSSFSheet appSheet, int curRowNum, int cellNumInRow) {
        if (Objects.isNull(appSheet))
            return null;
        XSSFRow row = appSheet.createRow(curRowNum);
        for (int i = 0; i < cellNumInRow; i++) {
            row.createCell(i);
        }
        return row;
    }

    /**
     * 设置单元格数据----基本对象转字符串
     *
     * @param value
     * @return
     */
    public static String setCellValue(Object value) {
        return Objects.isNull(value) ? "" : value.toString();
    }

    /**
     * 设置单元格数据
     *
     * @param value
     * @return
     */
    public static void setCellValue(XSSFCell cell, Object value) {
        cell.setCellValue(Objects.isNull(value) ? "" : value.toString());
    }

    /**
     * 设置单元格数据----Integer数据处理
     *
     * @param value
     * @return
     */
    public static Integer setCellValue(Integer value) {
        return Objects.isNull(value) ? 0 : value;
    }



}
