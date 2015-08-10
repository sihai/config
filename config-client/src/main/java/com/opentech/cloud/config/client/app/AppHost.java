package com.opentech.cloud.config.client.app;

import java.io.Serializable;

/**
 * 应用主机
 * @author sihai
 *
 */
public class AppHost implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6636115431176690025L;

	/**
	 * 
	 * @author sihai
	 *
	 */
	public static enum Status {
		OFFLINE,
		ONLINE;
	}
	
	/**
	 * 数据库表主键id
	 */
	private Long id;
	
	/**
	 * 所属应用
	 */
	private Long appId;

	/**
	 * 主名
	 */
	private String name;
	
	/**
	 * 主机ip
	 */
	private String ip;
	
	/**
	 * 机器配置
	 */
	private String config;
	
	/**
	 * 主机状态
	 */
	private Status status;
	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}