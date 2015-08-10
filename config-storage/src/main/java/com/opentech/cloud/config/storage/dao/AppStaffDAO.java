package com.opentech.cloud.config.storage.dao;

import java.util.List;

import com.opentech.cloud.config.client.app.AppStaff;

/**
 * 
 * @author sihai
 *
 */
public interface AppStaffDAO {
	
	/**
	 * insert 一条纪录
	 * @param appStaff
	 */
	void insert(AppStaff appStaff);
	
	/**
	 * 查询
	 * @param appId
	 * @param role
	 * @return
	 */
	List<AppStaff> queryByAppIdAndRole(Long appId, AppStaff.Role role);
	
	/**
	 * 更新
	 * @param appStaff
	 */
	void update(AppStaff appStaff);
	
	/**
	 * 
	 * @param id
	 */
	void deleteById(Long id);
}
