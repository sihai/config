package com.opentech.cloud.config.storage.dao;

import java.util.List;

import com.opentech.cloud.config.client.publish.ConfigPublish;

/**
 * 
 * @author sihai
 *
 */
public interface ConfigPublishDAO {
	
	/**
	 * insert 一条纪录
	 * @param configPublish
	 */
	void insert(ConfigPublish configPublish);
	
	/**
	 * 
	 * @param configId
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	List<ConfigPublish> queryByConfigId(Long configId, int currentPage, int pageSize);
	
	/**
	 * 
	 * @param configId
	 * @return
	 */
	int countByConfigId(Long configId);
	
	/**
	 * 
	 * @param configId
	 */
	void deleteByConfigId(Long configId);
}
