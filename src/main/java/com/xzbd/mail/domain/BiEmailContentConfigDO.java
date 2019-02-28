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
public class BiEmailContentConfigDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//ID
	private Long id;
	//任务id
	private Long emailJobId;
	//内容id：文本、图片、excel等
	private Long contentId;
	//内容类型：1 文本；2、图片 3、附件 4 自动生成Excel ……
	private String type;
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
	 * 设置：任务id
	 */
	public void setEmailJobId(Long emailJobId) {
		this.emailJobId = emailJobId;
	}
	/**
	 * 获取：任务id
	 */
	public Long getEmailJobId() {
		return emailJobId;
	}
	/**
	 * 设置：内容id：文本、图片、excel等
	 */
	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}
	/**
	 * 获取：内容id：文本、图片、excel等
	 */
	public Long getContentId() {
		return contentId;
	}
	/**
	 * 设置：内容类型：1 文本；2、图片 3、附件 4 自动生成Excel ……
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：内容类型：1 文本；2、图片 3、附件 4 自动生成Excel ……
	 */
	public String getType() {
		return type;
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
