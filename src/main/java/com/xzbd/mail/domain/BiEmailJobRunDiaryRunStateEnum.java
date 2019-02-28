package com.xzbd.mail.domain;

public enum BiEmailJobRunDiaryRunStateEnum {
    RUNNING(0,"正在运行"),
    FAILED(1,"失败"),
    SUCCESS(2,"成功");
    private Integer value;
    private String name;

    BiEmailJobRunDiaryRunStateEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
