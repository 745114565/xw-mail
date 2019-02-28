package com.xzbd.mail.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 邮件内容配置
 * 
 * @author xzbd
 * @email 745114565@qq.com
 * @date 2019-01-15 17:42:53
 */
public class BiEmailExcelSheetConfigDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//ID
	private Long id;
	//Excel id
	private Long excelId;
	//sheet id
	private Long sheetId;
	//是否删除：0 正常 ，1 已删除
	private Integer del;
	//创建时间
	private Date createTime;
	//创建人
	private Long createMan;
	//备用字段 1
	private String memo1;
	//备用字段 2
	private String memo2;
	//备用字段 3
	private String memo3;

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
	 * 设置：Excel id
	 */
	public void setExcelId(Long excelId) {
		this.excelId = excelId;
	}
	/**
	 * 获取：Excel id
	 */
	public Long getExcelId() {
		return excelId;
	}
	/**
	 * 设置：sheet id
	 */
	public void setSheetId(Long sheetId) {
		this.sheetId = sheetId;
	}
	/**
	 * 获取：sheet id
	 */
	public Long getSheetId() {
		return sheetId;
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
}
