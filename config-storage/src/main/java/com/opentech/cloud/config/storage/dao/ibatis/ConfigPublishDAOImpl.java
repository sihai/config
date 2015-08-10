package com.opentech.cloud.config.storage.dao.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.opentech.cloud.config.client.publish.ConfigPublish;
import com.opentech.cloud.config.storage.dao.ConfigPublishDAO;

/**
 * 
 * @author sihai
 *
 */
@Repository
public class ConfigPublishDAOImpl extends AbstractDAO implements ConfigPublishDAO {

	@Override
	public void insert(ConfigPublish configPublish) {
		super.insert("configPublishDAO.insert", configPublish);
	}

	@Override
	public List<ConfigPublish> queryByConfigId(Long configId, int currentPage, int pageSize) {
		Map<String, Object> parameters = new HashMap<String, Object>(2);
		parameters.put("configId", configId);
		parameters.put("start", Integer.valueOf(pageSize * (currentPage -1)));
		parameters.put("pageSize", Integer.valueOf(pageSize));
		return super.query4Object("configPublishDAO.queryByConfigId", parameters);
	}

	@Override
	public int countByConfigId(Long configId) {
		return super.count("configPublishDAO.countByConfigId", configId);
	}

	@Override
	public void deleteByConfigId(Long configId) {
		super.delete("configPublishDAO.deleteByConfigId", configId);
	}

}
