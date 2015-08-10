package com.opentech.cloud.config.storage.dao.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.opentech.cloud.config.client.app.AppConfig;
import com.opentech.cloud.config.storage.dao.AppConfigDAO;

/**
 * 
 * @author sihai
 *
 */
@Repository
public class AppConfigDAOImpl extends AbstractDAO implements AppConfigDAO {

	@Override
	public void insert(AppConfig appConfig) {
		super.insert("appConfigDAO.insert", appConfig);
	}

	@Override
	public AppConfig queryByAppIdAndKey(Long appId, String key) {
		Map<String, Object> parameters = new HashMap<String, Object>(2);
		parameters.put("appId", appId);
		parameters.put("key", key);
		return super.query4Object("appConfigDAO.queryByAppIdAndKey", parameters);
	}

	@Override
	public AppConfig queryByAppIdAndKeyAndVersion(Long appId, String key, long version) {
		Map<String, Object> parameters = new HashMap<String, Object>(2);
		parameters.put("appId", appId);
		parameters.put("key", key);
		parameters.put("version", Long.valueOf(version));
		return super.query4Object("appConfigDAO.queryByAppIdAndKeyAndVersion", parameters);
	}

	@Override
	public List<AppConfig> queryByAppIdAndQ(Long appId, String q, int currentPage, int pageSize) {
		Map<String, Object> parameters = new HashMap<String, Object>(2);
		parameters.put("appId", appId);
		parameters.put("q", q);
		parameters.put("start", Integer.valueOf(pageSize * (currentPage -1)));
		parameters.put("pageSize", Integer.valueOf(pageSize));
		return super.query4Object("appConfigDAO.queryByAppIdAndKeyAndVersion", parameters);
	}

	@Override
	public int countByAppIdAndQ(Long appId, String q) {
		Map<String, Object> parameters = new HashMap<String, Object>(2);
		parameters.put("appId", appId);
		parameters.put("q", q);
		return super.query4Object("appConfigDAO.countByAppIdAndQ", parameters);
	}

	@Override
	public void update(AppConfig appConfig) {
		super.update("appConfigDAO.update", appConfig);
	}

	@Override
	public void deleteById(Long id) {
		super.delete("appConfigDAO.deleteById", id);
	}

}
