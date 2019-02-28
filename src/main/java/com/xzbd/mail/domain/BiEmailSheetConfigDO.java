package com.xzbd.mail.domain;

import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.util.Date;



/**
 * 邮件Excel中sheet配置
 * 
 * @author xzbd
 * @email 745114565@qq.com
 * @date 2019-01-15 17:42:53
 */
public class BiEmailSheetConfigDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//ID
	private Long id;
	//sheet名
	private String sheetName;
	//sheet表头
	private String titles;
	//数据库列名 与sql查询返回的列名一致
	private String coloums;
	//sql 语句
	private String qSql;
	//数据源
	private String dataSource;
	//空数据处理方案： 1 发送空表, 2 终止发送,记录错误信息
	private Integer nodataDeal;
	//数据量是否巨大： 0 否、1 是
	private Integer dataRange;
	//是否删除：0 正常 ，1 已删除
	private Integer del;
	//创建时间
	private Date createTime;
	//创建人
	private Long createMan;
	//最后一次修改人
	private Long lastOptionMan;
	//最后一次修改时间
	private Date lastOptionTime;
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
	 * 设置：sheet名
	 */
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	/**
	 * 获取：sheet名
	 */
	public String getSheetName() {
		return sheetName;
	}
	/**
	 * 设置：sheet表头
	 */
	public void setTitles(String titles) {
		this.titles = titles;
	}
	/**
	 * 获取：sheet表头
	 */
	public String getTitles() {
		return titles;
	}
	/**
	 * 设置：数据库列名 与sql查询返回的列名一致
	 */
	public void setColoums(String coloums) {
		this.coloums = coloums;
	}
	/**
	 * 获取：数据库列名 与sql查询返回的列名一致
	 */
	public String getColoums() {
		return coloums;
	}
	/**
	 * 设置：sql 语句
	 */
	public void setQSql(String qSql) {
		this.qSql = qSql;
	}
	/**
	 * 获取：sql 语句
	 */
	public String getQSql() {
		return qSql;
	}
	/**
	 * 设置：数据源
	 */
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	/**
	 * 获取：数据源
	 */
	public String getDataSource() {
		return dataSource;
	}
	/**
	 * 设置：空数据处理方案： 1 发送空表, 2 终止发送,记录错误信息
	 */
	public void setNodataDeal(Integer nodataDeal) {
		this.nodataDeal = nodataDeal;
	}
	/**
	 * 获取：空数据处理方案： 1 发送空表, 2 终止发送,记录错误信息
	 */
	public Integer getNodataDeal() {
		return nodataDeal;
	}
	/**
	 * 设置：数据量是否巨大： 0 否、1 是
	 */
	public void setDataRange(Integer dataRange) {
		this.dataRange = dataRange;
	}
	/**
	 * 获取：数据量是否巨大： 0 否、1 是
	 */
	public Integer getDataRange() {
		return dataRange;
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
	 * 设置：最后一次修改人
	 */
	public void setLastOptionMan(Long lastOptionMan) {
		this.lastOptionMan = lastOptionMan;
	}
	/**
	 * 获取：最后一次修改人
	 */
	public Long getLastOptionMan() {
		return lastOptionMan;
	}
	/**
	 * 设置：最后一次修改时间
	 */
	public void setLastOptionTime(Date lastOptionTime) {
		this.lastOptionTime = lastOptionTime;
	}
	/**
	 * 获取：最后一次修改时间
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
