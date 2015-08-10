package com.opentech.cloud.config.console.web.controller;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.opentech.cloud.config.client.app.AppConfig;
import com.opentech.cloud.config.client.exception.DataValidateException;
import com.opentech.cloud.config.client.result.ErrorCode;
import com.opentech.cloud.config.client.result.Result;
import com.opentech.cloud.config.client.result.ResultFactory;
import com.opentech.cloud.config.storage.manager.ConfigManager;

/**
 * 
 * @author sihai
 *
 */
@Controller
@RequestMapping("/api/config/")
public class ConfigAPIController extends AbstractController {

	@Resource
	private ConfigManager storage;
	
	@RequestMapping(value = "get", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Result doGet(String data) {
		if(StringUtils.isBlank(data)) {
			return ResultFactory.failed(ErrorCode.WRONG_PARAMETER, new String[]{"need data"});
		}
		GetParameters ps = JSON.toJavaObject(JSON.parseObject(data), GetParameters.class);
		if(null == ps.getKeys() || 0 == ps.getKeys().length) {
			return ResultFactory.failed(ErrorCode.WRONG_PARAMETER, new String[]{"need data.keys"});
		}
		if(null == ps.getLocalVersions() || 0 == ps.getLocalVersions().length) {
			return ResultFactory.failed(ErrorCode.WRONG_PARAMETER, new String[]{"need data.localVersions"});
		}
		if(ps.getKeys().length != ps.getLocalVersions().length) {
			return ResultFactory.failed(ErrorCode.WRONG_PARAMETER, new String[]{"keys.length != localVersions.length"});
		}
		return ResultFactory.succeed(this.storage.get(ps.getKeys(), ps.getLocalVersions()));
	}
	
	@RequestMapping(value = "store", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Result doStore(String data) {
		if(StringUtils.isBlank(data)) {
			return ResultFactory.failed(ErrorCode.WRONG_PARAMETER, new String[]{"need data"});
		}
		
		StoreParameters ps = JSON.toJavaObject(JSON.parseObject(data), StoreParameters.class);
		if(null == ps.getConfig()) {
			return ResultFactory.failed(ErrorCode.WRONG_PARAMETER, new String[]{"need data.config"});
		}
		if(null == ps.getReason()) {
			return ResultFactory.failed(ErrorCode.WRONG_PARAMETER, new String[]{"need data.reason"});
		}
		
		try {
			this.storage.store(ps.config, ps.reason);
			return ResultFactory.succeed(null);
		} catch (DataValidateException e) {
			return ResultFactory.failed(ErrorCode.WRONG_PARAMETER, new String[]{e.getMessage()});
		}
	}
	
	/**
	 * 
	 * @author sihai
	 *
	 */
	public static class GetParameters {
		
		private String[] keys;
		
		private long[] localVersions;

		public String[] getKeys() {
			return keys;
		}

		public void setKeys(String[] keys) {
			this.keys = keys;
		}

		public long[] getLocalVersions() {
			return localVersions;
		}

		public void setLocalVersions(long[] localVersions) {
			this.localVersions = localVersions;
		}
	}
	
	/**
	 * 
	 * @author sihai
	 *
	 */
	public static class StoreParameters {
		
		private AppConfig config;
		
		private String reason;

		public AppConfig getConfig() {
			return config;
		}

		public void setConfig(AppConfig config) {
			this.config = config;
		}

		public String getReason() {
			return reason;
		}

		public void setReason(String reason) {
			this.reason = reason;
		}
	}
}
