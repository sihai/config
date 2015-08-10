package com.opentech.cloud.config.storage.dao.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.opentech.cloud.config.client.app.AppStaff;
import com.opentech.cloud.config.client.app.AppStaff.Role;
import com.opentech.cloud.config.storage.dao.AppStaffDAO;

/**
 * 
 * @author sihai
 *
 */
@Repository
public class AppStaffDAOImpl extends AbstractDAO implements AppStaffDAO {

	@Override
	public void insert(AppStaff appStaff) {
		super.insert("appStaffDAO.insert", appStaff);
	}

	@Override
	public List<AppStaff> queryByAppIdAndRole(Long appId, Role role) {
		Map<String, Object> parameters = new HashMap<String, Object>(2);
		parameters.put("appId", appId);
		parameters.put("role", role);
		return super.query4List("appStaffDAO.queryByAppIdAndRole", parameters);
	}

	@Override
	public void update(AppStaff appStaff) {
		super.update("appStaffDAO.update", appStaff);
	}

	@Override
	public void deleteById(Long id) {
		super.delete("appStaffDAO.deleteById", id);
	}

}
