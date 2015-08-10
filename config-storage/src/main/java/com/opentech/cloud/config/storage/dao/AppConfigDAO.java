package com.opentech.cloud.config.storage.dao;

import java.util.List;

import com.opentech.cloud.config.client.app.AppConfig;

/**
 * 
 * @author sihai
 *
 */
public interface AppConfigDAO {
	
	/**
	 * insert 一条纪录
	 * @param appConfig
	 */
	void insert(AppConfig appConfig);
	
	/**
	 * 
	 * @param appId
	 * @param key
	 * @return
	 */
	AppConfig queryByAppIdAndKey(Long appId, String key);
	
	/**
	 * 
	 * @param appId
	 * @param key
	 * @param version
	 * @return
	 */
	AppConfig queryByAppIdAndKeyAndVersion(Long appId, String key, long version);
	
	/**
	 * 
	 * @param appId
	 * @param q
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	List<AppConfig> queryByAppIdAndQ(Long appId, String q, int currentPage, int pageSize);
	
	/**
	 * 
	 * @param appId
	 * @param q
	 * @return
	 */
	int countByAppIdAndQ(Long appId, String q);
	
	/**
	 * 更新
	 * @param appConfig
	 */
	void update(AppConfig appConfig);
	
	/**
	 * 
	 * @param id
	 */
	void deleteById(Long id);
}
