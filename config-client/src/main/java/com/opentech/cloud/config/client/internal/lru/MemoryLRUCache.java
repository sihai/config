package com.opentech.cloud.config.client.internal.lru;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * LRU Cache (in memory) 非线程安全
 * @author sihai
 *
 */
public class MemoryLRUCache<K, V> {
	
	/**
	 * 默认的常住内存的最大条目数
	 */
	public static final int DEFAULT_MAX_ENTRIES = 1024;

	/**
	 * 常住内存的最大条目数
	 */
	private final int maxEntries;
	
	/**
	 * 内部存储
	 */
	private LinkedHashMap<K, V> inernalStore;
	
	/**
	 * inernalStore 同步保护读写锁
	 */
	private ReadWriteLock rwlock;
	
	/**
	 * 构造方法
	 */
	public MemoryLRUCache() {
		this(DEFAULT_MAX_ENTRIES);
	}
	
	/**
	 * 构造方法
	 * @param maxEntries
	 */
	public MemoryLRUCache(int maxEntries) {
		this.maxEntries = maxEntries;
		this.inernalStore = new LinkedHashMap<K, V>(maxEntries) {
			protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
				boolean isNeedRemove = inernalStore.size() > MemoryLRUCache.this.maxEntries;
				if(isNeedRemove) {
					// fire
					//fireEliminated(eldest.getKey(), eldest.getValue());
				}
				return isNeedRemove;
			}
		};
		this.rwlock = new ReentrantReadWriteLock(false);
	}

	public void clear() {
		ReadWriteLock rwlock = this.rwlock;
		try {
			rwlock.writeLock().lock();
			this.inernalStore.clear();
		} finally {
			rwlock.writeLock().unlock();
		}
	}

	public V get(K key) {
		try {
			rwlock.readLock().lock();
			return inernalStore.get(key);
		} finally {
			rwlock.readLock().unlock();
		}
	}

	public void put(K key, V value) {
		try {
			rwlock.writeLock().lock();
			this.inernalStore.put(key, value);
		} finally {
			rwlock.writeLock().unlock();
		}
	}

	public void delete(K key) {
		try {
			rwlock.writeLock().lock();
			this.inernalStore.remove(key);
		} finally {
			rwlock.writeLock().unlock();
		}
	}
}
