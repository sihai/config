package com.opentech.cloud.config.client.publish;

import java.io.Serializable;
import java.util.Date;

/**
 * 发布
 * @author sihai
 *
 */
public class ConfigPublish implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8460081017911535046L;

	/**
	 * 数据库表主键
	 */
	private Long id;
	
	/**
	 * Config.id 外键
	 */
	private Long configId;
	
	/**
	 * 配置项值
	 */
	private String value;
	
	/**
	 * 变更之前的版本
	 */
	private long version;
	
	/**
	 * 变更原因
	 */
	private String reason;
	
	/**
	 * 记录创建者
	 */
	private String creator;
	
	/**
	 * 修改者, 该字段不会被修改, 应该是和creator一样
	 */
	private String mender;
	
	/**
	 * 配置项创建时间
	 */
	private Date createTime;
	
	/**
	 * 配置项最后修改时间, 该字段不会被修改, 应该是和createTime一样
	 */
	private Date lastModifiedTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getConfigId() {
		return configId;
	}

	public void setConfigId(Long configId) {
		this.configId = configId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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
