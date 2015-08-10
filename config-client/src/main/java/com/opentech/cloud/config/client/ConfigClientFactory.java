package com.opentech.cloud.config.client;

/**
 * ConfigClient工厂
 * @author sihai
 *
 */
public interface ConfigClientFactory {
	
	/**
	 * 创建ConfigClient实例
	 * @return
	 */
	ConfigClient newInstance();
}
