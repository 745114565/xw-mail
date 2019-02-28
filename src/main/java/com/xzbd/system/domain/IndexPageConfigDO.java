package com.xzbd.system.domain;

import java.io.Serializable;

/**
 *
 * @author xzbd
 * @email 745114565@qq.com
 * @date 2019-01-24 11:19:43
 */
public class IndexPageConfigDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键(userId)
	private Long id;
	//首页路径
	private String indexPagePath;
	//
	private String memo;
	//
	private String memo1;
	//
	private String memo2;
	//
	private String memo3;
	//
	private String memo4;

	/**
	 * 设置：主键(userId)
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：主键(userId)
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：首页路径
	 */
	public void setIndexPagePath(String indexPagePath) {
		this.indexPagePath = indexPagePath;
	}
	/**
	 * 获取：首页路径
	 */
	public String getIndexPagePath() {
		return indexPagePath;
	}
	/**
	 * 设置：
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}
	/**
	 * 获取：
	 */
	public String getMemo() {
		return memo;
	}
	/**
	 * 设置：
	 */
	public void setMemo1(String memo1) {
		this.memo1 = memo1;
	}
	/**
	 * 获取：
	 */
	public String getMemo1() {
		return memo1;
	}
	/**
	 * 设置：
	 */
	public void setMemo2(String memo2) {
		this.memo2 = memo2;
	}
	/**
	 * 获取：
	 */
	public String getMemo2() {
		return memo2;
	}
	/**
	 * 设置：
	 */
	public void setMemo3(String memo3) {
		this.memo3 = memo3;
	}
	/**
	 * 获取：
	 */
	public String getMemo3() {
		return memo3;
	}
	/**
	 * 设置：
	 */
	public void setMemo4(String memo4) {
		this.memo4 = memo4;
	}
	/**
	 * 获取：
	 */
	public String getMemo4() {
		return memo4;
	}
}
