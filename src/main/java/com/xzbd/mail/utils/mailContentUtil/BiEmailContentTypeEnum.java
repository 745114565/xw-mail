package com.xzbd.mail.utils.mailContentUtil;

/**
 * 邮件内容类型枚举
 *
 * @author xzbd
 * @email 745114565@qq.com
 * @date 2019-01-15 17:42:53
 */
public enum BiEmailContentTypeEnum {
    TEXT_CONTENT("文本","1","文本内容"),
    IMAGE_CONTENT("图片","2","图片内容"),
    FILE_CONTENT("附件","3","附件内容，所有可以作为附件的文件"),
    EXCEL_CONTEXT("excel","4","excel内容");

    private String name;
    private String value;
    private String descStr;

    BiEmailContentTypeEnum(String name, String value, String descStr) {
        this.name = name;
        this.value = value;
        this.descStr = descStr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescStr() {
        return descStr;
    }

    public void setDescStr(String descStr) {
        this.descStr = descStr;
    }}
