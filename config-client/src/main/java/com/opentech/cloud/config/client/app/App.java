package com.opentech.cloud.config.client.app;

import java.io.Serializable;
import java.util.Date;

/**
 * 应用
 * @author sihai
 *
 */
public class App implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3380725661826475880L;

	/**
	 * 数据库表主键id
	 */
	private Long id;

	/**
	 * 应用名
	 */
	private String name;
	
	/**
	 * 描述
	 */
	private String desc;
	
	/**
	 * 创建者
	 */
	private String creator;
	
	/**
	 * 最后修改者
	 */
	private String mender;
	
	/**
	 * 配置项创建时间
	 */
	private Date createTime;
	
	/**
	 * 配置项最后修改时间
	 */
	private Date lastModifiedTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getMender() {
		return mender;
	}

	public void setMender(String mender) {
		this.mender = mender;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
}