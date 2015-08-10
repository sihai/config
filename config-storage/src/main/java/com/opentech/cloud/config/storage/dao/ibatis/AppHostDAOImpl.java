package com.opentech.cloud.config.storage.dao.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.opentech.cloud.config.client.app.AppHost;
import com.opentech.cloud.config.storage.dao.AppHostDAO;

/**
 * 
 * @author sihai
 *
 */
@Repository
public class AppHostDAOImpl extends AbstractDAO implements AppHostDAO {

	@Override
	public void insert(AppHost appHost) {
		super.insert("appHostDAO.insert", appHost);
	}

	@Override
	public List<AppHost> queryByAppIdAndStatus(Long appId, AppHost.Status status) {
		Map<String, Object> parameters = new HashMap<String, Object>(2);
		parameters.put("appId", appId);
		parameters.put("status", status);
		return super.query4List("appHostDAO.queryByAppIdAndRole", parameters);
	}

	@Override
	public void update(AppHost appHost) {
		super.update("appHostDAO.update", appHost);
	}

	@Override
	public void deleteById(Long id) {
		super.delete("appHostDAO.deleteById", id);
	}
}
