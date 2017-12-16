package com.exmple.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpUtil {
	private final static String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36";
	private Map<String, String> mapHeaders = new ConcurrentHashMap<String, String> ();
	private Map<String, String> mapCookies = new ConcurrentHashMap<String,  String> ();

	public String doGet(String url) throws Exception {
		return doGet(url, null, 30000);
	}

	public String doGet(String url, Map<String, String> headers) throws Exception {
		return doGet(url, headers, 30000);
	}
	
	public String doPost(String url) throws Exception {
		String charset = "UTF-8";
		return doPost(url, null, null, charset, 30000);
	}

	public String doPost(String url, String json) throws Exception {
		String charset = "UTF-8";
		return doPost(url, json, null, charset);
	}
	
	public String doPost(String url, String json, String charset) throws Exception {
		return doPost(url, json, null, charset);
	}

	public String doPost(String url, Map<String, String> params) throws Exception {
		String charset = "UTF-8";
		return doPost(url, params, null, charset, 30000);
	}

	public String doPost(String url, Map<String, String> params, Map<String, String> headers) throws Exception {
		String charset = "UTF-8";
		return doPost(url, params, headers, charset, 30000);
	}

	public String getHeader(String key) {
		return mapHeaders.containsKey(key) ?  mapHeaders.get(key) : "";
	}
	
	public String getCookie(String key) {
		return mapCookies.containsKey(key) ?  mapCookies.get(key) : "";
	}
	
	private void getHeaderAndCookie(CloseableHttpResponse response) {
		Header[] header = response.getAllHeaders();
		for (int i = 0; i < header.length; i++) {
			if (header[i].getName().equalsIgnoreCase("Set-Cookie")) {
				String value = header[i].getValue();
				int start = value.indexOf("=");
				int end = value.indexOf(";", start);
				if (start != -1 && end != -1) {
					String name = value.substring(0, start);
					mapCookies.put(name, value.substring(start + 1, end));
				}
			}
			else {
				mapHeaders.put(header[i].getName(), header[i].getValue());
			}
		}
	}
	
	private String doGet(String url, Map<String, String> headers, int connectionRequestTimeout) throws Exception {
		if (StringUtils.isEmpty(url)) {
			throw new Exception();
		}

		mapHeaders.clear();
		mapCookies.clear();
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			HttpGet httpGet = new HttpGet(url);
			RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(connectionRequestTimeout).build();
			httpGet.setConfig(config);
			httpGet.addHeader("User-Agent", USER_AGENT);
			if (headers != null) {
				Iterator<Entry<String, String>> iterator = headers.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<String, String> header = iterator.next();
					httpGet.addHeader(header.getKey(), header.getValue());
				}
			}
			
			response = client.execute(httpGet);
			getHeaderAndCookie(response);
			return EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			throw e;
		} finally {
			if (response != null) {
				response.close();
			}

			client.close();
		}
	}
	
	public String doPost(String url, Map<String, String> params, Map<String, String> headers, String charset, int connectionRequestTimeout)
			throws Exception {
		if (StringUtils.isEmpty(url)) {
			return null;
		}

		mapHeaders.clear();
		mapCookies.clear();
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			HttpPost httpPost = new HttpPost(url);
			RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(20000).build();
			httpPost.setConfig(requestConfig);
			httpPost.addHeader("User-Agent", USER_AGENT);
			if (headers != null) {
				Iterator<Entry<String, String>> iterator = headers.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<String, String> header = iterator.next();
					httpPost.addHeader(header.getKey(), header.getValue());
				}
			}

			if (params != null) {
				List<NameValuePair> list = new ArrayList<NameValuePair>();
				Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<String, String> param = iterator.next();
					list.add(new BasicNameValuePair(param.getKey(), param.getValue()));
				}

				if (list.size() > 0) {
					UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
					httpPost.setEntity(entity);
				}
			}

			response = client.execute(httpPost);
			getHeaderAndCookie(response);
			return EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			throw e;
		} finally {
			if (response != null) {
				response.close();
			}

			client.close();
		}
	}

	public String doPost(String url, String json, Map<String, String> headers, String charset) throws Exception {
		if (StringUtils.isEmpty(url)) {
			return null;
		}

		mapHeaders.clear();
		mapCookies.clear();
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			HttpPost httpPost = new HttpPost(url);
			RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(30000).build();
			httpPost.setConfig(requestConfig);
			httpPost.addHeader("User-Agent", USER_AGENT);
			httpPost.addHeader("Content-Type", "application/json");
			if (headers != null) {
				Iterator<Entry<String, String>> iterator = headers.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<String, String> header = iterator.next();
					httpPost.addHeader(header.getKey(), header.getValue());
				}
			}
			
			httpPost.setEntity(new StringEntity(json, charset));
			response = client.execute(httpPost);
			getHeaderAndCookie(response);
			return EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			throw e;
		} finally {
			if (response != null) {
				response.close();
			}

			client.close();
		}
	}

	public byte[] doGetStream(String url, int connectionRequestTimeout) throws Exception {
		if (StringUtils.isEmpty(url)) {
			return null;
		}

		mapHeaders.clear();
		mapCookies.clear();
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			HttpPost httpPost = new HttpPost(url);
			RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(connectionRequestTimeout)
					.build();
			httpPost.setConfig(requestConfig);
			httpPost.addHeader("User-Agent", USER_AGENT);
			httpPost.setEntity(new StringEntity(url, "UTF-8"));
			response = client.execute(httpPost);
			getHeaderAndCookie(response);
			return EntityUtils.toByteArray(response.getEntity());
		} catch (Exception e) {
			throw e;
		} finally {
			if (response != null) {
				response.close();
			}

			client.close();
		}
	}
}
