package com.opentech.cloud.config.utils.network;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * 
 * @author sihai
 *
 */
public abstract class HttpClientUtils {

	public static final String HTTP_HEADER_REFERER = "referer";

	/**
	 * 
	 */
	private static HttpClient httpclient;
	
	static {
		
		try {
			final TrustManager[] trustAllCerts = new TrustManager[] {
		        new X509TrustManager() {
		          @Override
		          public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
		          }
	
		          @Override
		          public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
		          }
	
		          @Override
		          public java.security.cert.X509Certificate[] getAcceptedIssuers() {
		            return null;
		          }
		        }
		    };
	
		    // Install the all-trusting trust manager
		    final SSLContext sslContext = SSLContext.getInstance("SSL");
		    sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
			
			HttpClientBuilder hcb = HttpClientBuilder.create();
			hcb.setSslcontext(sslContext);
			httpclient = hcb.build();
		} catch (Exception e) {
		    throw new RuntimeException(e);
		}
	}
	
	/**
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static final String sync_get(String strURL) throws IOException {
		try {
			URI url = new URI(strURL);
			return sync_get(url);
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/**
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static final String sync_get(URI url) throws IOException {
		return sync_get(url, Collections.EMPTY_MAP);
	}
	
	/**
	 * 
	 * @param url
	 * @param headers
	 * @return
	 * @throws IOException
	 */
	public static final String sync_get(URI url, Map<String, String> headers) throws IOException {
		HttpGet request = new HttpGet(url);
		if(null != headers && !headers.isEmpty()) {
			for(Map.Entry<String, String> e : headers.entrySet()) {
				request.addHeader(e.getKey(), e.getValue());
			}
		}
		HttpResponse response = httpclient.execute(request);
		return EntityUtils.toString(response.getEntity());
	}
	
	/**
	 * 
	 * @param url
	 * @param formData
	 * @return
	 * @throws IOException
	 */
	public static final String sync_post(URI url, Map<String, String> formData) throws IOException {
		return sync_post(url, null, formData);
	}
	
	/**
	 * 
	 * @param url
	 * @param headers
	 * @param formData
	 * @return
	 * @throws IOException
	 */
	public static final String sync_post(URI url, Map<String, String> headers, Map<String, String> formData) throws IOException {
		HttpPost request = new HttpPost(url);
		if(null != headers && !headers.isEmpty()) {
			for(Map.Entry<String, String> e : headers.entrySet()) {
				request.addHeader(e.getKey(), e.getValue());
			}
		}
		
		if(null != formData && !formData.isEmpty()) {
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			for(Map.Entry<String, String> e : formData.entrySet()) {
				parameters.add(new BasicNameValuePair(e.getKey(), e.getValue()));
			}
			request.setEntity(new UrlEncodedFormEntity(parameters));
		}
		
		HttpResponse response = httpclient.execute(request);
		return EntityUtils.toString(response.getEntity());
	}
}
