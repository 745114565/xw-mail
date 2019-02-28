package com.xzbd.mail.utils.mailContentUtil;

import com.xzbd.mail.utils.excelUtils.ExcelProperty;

public class ExcelEmailContentItem extends AbstractEmailContentItem {
    private ExcelProperty excel;

    public ExcelProperty getExcel() {
        return excel;
    }

    public void setExcel(ExcelProperty excel) {
        this.excel = excel;
    }
}
