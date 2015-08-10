package com.opentech.cloud.config.storage.dao;

import java.util.List;

import com.opentech.cloud.config.client.publish.ConfigPublishHost;

/**
 * 
 * @author sihai
 *
 */
public interface ConfigPublishHostDAO {
	
	/**
	 * insert 一条纪录
	 * @param configPublishHost
	 */
	void insert(ConfigPublishHost configPublishHost);
	
	/**
	 * 查询
	 * @param configId
	 * @return
	 */
	List<ConfigPublishHost> queryByConfigId(Long configId);
	
	/**
	 * 更新
	 * @param configPublishHost
	 */
	void update(ConfigPublishHost configPublishHost);
	
	/**
	 * 
	 * @param id
	 */
	void deleteById(Long id);
}
