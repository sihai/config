package com.opentech.cloud.config.client.internal;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.opentech.cloud.config.client.ConfigClient;
import com.opentech.cloud.config.client.ConfigClientHolder;
import com.opentech.cloud.config.client.app.AppConfig;
import com.opentech.cloud.config.client.exception.ConfigException;

/**
 * 
 * @author sihai
 *
 */
public class DefaultConfigClientTest {
	
	private static final String KEY1 = "com.opentech.cloud.config.key1";
	private static final String VALUE1 = "com.opentech.cloud.config.value1";
	private static final String KEY2 = "com.opentech.cloud.config.key2";
	private static final String VALUE2 = "com.opentech.cloud.config.value2";
	private static final String KEY3 = "com.opentech.cloud.config.key3";
	private static final String VALUE3 = "com.opentech.cloud.config.value3";

	@Test
	public void test() throws ConfigException, InterruptedException {
		final CountDownLatch latch = new CountDownLatch(2);
		ConfigClient configclient = ConfigClientHolder.getInstance();
		
		this.storeData();
		
		System.out.println(configclient.getValue(KEY1));
		
		System.out.println(configclient.getValue(KEY2, new ConfigClient.Listener() {
			
			@Override
			public void onChanged(String value) {
				System.out.println(String.format("Config changed, thread: %s, key: %s, new value: %s", Thread.currentThread().getName(), KEY2, value));
				latch.countDown();
			}
		}));
		
		System.out.println(configclient.getValue(KEY3, new ConfigClient.Listener() {
			
			@Override
			public void onChanged(String value) {
				System.out.println(String.format("Config changed, thread: %s, key: %s, new value: %s", Thread.currentThread().getName(), KEY3, value));
				latch.countDown();
			}
		}));
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					DefaultConfigClientTest.this.storeData();
				} catch (ConfigException e) {
					e.printStackTrace();
				}
			}
			
		}).start();
		
		latch.await(5, TimeUnit.MINUTES);
	}
	
	/**
	 * 
	 * @throws ConfigException
	 */
	private void storeData() throws ConfigException {
		ConfigClient configclient = ConfigClientHolder.getInstance();
		AppConfig config = new AppConfig();
		config.setKey("com.opentech.cloud.config.key1");
		config.setValue("com.opentech.cloud.config.value1");
		config.setCreator("sihai");
		config.setMender("sihai2");
		configclient.publish(config, "测试1");
		
		config = new AppConfig();
		config.setKey("com.opentech.cloud.config.key2");
		config.setValue("com.opentech.cloud.config.value2");
		config.setCreator("sihai");
		config.setMender("sihai2");
		configclient.publish(config, "测试2");
		
		config = new AppConfig();
		config.setKey("com.opentech.cloud.config.key3");
		config.setValue("com.opentech.cloud.config.value3");
		config.setCreator("sihai");
		config.setMender("sihai");
		configclient.publish(config, "测试2");
	}

}
