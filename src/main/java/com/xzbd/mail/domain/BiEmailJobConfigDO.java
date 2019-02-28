package com.xzbd.mail.domain;

import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 邮件作业任务配置
 * 
 * @author xzbd
 * @email 745114565@qq.com
 * @date 2019-01-15 17:42:53
 */
public class BiEmailJobConfigDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//ID
	private Long id;
	//收件人邮箱地址
	private String sendTo;
	//抄送人邮箱地址
	private String cc;
	//密送人邮箱地址
	private String bcc;
	//邮件主题
	private String subject;
	//任务描述
	private String jobDesc;
	//邮箱内容ID
	private String emailContentId;
	//发送周期： cron 表达式
	private String cronExpression;
	//邮箱发送者ID
	private Long emailSenderId;
	//是否删除：0 正常 ，1 已删除
	private Integer del;
	//任务名称
	private String jobName;
	//任务组
	private String jobGroup;
	//任务执行时调用的类：包名+类名
	private String jobBeanClass;
	//方法名
	private String methodName;
	//任务状态：0 未启动 ，1 已启动
	private Integer jobStatus;
	//任务是否有状态：0 没有 ，1 有
	private Integer isconcurrent;
	//创建时间
	private Date createTime;
	//创建人
	private Long createMan;
	//是否是单表大数据邮件
	private String memo1;
	//备用字段 2
	private String memo2;
	//备用字段 3
	private String memo3;
	//最后一次操作人
	private Long lastOptionMan;
	//最后一次修改时间
	private Date lastOptionTime;

	private List<Long> textEmailContentIds;

	private List<Long> excelEmailContentIds;
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
	 * 设置：收件人邮箱地址
	 */
	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}
	/**
	 * 获取：收件人邮箱地址
	 */
	public String getSendTo() {
		return sendTo;
	}
	/**
	 * 设置：抄送人邮箱地址
	 */
	public void setCc(String cc) {
		this.cc = cc;
	}
	/**
	 * 获取：抄送人邮箱地址
	 */
	public String getCc() {
		return cc;
	}
	/**
	 * 设置：密送人邮箱地址
	 */
	public void setBcc(String bcc) {
		this.bcc = bcc;
	}
	/**
	 * 获取：密送人邮箱地址
	 */
	public String getBcc() {
		return bcc;
	}
	/**
	 * 设置：邮件主题
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * 获取：邮件主题
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * 设置：任务描述
	 */
	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
	}
	/**
	 * 获取：任务描述
	 */
	public String getJobDesc() {
		return jobDesc;
	}
	/**
	 * 设置：邮箱内容ID
	 */
	public void setEmailContentId(String emailContentId) {
		this.emailContentId = emailContentId;
	}
	/**
	 * 获取：邮箱内容ID
	 */
	public String getEmailContentId() {
		return emailContentId;
	}
	/**
	 * 设置：发送周期： cron 表达式
	 */
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	/**
	 * 获取：发送周期： cron 表达式
	 */
	public String getCronExpression() {
		return cronExpression;
	}
	/**
	 * 设置：邮箱发送者ID
	 */
	public void setEmailSenderId(Long emailSenderId) {
		this.emailSenderId = emailSenderId;
	}
	/**
	 * 获取：邮箱发送者ID
	 */
	public Long getEmailSenderId() {
		return emailSenderId;
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
	 * 设置：任务名称
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	/**
	 * 获取：任务名称
	 */
	public String getJobName() {
		return jobName;
	}
	/**
	 * 设置：任务组
	 */
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}
	/**
	 * 获取：任务组
	 */
	public String getJobGroup() {
		return jobGroup;
	}
	/**
	 * 设置：任务执行时调用的类：包名+类名
	 */
	public void setJobBeanClass(String jobBeanClass) {
		this.jobBeanClass = jobBeanClass;
	}
	/**
	 * 获取：任务执行时调用的类：包名+类名
	 */
	public String getJobBeanClass() {
		return jobBeanClass;
	}
	/**
	 * 设置：方法名
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	/**
	 * 获取：方法名
	 */
	public String getMethodName() {
		return methodName;
	}
	/**
	 * 设置：任务状态：0 未启动 ，1 已启动
	 */
	public void setJobStatus(Integer jobStatus) {
		this.jobStatus = jobStatus;
	}
	/**
	 * 获取：任务状态：0 未启动 ，1 已启动
	 */
	public Integer getJobStatus() {
		return jobStatus;
	}
	/**
	 * 设置：任务是否有状态：0 没有 ，1 有
	 */
	public void setIsconcurrent(Integer isconcurrent) {
		this.isconcurrent = isconcurrent;
	}
	/**
	 * 获取：任务是否有状态：0 没有 ，1 有
	 */
	public Integer getIsconcurrent() {
		return isconcurrent;
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
	 * 设置：是否是单表大数据邮件
	 */
	public void setMemo1(String memo1) {
		this.memo1 = memo1;
	}
	/**
	 * 获取：是否是单表大数据邮件
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

	public List<Long> getTextEmailContentIds() {
		return textEmailContentIds;
	}

	public void setTextEmailContentIds(List<Long> textEmailContentIds) {
		this.textEmailContentIds = textEmailContentIds;
	}

	public List<Long> getExcelEmailContentIds() {
		return excelEmailContentIds;
	}

	public void setExcelEmailContentIds(List<Long> excelEmailContentIds) {
		this.excelEmailContentIds = excelEmailContentIds;
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
