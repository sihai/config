package com.opentech.cloud.config.client.internal.storage;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.opentech.cloud.config.client.app.AppConfig;
import com.opentech.cloud.config.client.exception.ConfigException;
import com.opentech.cloud.config.client.result.ErrorCode;
import com.opentech.cloud.config.client.result.Result;
import com.opentech.cloud.config.utils.network.HttpClientUtils;

/**
 * 基于HTTP实现的远程存储
 * @author sihai
 *
 */
public class HttpRemoteStorage implements RemoteStorage {
	
	private static final Log logger = LogFactory.getLog(HttpRemoteStorage.class);
	
	/**
	 * http请求参数data
	 */
	private static final String PARAMETER_DATA = "data";
	
	/**
	 * http请求参数keys
	 */
	private static final String PARAMETER_KEYS = "keys";
	
	/**
	 * http请求参数localVersions
	 */
	private static final String PARAMETER_LOCAL_VERSIONS = "localVersions";
	
	/**
	 * 存储url
	 */
	private URI storeURL;
	
	/**
	 * 查询url
	 */
	private URI getURL;
	
	/**
	 * 
	 * @param storeURL
	 * @param getURL
	 */
	public HttpRemoteStorage(URI storeURL, URI getURL) {
		this.storeURL = storeURL;
		this.getURL = getURL;
	}

	@Override
	public AppConfig get(String key) throws ConfigException {
		return this.get(key, AppConfig.DUMMY_VERSION);
	}

	@Override
	public AppConfig get(String key, long localVersion) throws ConfigException {
		AppConfig[] configs = this.get(new String[]{key}, new long[]{localVersion});
		return (null == configs || 0 == configs.length) ? null : configs[0];
	}

	@Override
	public AppConfig[] get(String[] keys, long[] localVersions) throws ConfigException {
		try {
			Map<String, String> parameters = new HashMap<String, String>();
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("keys", keys);
			data.put("localVersions", localVersions);
			parameters.put(PARAMETER_DATA, JSON.toJSONString(data));
			String response = HttpClientUtils.sync_post(this.getURL, parameters);
			Result result = JSON.toJavaObject(JSON.parseObject(response), Result.class);
			if(!result.isSucceed()) {
				throw new ConfigException(result.getErrorCode(), result.getErrorMsg());
			}
			
			if(null == result.getData()) {
				return new AppConfig[0];
			}
			
			int i = 0;
			JSONArray array = (JSONArray)result.getData();
			AppConfig[] configs = new AppConfig[array.size()];
			for(Object jo : array) {
				configs[i++] = JSON.toJavaObject((JSONObject)jo, AppConfig.class);
			}
			return configs;
		} catch (IOException e) {
			logger.error(ErrorCode.NETWORK_ERROR.getCode(), e);
			throw new ConfigException(ErrorCode.NETWORK_ERROR.getCode(), ErrorCode.NETWORK_ERROR.getMsg());
		}
	}

	@Override
	public void store(AppConfig config, String reason) throws ConfigException {
		try {
			Map<String, String> parameters = new HashMap<String, String>();
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("config", config);
			data.put("reason", reason);
			parameters.put(PARAMETER_DATA, JSON.toJSONString(data));
			Result result = JSON.toJavaObject(JSON.parseObject(HttpClientUtils.sync_post(this.storeURL, parameters)), Result.class);
			if(!result.isSucceed()) {
				throw new ConfigException(result.getErrorCode(), result.getErrorMsg());
			}
		} catch (IOException e) {
			logger.error(ErrorCode.NETWORK_ERROR.getCode(), e);
			throw new ConfigException(ErrorCode.NETWORK_ERROR.getCode(), ErrorCode.NETWORK_ERROR.getMsg());
		}
	}

	@Override
	public AppConfig[] get(String[] keys) throws ConfigException {
		long[] localVersions = new long[keys.length];
		return this.get(keys, localVersions);
	}
}