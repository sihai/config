package com.opentech.cloud.config.storage.dao;

import java.util.List;

import com.opentech.cloud.config.client.app.AppHost;

/**
 * 
 * @author sihai
 *
 */
public interface AppHostDAO {
	
	/**
	 * insert 一条纪录
	 * @param appHost
	 */
	void insert(AppHost appHost);
	
	/**
	 * 查询
	 * @param appId
	 * @param status
	 * @return
	 */
	List<AppHost> queryByAppIdAndStatus(Long appId, AppHost.Status status);
	
	/**
	 * 更新
	 * @param appHost
	 */
	void update(AppHost appHost);
	
	/**
	 * 
	 * @param id
	 */
	void deleteById(Long id);
}
