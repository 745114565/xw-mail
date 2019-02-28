package com.xzbd.mail.domain;

import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 邮件Excel内容配置
 *
 * @author xzbd
 * @email 745114565@qq.com
 * @date 2019-01-15 17:42:53
 */
public class BiEmailExcelConfigDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //ID
    private Long id;
    //Excel文件名
    private String name;
    //文件描述
    private String fileDesc;
    //是否删除：0 正常 ，1 已删除
    private Integer del;
    //创建时间
    private Date createTime;
    //创建人
    private Long createMan;
    //最后一次操作人
    private Long lastOptionMan;
    //最后一次操作时间
    private Date lastOptionTime;
    //备用字段 1
    private String memo1;
    //备用字段 2
    private String memo2;
    //备用字段 3
    private String memo3;

    // sheets
    private List<Long> sheets;

    /**
     * 设置：ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取：ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置：Excel文件名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取：Excel文件名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置：文件描述
     */
    public void setFileDesc(String fileDesc) {
        this.fileDesc = fileDesc;
    }

    /**
     * 获取：文件描述
     */
    public String getFileDesc() {
        return fileDesc;
    }

    /**
     * 设置：是否删除：0 正常 ，1 已删除
     */
    public void setDel(Integer del) {
        this.del = del;
    }

    /**
     * 获取：是否删除：0 正常 ，1 已删除
     */
    public Integer getDel() {
        return del;
    }

    /**
     * 设置：创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取：创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置：创建人
     */
    public void setCreateMan(Long createMan) {
        this.createMan = createMan;
    }

    /**
     * 获取：创建人
     */
    public Long getCreateMan() {
        return createMan;
    }

    /**
     * 设置：最后一次操作人
     */
    public void setLastOptionMan(Long lastOptionMan) {
        this.lastOptionMan = lastOptionMan;
    }

    /**
     * 获取：最后一次操作人
     */
    public Long getLastOptionMan() {
        return lastOptionMan;
    }

    /**
     * 设置：最后一次操作时间
     */
    public void setLastOptionTime(Date lastOptionTime) {
        this.lastOptionTime = lastOptionTime;
    }

    /**
     * 获取：最后一次操作时间
     */
    public Date getLastOptionTime() {
        return lastOptionTime;
    }

    /**
     * 设置：备用字段 1
     */
    public void setMemo1(String memo1) {
        this.memo1 = memo1;
    }

    /**
     * 获取：备用字段 1
     */
    public String getMemo1() {
        return memo1;
    }

    /**
     * 设置：备用字段 2
     */
    public void setMemo2(String memo2) {
        this.memo2 = memo2;
    }

    /**
     * 获取：备用字段 2
     */
    public String getMemo2() {
        return memo2;
    }

    /**
     * 设置：备用字段 3
     */
    public void setMemo3(String memo3) {
        this.memo3 = memo3;
    }

    /**
     * 获取：备用字段 3
     */
    public String getMemo3() {
        return memo3;
    }


    public List<Long> getSheets() {
        return sheets;
    }

    public void setSheets(List<Long> sheets) {
        this.sheets = sheets;
    }
    @Transient
    private String createManStr;
    @Transient
    private String lastOptionManStr;

    public String getCreateManStr() {
        return createManStr;
    }

    public void setCreateManStr(String createManStr) {
        this.createManStr = createManStr;
    }

    public String getLastOptionManStr() {
        return lastOptionManStr;
    }

    public void setLastOptionManStr(String lastOptionManStr) {
        this.lastOptionManStr = lastOptionManStr;
    }
}
