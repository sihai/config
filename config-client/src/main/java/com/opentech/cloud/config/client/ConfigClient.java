package com.opentech.cloud.config.client;

import com.opentech.cloud.config.client.app.AppConfig;
import com.opentech.cloud.config.client.exception.ConfigException;

/**
 * 配置项接口
 * @author sihai
 *
 */
public interface ConfigClient {

	/**
	 * 配置项监听器
	 * @author sihai
	 *
	 */
	interface Listener {
		
		/**
		 * 配置项目被修改
		 * @param value
		 */
		void onChanged(String value);
	}
	
	/**
	 * 发布配置项
	 * @param config
	 * @param reason
	 * @throws ConfigException
	 */
	void publish(AppConfig config, String reason) throws ConfigException;
	
	/**
	 * 查询配置项
	 * @param key
	 * @return
	 * @throws ConfigException
	 */
	AppConfig get(String key) throws ConfigException;
	
	/**
	 * 查询配置项并注册监听器
	 * @param key
	 * @param listener
	 * @return
	 * @throws ConfigException
	 */
	AppConfig get(String key, Listener listener) throws ConfigException;
	
	/**
	 * 查询配置项值
	 * @param key
	 * @return
	 * @throws ConfigException
	 */
	String getValue(String key) throws ConfigException;
	
	/**
	 * 查询配置项值并注册监听器
	 * @param key
	 * @param listener
	 * @return
	 * @throws ConfigException
	 */
	String getValue(String key, Listener listener) throws ConfigException;
	
	/**
	 * 释放资源
	 */
	void release();
}