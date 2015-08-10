package com.opentech.cloud.config.client.app;

import java.io.Serializable;
import java.util.Date;

/**
 * 配置
 * @author sihai
 *
 */
public class AppConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6391440772504305149L;
	
	/**
	 * 
	 */
	public static final long DUMMY_VERSION = -1;
	
	/**
	 * 起始版本
	 */
	public static final long DEFAULT_VERSION = 1;
	
	/**
	 * 数据库表主键id
	 */
	private Long id;
	
	/**
	 * 所属应用
	 */
	private Long appId;

	/**
	 * 配置项key, 全局唯一, 如com.opentech.cloud.server.gateway.cache.memcache.server.address
	 */
	private String key;
	
	/**
	 * 配置项值, 任意格式字符串, 订阅者自己解析, 比如是properties格式, 或xml, 或json
	 */
	private String value;
	
	/**
	 * 配置项版本
	 */
	private long version;
	
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
	
	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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