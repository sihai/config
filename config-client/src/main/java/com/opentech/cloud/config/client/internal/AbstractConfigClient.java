package com.opentech.cloud.config.client.internal;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opentech.cloud.config.client.ConfigClient;
import com.opentech.cloud.config.client.app.AppConfig;
import com.opentech.cloud.config.client.exception.ConfigException;
import com.opentech.cloud.config.client.internal.storage.RemoteStorage;
import com.opentech.cloud.config.client.internal.storage.Storage;
import com.opentech.cloud.config.utils.thread.NamedThreadFactory;

/**
 * 抽象的ConfigClient实现
 * <p>
 * 实现一下公共的方法, 以及定义一些模板方法
 * 
 * @author sihai
 *
 */
public abstract class AbstractConfigClient implements ConfigClient {
 
	private static final Log logger = LogFactory.getLog(AbstractConfigClient.class);
	
	/**
	 * 默认轮询周期
	 */
	public static final long DEFAULT_PULL_PERIOD = 15;
	
	/**
	 * 轮询线程池默认core size
	 */
	public static final int DEFAULT_PULL_TASK_THREAD_POOL_CORE_SIZE = 2;
	
	/**
	 * 轮询线程池默认max size
	 */
	public static final int DEFAULT_PULL_TASK_THREAD_POOL_MAX_SIZE = 8;
	
	/**
	 * 轮询线程池默认keep alive, 单位秒
	 */
	public static final long DEFAULT_PULL_TASK_THREAD_POOL_KEEP_ALIVE = 60;
	
	/**
	 * 轮询线程池默认工作队列大小
	 */
	public static final int DEFAULT_PULL_TASK_THREAD_POOL_WORKER_QUEUE_CAPACITY = 1024;
	
	/**
	 * 配置项监听器, config.key -> listener, 目前设计的是一个配置项, 只能有一个监听器, 后注册的覆盖之前注册的监听器
	 */
	private Map<String, Listener> listeners;
	
	/**
	 * listeners 同步保护读写锁
	 */
	private ReadWriteLock rwlock;
	
	/**
	 * 本地存储
	 */
	private Storage localStorage;
	
	/**
	 * 远程存储
	 */
	private RemoteStorage remoteStorage;
	
	/**
	 * 轮询周期
	 */
	private long pullPeriod = DEFAULT_PULL_PERIOD;
	
	/**
	 * 调度线程
	 */
	private ScheduledExecutorService scheduler;
	
	/**
	 * 轮询线程池core size
	 */
	private int threadpoolCoreSize = DEFAULT_PULL_TASK_THREAD_POOL_CORE_SIZE;
	
	/**
	 * 轮询线程池max size
	 */
	private int threadpoolMaxSize = DEFAULT_PULL_TASK_THREAD_POOL_MAX_SIZE;
	
	/**
	 * 轮询线程池keep alive, 单位秒
	 */
	private long threadpoolKeepAlive = DEFAULT_PULL_TASK_THREAD_POOL_KEEP_ALIVE;
	
	/**
	 * 轮询线程池keep alive, 单位秒
	 */
	private int threadpoolWorkQueueCapacity = DEFAULT_PULL_TASK_THREAD_POOL_WORKER_QUEUE_CAPACITY;
	
	/**
	 * 轮询任务队列
	 */
	private BlockingQueue<Runnable> workQueue;
	/**
	 * 轮询任务执行线程池
	 */
	private ThreadPoolExecutor threadpool;
	
	/**
	 * 构造方法
	 * @param localStorage	本地存储接口
	 * @param remoteStorage	远程存储接口
	 */
	public AbstractConfigClient(Storage localStorage, RemoteStorage remoteStorage) {
		this.listeners = new HashMap<String, Listener>(32);
		this.rwlock = new ReentrantReadWriteLock(false);
		this.localStorage = localStorage;
		this.remoteStorage = remoteStorage;
	}
	
	@Override
	public void publish(AppConfig config, String reason) throws ConfigException {
		this.remoteStorage.store(config, reason);
	}

	@Override
	public AppConfig get(String key) throws ConfigException {
		// 1. 优先查询本地
		AppConfig config = this.localStorage.get(key);
		if(null != config) {
			return config;
		}
		
		// 2. 本地缓存未命中, 查询远程
		config = remoteStorage.get(key);
		if(null != config) {
			// 3. 缓存到本地
			localStorage.store(config, null);
		}
		
		// 返回
		return config;
	}

	@Override
	public AppConfig get(String key, Listener listener) throws ConfigException {
		// 1. 查询
		AppConfig config = this.get(key);
		// 2. 注册监听器
		this.registerListener(key, listener);
		return config;
	}

	@Override
	public String getValue(String key) throws ConfigException {
		AppConfig config = this.get(key);
		return null == config ? null : config.getValue();
	}

	@Override
	public String getValue(String key, Listener listener) throws ConfigException {
		AppConfig config = this.get(key, listener);
		return null == config ? null : config.getValue();
	}
	
	@Override
	public void release() {
		this.stopPuller();
	}

	//=====================================================================
	//	setter
	//=====================================================================
	public void setPullPeriod(long pullPeriod) {
		this.pullPeriod = pullPeriod;
	}

	public void setThreadpoolCoreSize(int threadpoolCoreSize) {
		this.threadpoolCoreSize = threadpoolCoreSize;
	}

	public void setThreadpoolMaxSize(int threadpoolMaxSize) {
		this.threadpoolMaxSize = threadpoolMaxSize;
	}

	public void setThreadpoolKeepAlive(long threadpoolKeepAlive) {
		this.threadpoolKeepAlive = threadpoolKeepAlive;
	}

	public void setThreadpoolWorkQueueCapacity(int threadpoolWorkQueueCapacity) {
		this.threadpoolWorkQueueCapacity = threadpoolWorkQueueCapacity;
	}
	
	/**
	 * 
	 * @param key
	 * @param listener
	 */
	private void registerListener(String key, Listener listener) {
		ReadWriteLock rwlock = this.rwlock;
		try {
			// 1. 写锁锁住
			rwlock.writeLock().lock();
			// 2. 存储监听器
			this.listeners.put(key, listener);
			// 3. 尝试启动轮询任务
			if(1 == this.listeners.size()) {
				this.startPuller();
			}
		} finally {
			// 4. 解锁
			rwlock.writeLock().unlock();
		}
	}
	
	/**
	 * 启动轮询
	 */
	private void startPuller() {
		// 调度, 1个线程应该够了
		this.scheduler = Executors.newScheduledThreadPool(1);
		// 轮询任务队列
		this.workQueue = new ArrayBlockingQueue<Runnable>(this.threadpoolWorkQueueCapacity);
		// 轮询任务执行
		this.threadpool = new ThreadPoolExecutor(this.threadpoolCoreSize, this.threadpoolMaxSize, this.threadpoolKeepAlive, TimeUnit.SECONDS, this.workQueue, new NamedThreadFactory("opentech.config.puller"));
		// 调度任务
		this.scheduler.scheduleWithFixedDelay(new Runnable() {

			@Override
			public void run() {
				ReadWriteLock rwlock = AbstractConfigClient.this.rwlock;
				try {
					// 1. 读锁锁住
					rwlock.readLock().lock();
					// 2. 遍历所有的, 被监听的配置项key, 为每一个key创建一个任务
					for(String key : AbstractConfigClient.this.listeners.keySet()) {
						try {
							AppConfig localConfig = AbstractConfigClient.this.localStorage.get(key);
							AbstractConfigClient.this.threadpool.execute(new PullTask(key, null == localConfig ? AppConfig.DUMMY_VERSION : localConfig.getVersion()));
						} catch (ConfigException e) {
							logger.error("AbstractConfigClient.this.localStorage.get failed", e);
						}
					}
				} finally {
					// 3. 解锁
					rwlock.readLock().unlock();
				}
			}
			
		}, this.pullPeriod, this.pullPeriod, TimeUnit.SECONDS);
	}
	
	/**
	 * 停止轮询
	 */
	private void stopPuller() {
		// 1. 停止调度线程
		if(null != this.scheduler) {
			this.scheduler.shutdownNow();
		}
		// 2. 停止轮询线程池
		if(null != this.threadpool) {
			this.threadpool.shutdownNow();
		}
		// 3. 清空任务队列
		if(null != this.workQueue) {
			this.workQueue.clear();
		}
	}
	
	/**
	 * 
	 * @param config
	 */
	private void fireChanged(AppConfig config) {
		ReadWriteLock rwlock = this.rwlock;
		try {
			// 1. 读锁锁住
			rwlock.readLock().lock();
			// 2. 获取监听器
			Listener l = this.listeners.get(config.getKey());
			if(null != l) {
				// 3. 执行
				l.onChanged(config.getValue());
			}
		} finally {
			// 4. 解锁
			rwlock.readLock().unlock();
		}
	}
	
	/**
	 * 轮询任务
	 * @author sihai
	 *
	 */
	private class PullTask implements Runnable {

		/**
		 * key
		 */
		private String key;
		
		/**
		 * 本地版本
		 */
		private long localVersion;
		
		/**
		 * 
		 * @param key
		 * @param localVersion
		 */
		public PullTask(String key, long localVersion) {
			this.key = key;
			this.localVersion = localVersion;
		}
		
		@Override
		public void run() {
			try {
				AppConfig config = AbstractConfigClient.this.remoteStorage.get(key);
				if(null != config) {
					// 1. 更新本地缓存
					AbstractConfigClient.this.localStorage.store(config, null);
					// 2. 通知相应监听器
					AbstractConfigClient.this.fireChanged(config);
				}
			} catch (Throwable t) {
				logger.debug("pull config for key: " + this.key + ", failed", t);
			}
		}

	}
}