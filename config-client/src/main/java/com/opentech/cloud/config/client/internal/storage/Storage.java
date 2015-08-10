package com.opentech.cloud.config.client.internal.storage;

import com.opentech.cloud.config.client.app.AppConfig;
import com.opentech.cloud.config.client.exception.ConfigException;

/**
 * 
 * @author sihai
 *
 */
public interface Storage {
	
	/**
	 * 存储
	 * @param config
	 * @param reason
	 * @throws ConfigException
	 */
	void store(AppConfig config, String reason) throws ConfigException;
	
	/**
	 * 查询
	 * @param key
	 * @return
	 * @throws ConfigException
	 */
	AppConfig get(String key) throws ConfigException;
}