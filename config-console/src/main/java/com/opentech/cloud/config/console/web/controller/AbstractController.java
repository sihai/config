package com.opentech.cloud.config.console.web.controller;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * 
 * @author sihai
 *
 */
public class AbstractController {

	/**
	 * 数据绑定
	 * 
	 * @param binder
	 *            WebDataBinder
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
		binder.registerCustomEditor(Date.class, new DateEditor(true));
	}
	
	/**
	 * 判断是否登录
	 * @param request
	 * @return
	 */
	protected boolean isLogined(HttpServletRequest request) {
		HttpSession s = request.getSession();
		//return null != s.getAttribute(AdminUser.COOKIE_KEY);
		return false;
	}
	
	public static class DateEditor extends PropertyEditorSupport {

		public static final String[] DATE_PATTERNS = new String[] { "yyyy", "yyyy-MM", "yyyyMM", "yyyy/MM", "yyyy-MM-dd", "yyyyMMdd", "yyyy/MM/dd", "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss", "yyyy/MM/dd HH:mm:ss" };
		
		/** 默认日期格式 */
		private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

		/** 是否将空转换为null */
		private boolean emptyAsNull;

		/** 日期格式 */
		private String dateFormat = DEFAULT_DATE_FORMAT;

		/**
		 * @param emptyAsNull
		 *            是否将空转换为null
		 */
		public DateEditor(boolean emptyAsNull) {
			this.emptyAsNull = emptyAsNull;
		}

		/**
		 * @param emptyAsNull
		 *            是否将空转换为null
		 * @param dateFormat
		 *            日期格式
		 */
		public DateEditor(boolean emptyAsNull, String dateFormat) {
			this.emptyAsNull = emptyAsNull;
			this.dateFormat = dateFormat;
		}

		/**
		 * 获取日期
		 * 
		 * @return 日期
		 */
		@Override
		public String getAsText() {
			Date value = (Date) getValue();
			return value != null ? new SimpleDateFormat(dateFormat).format(value) : "";
		}

		/**
		 * 设置日期
		 * 
		 * @param text
		 *            字符串
		 */
		@Override
		public void setAsText(String text) {
			if (text == null) {
				setValue(null);
			} else {
				String value = text.trim();
				if (emptyAsNull && "".equals(value)) {
					setValue(null);
				} else {
					try {
						setValue(DateUtils.parseDate(value, DATE_PATTERNS));
					} catch (ParseException e) {
						setValue(null);
					}
				}
			}
		}
	}
}
