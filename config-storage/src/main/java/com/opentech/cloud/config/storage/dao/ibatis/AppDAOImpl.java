package com.opentech.cloud.config.storage.dao.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.opentech.cloud.config.client.app.App;
import com.opentech.cloud.config.storage.dao.AppDAO;

/**
 * 
 * @author sihai
 *
 */
@Repository
public class AppDAOImpl extends AbstractDAO implements AppDAO {

	@Override
	public void insert(App app) {
		super.insert("appDAO.insert", app);
	}

	@Override
	public App queryByName(String name) {
		return super.query4Object("appDAO.queryByName", name);
	}

	@Override
	public List<App> queryByQ(String q, int currentPage, int pageSize) {
		Map<String, Object> parameters = new HashMap<String, Object>(3);
		parameters.put("q", q);
		parameters.put("start", Integer.valueOf(pageSize * (currentPage -1)));
		parameters.put("pageSize", Integer.valueOf(pageSize));
		return super.query4List("appDAO.queryByQ", parameters);
	}

	@Override
	public int countByQ(String q) {
		return super.count("appDAO.countByName", q);
	}

	@Override
	public void update(App app) {
		super.update("appDAO.update", app);
	}

	@Override
	public void deleteById(Long id) {
		super.delete("appDAO.delete", id);
	}

}
