package com.opentech.cloud.config.client;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

/**
 * ConfigClient Holder
 * @author sihai
 *
 */
public class ConfigClientHolder {
	
	/**
	 * 
	 */
	public static final String CONF_FILE = "config.properties";
	
	/**
	 * 
	 */
	public static final String CONF_KEY_LOCAL_STORAGE_DIRECTORY = "com.opentech.cloud.config.client.storage.local.directory";
	
	/**
	 * 
	 */
	public static final String CONF_KEY_LOCAL_CACHE_MAX_ENTRIES_IN_MEMORY = "com.opentech.cloud.config.client.storage.maxEntriesInMemory";
	
	/**
	 * 
	 */
	public static final String CONF_KEY_REMOTE_STORAGE_GET_URL = "com.opentech.cloud.config.client.storage.remote.getURL";
	
	/**
	 * 
	 */
	public static final String CONF_KEY_REMOTE_STORAGE_STORE_URL = "com.opentech.cloud.config.client.storage.remote.storeURL";
	
	/**
	 * 
	 */
	private static final String LOCAL_STORAGE_DIRECTORY = System.getProperty("user.home") + File.separator + "config-local-storage";
	
	/**
	 * 
	 */
	private static final int MAX_ENTRIES_IN_MEMORY = 32;
	
	/**
	 * 
	 */
	private static final String GET_URL = "http://127.0.0.1:8080/api/config/get.do";
	
	/**
	 * 
	 */
	private static final String STORE_URL = "http://127.0.0.1:8080/api/config/store.do";
	
	/**
	 * 单例
	 */
	private static final ConfigClient singleton;
	
	static {
		
		// load 配置文件
		try {
			Properties properties = new Properties();
			properties.load(ConfigClientHolder.class.getResourceAsStream(String.format("/conf/%s", CONF_FILE)));
			
			// then try to load custom to override
			InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(CONF_FILE);
			if(null != in) {
				properties.load(in);
			}
			
			// local storage directory
			String localStorageDirectory = (String)properties.get(CONF_KEY_LOCAL_STORAGE_DIRECTORY);
			if(StringUtils.isBlank(localStorageDirectory)) {
				localStorageDirectory = LOCAL_STORAGE_DIRECTORY;
			}
			
			// maxEntriesInMemory
			Integer maxEntriesInMemory = (Integer)properties.get(CONF_KEY_LOCAL_CACHE_MAX_ENTRIES_IN_MEMORY);
			if(null == maxEntriesInMemory) {
				maxEntriesInMemory = MAX_ENTRIES_IN_MEMORY;
			}
			
			// get url
			String getURL = (String)properties.get(CONF_KEY_REMOTE_STORAGE_GET_URL);
			if(StringUtils.isBlank(getURL)) {
				getURL = GET_URL;
			}
			
			// store url
			String storeURL = (String)properties.get(CONF_KEY_REMOTE_STORAGE_STORE_URL);
			if(StringUtils.isBlank(storeURL)) {
				storeURL = STORE_URL;
			}
			
			//
			singleton = new DefaultConfigClientFactory()
				.withLocalCacheDirectory(localStorageDirectory)
				.withMaxEntriesInMemory(maxEntriesInMemory)
				.withGetURL(getURL)
				.withStoreURL(storeURL)
				.newInstance();
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 
	 */
	public static final ConfigClient getInstance() {
		return singleton;
	}
}
