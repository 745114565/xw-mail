package com.xzbd.mail.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author xzbd
 * @email 745114565@qq.com
 * @date 2019-01-15 17:42:53
 */
public class BiEmailJobRunDiaryDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键ID
	private Long id;
	//任务ID
	private Long jobId;
	//任务名称
	private String jobName;
	//任务开始时间
	private Date startTime;
	//任务结束时间
	private Date endTime;
	//运行状态 0 正在执行，1 失败， 2 成功 
	private Integer runState;
	//0 程序异常 ，1 数据异常 2 程序异常
	private Integer failedType;
	//重试次数
	private Integer retryTimes;
	//0 未重试， 1已经重试
	private Integer retried;
	//失败信息。程序异常
	private String failedMsg;
	//原始收件人
	private String originTo;
	//原始抄送人
	private String originBc;
	//原始密送人
	private String originCc;
	//备用字段1
	private String str1;
	//备用字段2
	private String str2;
	//备用字段3
	private String str3;
	//备用字段4
	private String str4;
	//备用字段5
	private String str5;

	/**
	 * 设置：主键ID
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：主键ID
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：任务ID
	 */
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}
	/**
	 * 获取：任务ID
	 */
	public Long getJobId() {
		return jobId;
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
	 * 设置：任务开始时间
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * 获取：任务开始时间
	 */
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * 设置：任务结束时间
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * 获取：任务结束时间
	 */
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * 设置：运行状态 0 正在执行，1 失败， 2 成功 
	 */
	public void setRunState(Integer runState) {
		this.runState = runState;
	}
	/**
	 * 获取：运行状态 0 正在执行，1 失败， 2 成功 
	 */
	public Integer getRunState() {
		return runState;
	}
	/**
	 * 设置：0 程序异常 ，1 数据异常 2 程序异常
	 */
	public void setFailedType(Integer failedType) {
		this.failedType = failedType;
	}
	/**
	 * 获取：0 程序异常 ，1 数据异常 2 程序异常
	 */
	public Integer getFailedType() {
		return failedType;
	}
	/**
	 * 设置：重试次数
	 */
	public void setRetryTimes(Integer retryTimes) {
		this.retryTimes = retryTimes;
	}
	/**
	 * 获取：重试次数
	 */
	public Integer getRetryTimes() {
		return retryTimes;
	}
	/**
	 * 设置：0 未重试， 1已经重试
	 */
	public void setRetried(Integer retried) {
		this.retried = retried;
	}
	/**
	 * 获取：0 未重试， 1已经重试
	 */
	public Integer getRetried() {
		return retried;
	}
	/**
	 * 设置：失败信息。程序异常
	 */
	public void setFailedMsg(String failedMsg) {
		this.failedMsg = failedMsg;
	}
	/**
	 * 获取：失败信息。程序异常
	 */
	public String getFailedMsg() {
		return failedMsg;
	}
	/**
	 * 设置：原始收件人
	 */
	public void setOriginTo(String originTo) {
		this.originTo = originTo;
	}
	/**
	 * 获取：原始收件人
	 */
	public String getOriginTo() {
		return originTo;
	}
	/**
	 * 设置：原始抄送人
	 */
	public void setOriginBc(String originBc) {
		this.originBc = originBc;
	}
	/**
	 * 获取：原始抄送人
	 */
	public String getOriginBc() {
		return originBc;
	}
	/**
	 * 设置：原始密送人
	 */
	public void setOriginCc(String originCc) {
		this.originCc = originCc;
	}
	/**
	 * 获取：原始密送人
	 */
	public String getOriginCc() {
		return originCc;
	}
	/**
	 * 设置：备用字段1
	 */
	public void setStr1(String str1) {
		this.str1 = str1;
	}
	/**
	 * 获取：备用字段1
	 */
	public String getStr1() {
		return str1;
	}
	/**
	 * 设置：备用字段2
	 */
	public void setStr2(String str2) {
		this.str2 = str2;
	}
	/**
	 * 获取：备用字段2
	 */
	public String getStr2() {
		return str2;
	}
	/**
	 * 设置：备用字段3
	 */
	public void setStr3(String str3) {
		this.str3 = str3;
	}
	/**
	 * 获取：备用字段3
	 */
	public String getStr3() {
		return str3;
	}
	/**
	 * 设置：备用字段4
	 */
	public void setStr4(String str4) {
		this.str4 = str4;
	}
	/**
	 * 获取：备用字段4
	 */
	public String getStr4() {
		return str4;
	}
	/**
	 * 设置：备用字段5
	 */
	public void setStr5(String str5) {
		this.str5 = str5;
	}
	/**
	 * 获取：备用字段5
	 */
	public String getStr5() {
		return str5;
	}
}
