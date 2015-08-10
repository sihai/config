package com.opentech.cloud.config.storage.dao.ibatis;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * 
 * @author sihai
 *
 */
public abstract class AbstractDAO {
	
	@Resource
	private SqlMapClient sqlMapClient;
	
	/**
	 * 
	 * @param statement
	 * @param o
	 */
	protected final <T> void insert(String statement, T o) {
		try {
			this.sqlMapClient.insert(statement, o);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 
	 * @param statement
	 * @param id
	 * @return
	 */
	protected <T> T queryById(String statement, Long id) {
		try {
			return (T)this.sqlMapClient.queryForObject(statement, id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 
	 * @param statement
	 * @param parameter
	 * @return
	 */
	protected <T> T query4Object(String statement, Object parameter) {
		try {
			return (T)this.sqlMapClient.queryForObject(statement, parameter);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 
	 * @param statement
	 * @param parameter
	 * @return
	 */
	protected <T> List<T> query4List(String statement, Object parameter) {
		try {
			return (List<T>)this.sqlMapClient.queryForList(statement, parameter);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 
	 * @param statement
	 * @param parameter
	 * @return
	 */
	protected int count(String statement, Object parameter) {
		try {
			return (Integer)this.sqlMapClient.queryForObject(statement, parameter);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 
	 * @param statement
	 * @param parameter
	 */
	protected void update(String statement, Object parameter) {
		try {
			this.sqlMapClient.update(statement, parameter);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 
	 * @param statement
	 * @param parameter
	 */
	protected void delete(String statement, Object parameter) {
		try {
			this.sqlMapClient.delete(statement, parameter);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
