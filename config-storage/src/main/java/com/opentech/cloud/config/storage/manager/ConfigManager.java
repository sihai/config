package com.opentech.cloud.config.storage.manager;

import java.util.List;

import com.opentech.cloud.config.client.app.AppConfig;
import com.opentech.cloud.config.client.exception.DataValidateException;
import com.opentech.cloud.config.client.publish.ConfigPublish;

/**
 * 数据存储
 * @author sihai
 *
 */
public interface ConfigManager {

	/**
	 * 存储
	 * @param config
	 * @param reason
	 * @throws DataValidateException
	 */
	void store(AppConfig config, String reason) throws DataValidateException;
	
	/**
	 * 按key获取
	 * @param appId
	 * @param key
	 * @return
	 */
	AppConfig get(Long appId, String key);
	
	/**
	 * 按key获取, 且 version > version
	 * @param appId
	 * @param key
	 * @param version
	 * @return
	 */
	AppConfig get(Long appId, String key, long version);
	
	/**
	 * @param appId
	 * @param keys
	 * @param versions
	 * @return
	 */
	List<AppConfig> get(Long appId, String[] keys, long[] versions);
	
	/**
	 * 模糊查询
	 * @param appId
	 * @param q
	 * @param currentPage
	 * @param pageSize
	 */
	List<AppConfig> query(Long appId, String q, int currentPage, int pageSize);
	
	/**
	 * 模糊查询总数
	 * @param q
	 * @return
	 */
	int count(Long appId, String q);
	
	/**
	 * 更新
	 * @param config
	 * @param reason
	 * @throws DataValidateException
	 */
	void update(AppConfig config, String reason) throws DataValidateException;
	
	/**
	 * 删除
	 * @param key
	 */
	void delete(Long appId, String key);
	
	//=========================================================================
	//
	//=========================================================================
	
	/**
	 * 分页查询配置项的发布记录
	 * @param configId
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	List<ConfigPublish> queryConfigPublish(Long configId, int currentPage, int pageSize);
	
	/**
	 * 查询配置项的变更发布总数
	 * @param configId
	 * @return
	 */
	int countConfigPublish(Long configId);
	
	/**
	 * 删除配置项的变发布录
	 * @param configId
	 */
	void deleteConfigPublish(Long configId);
}