package com.opentech.cloud.config.storage.dao.ibatis;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.opentech.cloud.config.client.publish.ConfigPublishHost;
import com.opentech.cloud.config.storage.dao.ConfigPublishHostDAO;

/**
 * 
 * @author sihai
 *
 */
@Repository
public class ConfigPublishHostDAOImpl extends AbstractDAO implements ConfigPublishHostDAO {

	@Override
	public void insert(ConfigPublishHost configPublishHost) {
		super.insert("configPublishHostDAO.insert", configPublishHost);
	}

	@Override
	public List<ConfigPublishHost> queryByConfigId(Long configId) {
		return super.query4List("configPublishHostDAO.queryByConfigId", configId);
	}

	@Override
	public void update(ConfigPublishHost configPublishHost) {
		super.update("configPublishHostDAO.update", configPublishHost);
	}

	@Override
	public void deleteById(Long id) {
		super.delete("configPublishHostDAO.deleteById", id);
	}

}
