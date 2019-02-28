package com.xzbd.mail.utils.excelUtils;

import java.util.List;

public class ExcelProperty {
    /**
     * excel 名称
     */
    private String excelName;
    /**
     * Excel里的sheet
     */
    private List<ExcelSheetProperty> sheets;


    public String getExcelName() {
        return excelName;
    }

    public void setExcelName(String excelName) {
        this.excelName = excelName;
    }

    public List<ExcelSheetProperty> getSheets() {
        return sheets;
    }

    public void setSheets(List<ExcelSheetProperty> sheets) {
        this.sheets = sheets;
    }
}
