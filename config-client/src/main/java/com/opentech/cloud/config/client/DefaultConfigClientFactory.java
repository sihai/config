package com.opentech.cloud.config.client;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import com.opentech.cloud.config.client.internal.DefaultConfigClient;
import com.opentech.cloud.config.client.internal.lru.MemoryLRUCache;
import com.opentech.cloud.config.client.internal.storage.FileLocalStorage;
import com.opentech.cloud.config.client.internal.storage.HttpRemoteStorage;
import com.opentech.cloud.config.client.internal.storage.RemoteStorage;
import com.opentech.cloud.config.client.internal.storage.Storage;

/**
 * 默认ConfigClientFactory实现
 * <p>
 * 使用
 * 	{@link com.opentech.cloud.config.client.internal.DefaultConfigClient}
 * 		{@link com.opentech.cloud.config.client.internal.storage.FileLocalStorage}
 * 		{@link com.opentech.cloud.config.client.internal.storage.HttpRemoteStorage}
 * @author sihai
 *
 */
public class DefaultConfigClientFactory implements ConfigClientFactory {

	/**
	 * 
	 */
	private String localCacheDirectory = System.getProperty("user.home") + File.separator + "config-local-cache";
	
	/**
	 * 
	 */
	private int maxEntriesInMemory = MemoryLRUCache.DEFAULT_MAX_ENTRIES;
	
	/**
	 * 
	 */
	private URI storeURL;
	
	/**
	 * 
	 */
	private URI getURL;
	
	/**
	 * 
	 * @param localCacheDirectory
	 * @return
	 */
	public DefaultConfigClientFactory withLocalCacheDirectory(String localCacheDirectory) {
		this.localCacheDirectory = localCacheDirectory;
		return this;
	}
	
	/**
	 * 
	 * @param storeURL
	 * @return
	 */
	public DefaultConfigClientFactory withStoreURL(String storeURL) {
		try {
			this.storeURL = new URI(storeURL);
			return this;
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/**
	 * 
	 * @param getURL
	 * @return
	 */
	public DefaultConfigClientFactory withGetURL(String getURL) {
		try {
			this.getURL = new URI(getURL);
			return this;
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/**
	 * 
	 * @param maxEntriesInMemory
	 * @return
	 */
	public DefaultConfigClientFactory withMaxEntriesInMemory(int maxEntriesInMemory) {
		this.maxEntriesInMemory = maxEntriesInMemory;
		return this;
	}
	
	@Override
	public ConfigClient newInstance() {
		Storage localStorage = new FileLocalStorage(this.localCacheDirectory, this.maxEntriesInMemory);
		RemoteStorage remoteStorage = new HttpRemoteStorage(this.storeURL, this.getURL);
		return new DefaultConfigClient(localStorage, remoteStorage);
	}

}