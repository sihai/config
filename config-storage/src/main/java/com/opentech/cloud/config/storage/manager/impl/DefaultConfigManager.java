package com.opentech.cloud.config.storage.manager.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.opentech.cloud.config.client.app.AppConfig;
import com.opentech.cloud.config.client.exception.DataValidateException;
import com.opentech.cloud.config.client.publish.ConfigPublish;
import com.opentech.cloud.config.storage.manager.ConfigManager;

/**
 * 
 * @author sihai
 *
 */
@Component
public class DefaultConfigManager implements ConfigManager {

	@Override
	public void store(AppConfig config, String reason)
			throws DataValidateException {
		// TODO Auto-generated method stub

	}

	@Override
	public AppConfig get(Long appId, String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AppConfig get(Long appId, String key, long version) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AppConfig> get(Long appId, String[] keys, long[] versions) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AppConfig> query(Long appId, String q, int currentPage,
			int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int count(Long appId, String q) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void update(AppConfig config, String reason)
			throws DataValidateException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Long appId, String key) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<ConfigPublish> queryConfigPublish(Long configId,
			int currentPage, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int countConfigPublish(Long configId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteConfigPublish(Long configId) {
		// TODO Auto-generated method stub

	}

}
