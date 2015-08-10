package com.opentech.cloud.config.client.publish;

import java.io.Serializable;

/**
 * 发布机器
 * @author sihai
 *
 */
public class ConfigPublishHost implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7815369491716235776L;

	/**
	 * 状态
	 * @author sihai
	 *
	 */
	public static enum Status {
		WAITING,
		PULLED,
		SUCCEED,
		FAILED;
	}
	
	/**
	 * 数据库表主键
	 */
	private Long id;
	
	/**
	 * 发布id
	 */
	private Long publishId;
	
	/**
	 * 
	 */
	private Long hostId;
	
	/**
	 * 
	 */
	private Status status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPublishId() {
		return publishId;
	}

	public void setPublishId(Long publishId) {
		this.publishId = publishId;
	}

	public Long getHostId() {
		return hostId;
	}

	public void setHostId(Long hostId) {
		this.hostId = hostId;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}