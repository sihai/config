package com.opentech.cloud.config.client.internal.storage;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.CharBuffer;

import org.apache.commons.io.IOUtils;

import com.alibaba.fastjson.JSON;
import com.opentech.cloud.config.client.app.AppConfig;
import com.opentech.cloud.config.client.exception.ConfigException;
import com.opentech.cloud.config.client.internal.lru.MemoryLRUCache;

/**
 * 基于本地文件的本地存储
 * @author sihai
 *
 */
public class FileLocalStorage implements Storage {

	/**
	 * 内存lru缓存
	 */
	private MemoryLRUCache<String, AppConfig> lruCache;
	
	/**
	 * 缓存目录
	 */
	private String storageDirectory;
	
	/**
	 * 构造方法
	 * @param cacheDirectory
	 */
	public FileLocalStorage(String cacheDirectory) {
		this(cacheDirectory, MemoryLRUCache.DEFAULT_MAX_ENTRIES);
	}
	
	/**
	 * 构造方法
	 * @param storageDirectory
	 * @param maxEntriesInMemory
	 */
	public FileLocalStorage(String storageDirectory, int maxEntriesInMemory) {
		this.storageDirectory = storageDirectory;
		this.lruCache = new MemoryLRUCache<String, AppConfig>();
		this.initStorageDirectoryIfNeed();
	}
	
	@Override
	public void store(AppConfig config, String reason) throws ConfigException {
		FileWriter writer = null;
		try {
			// 1. 更新内存
			this.lruCache.put(config.getKey(), config);
			// 2. 获取文件
			writer = new FileWriter(this.getFile(config.getKey()));
			// 3. 写
			writer.write(JSON.toJSONString(config));
			// 4. 刷盘
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			// 5. 关闭文件流
			IOUtils.closeQuietly(writer);
		}
	}

	@Override
	public AppConfig get(String key) {
		// 1. 先读内存
		AppConfig config = this.lruCache.get(key);
		if(null != config) {
			// 命中内存
			return config;
		}
		
		// 2. 读文件
		config = readFile(this.getFile(key));
		
		if(null != config) {
			// 3. 缓存到内存
			this.lruCache.put(key, config);
		}
		
		// 4. 返回
		return config;
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	private File getFile(String key) {
		return new File(this.storageDirectory + File.separator + key + ".config");
	}
	
	/**
	 * 初始化目录, 如果有需要
	 */
	private void initStorageDirectoryIfNeed() {
		File file = new File(this.storageDirectory);
		if(!file.exists()) {
			file.mkdirs();
		}
	}
	
	/**
	 * 从文件读取
	 * @param file
	 * @return
	 */
	private AppConfig readFile(File file) {
		FileReader reader = null;
		
		if(!file.exists()) {
			// 文件不存在
			return null;
		}
		
		try {
			// 1. 创建文件reader
			reader = new FileReader(file);
			// 2. 创建缓存
			CharBuffer buffer = CharBuffer.allocate((int)file.length());
			// 3. 读文件
			reader.read(buffer);
			// 4. json解析, 转换成java bean
			return JSON.toJavaObject(JSON.parseObject(buffer.toString()), AppConfig.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if(null != reader) {
				// 5. 关闭文件流
				IOUtils.closeQuietly(reader);
			}
		}
	}
}