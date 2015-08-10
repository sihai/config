package com.opentech.cloud.config.storage.dao;

import java.util.List;

import com.opentech.cloud.config.client.app.App;

/**
 * 
 * @author sihai
 *
 */
public interface AppDAO {
	
	/**
	 * insert 一条纪录
	 * @param app
	 */
	void insert(App app);
	
	/**
	 * 按name精确查询, name唯一索引
	 * @param name
	 * @return
	 */
	App queryByName(String name);
	
	/**
	 * 按name模糊查询
	 * @param q
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	List<App> queryByQ(String q, int currentPage, int pageSize);
	
	/**
	 * 按name模糊查询总数
	 * @param q
	 * @return
	 */
	int countByQ(String q);
	
	/**
	 * 更新
	 * @param app
	 */
	void update(App app);
	
	/**
	 * 
	 * @param id
	 */
	void deleteById(Long id);
}
