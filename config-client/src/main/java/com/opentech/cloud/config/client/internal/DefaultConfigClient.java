package com.opentech.cloud.config.client.internal;

import com.opentech.cloud.config.client.internal.storage.RemoteStorage;
import com.opentech.cloud.config.client.internal.storage.Storage;


/**
 * 默认的ConfigClient实现
 * <p>
 * 定时轮询config-server, 业务方listen的所有配置项
 * 
 * @author sihai
 *
 */
public class DefaultConfigClient extends AbstractConfigClient {
	
	/**
	 * 
	 * @param localStorage
	 * @param remoteStorage
	 */
	public DefaultConfigClient(Storage localStorage, RemoteStorage remoteStorage) {
		super(localStorage, remoteStorage);
	}
}