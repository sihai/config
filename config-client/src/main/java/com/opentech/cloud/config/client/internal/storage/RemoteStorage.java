package com.opentech.cloud.config.client.internal.storage;

import com.opentech.cloud.config.client.app.AppConfig;
import com.opentech.cloud.config.client.exception.ConfigException;

/**
 * 
 * @author sihai
 *
 */
public interface RemoteStorage extends Storage {

	/**
	 * 批量查询
	 * @param keys
	 * @return
	 * @throws ConfigException
	 */
	AppConfig[] get(String[] keys) throws ConfigException;
	
	/**
	 * 查询
	 * @param key
	 * @param localVersion
	 * @return
	 * @throws ConfigException
	 */
	AppConfig get(String key, long localVersion) throws ConfigException;
	
	/**
	 * 批量查询
	 * @param keys
	 * @param localVersions
	 * @return
	 * @throws ConfigException
	 */
	AppConfig[] get(String[] keys, long[] localVersions) throws ConfigException;
}
