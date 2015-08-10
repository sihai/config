package com.opentech.cloud.config.client.app;

import java.io.Serializable;

/**
 * 应用人员
 * @author sihai
 *
 */
public class AppStaff implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4886694788569852676L;

	/**
	 * 
	 * @author sihai
	 *
	 */
	public static enum Role {
		OWNER,
		DEVELOPER,
		TESTER,
		PE;
	}
	
	/**
	 * 数据库表主键id
	 */
	private Long id;
	
	/**
	 * 
	 */
	private Long appId;
	
	/**
	 * 
	 */
	private String name;
	
	/**
	 * 
	 */
	private Role role;

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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
