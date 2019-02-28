package com.xzbd.mail.utils.excelUtils;

import java.util.List;
import java.util.Map;

public class ExcelSheetProperty {
    /**
     * Sheet 名  默认为 Sheet
     */
    private String sheetName = "Sheet";

    /**
     * Sheet 表头
     */
    private String[] sheetTitles;
    /**
     * Sheet 展示的列字段 对应数据库列名
     */
    private List<String>  colums;

    /**
     * 表头所在行行号
     */
    private Integer sheetTitlesRowNum = 0;

    /**
     * sheet 数据（基于<数据库字段名,该字段对应的value>）
     */
    private List<Map<String,String>> sheetData;

    /**
     * sheet中表数据起始行
     */
    private Integer sheetDataStartRowNum;

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String[] getSheetTitles() {
        if(sheetTitles!=null && sheetTitles.length > 0)
            for(int i=0;i<sheetTitles.length;i++){
                sheetTitles[i] = sheetTitles[i] ==null ? "" : sheetTitles[i].trim();
            }
        return sheetTitles;
    }

    public void setSheetTitles(String[] sheetTitles) {
        if(sheetTitles!=null && sheetTitles.length > 0)
            for(int i=0;i<sheetTitles.length;i++){
                sheetTitles[i] = sheetTitles[i] ==null ? "" : sheetTitles[i].trim();
            }
        this.sheetTitles = sheetTitles;
    }

    public List<String> getColums() {
        return colums;
    }

    public void setColums(List<String> colums) {
        this.colums = colums;
    }

    public Integer getSheetTitlesRowNum() {
        return sheetTitlesRowNum;
    }

    public void setSheetTitlesRowNum(Integer sheetTitlesRowNum) {
        this.sheetTitlesRowNum = sheetTitlesRowNum;
    }

    public List<Map<String, String>> getSheetData() {
        return sheetData;
    }

    public void setSheetData(List<Map<String, String>> sheetData) {
        this.sheetData = sheetData;
    }

    public Integer getSheetDataStartRowNum() {
        return sheetDataStartRowNum;
    }

    public void setSheetDataStartRowNum(Integer sheetDataStartRowNum) {
        this.sheetDataStartRowNum = sheetDataStartRowNum;
    }
}
