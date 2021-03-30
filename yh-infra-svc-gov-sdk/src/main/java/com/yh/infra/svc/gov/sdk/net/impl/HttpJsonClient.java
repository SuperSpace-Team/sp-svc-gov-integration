package com.yh.infra.svc.gov.sdk.net.impl;

import com.yh.infra.svc.gov.sdk.command.HttpClientResponse;
import com.yh.infra.svc.gov.sdk.util.CollectionUtils;
import com.yh.infra.svc.gov.sdk.util.StringUtils;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @description: Http工具类
 * @author: luchao
 * @date: Created in 2021/3/24 4:53 下午
 */
public class HttpJsonClient {

    private static final Logger logger = LoggerFactory.getLogger(HttpJsonClient.class);

    private static CloseableHttpClient httpClient = null;
    private static final int DEFAULT_TIMEOUT_SECOND = 20;

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String DEFAULTCHARSET = "UTF-8";
    private static final String DEFAULT_CONTENT_TYPE = "application/json";

    private static final ContentType JSON_CONTENT_TYPE = ContentType.create(DEFAULT_CONTENT_TYPE, Charset.forName(DEFAULTCHARSET));

    static RequestConfig defaultRequestConfig = null;
    static {
        defaultRequestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT).build();

        // 设置默认的配置
        httpClient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).setConnectionTimeToLive(DEFAULT_TIMEOUT_SECOND, TimeUnit.SECONDS).build();

    }

    /**
     * 生成本次使用的请求配置
     *
     * @param second 超时秒数
     * @return
     */
    private static RequestConfig makeLocalRequestConfig(int second) {

        RequestConfig requestConfig = null;

        if (second > 0) {
            requestConfig = RequestConfig.copy(defaultRequestConfig).setSocketTimeout(second * 1000).setConnectTimeout(second * 1000).setConnectionRequestTimeout(second * 1000).build();

        } else {
            requestConfig = RequestConfig.copy(defaultRequestConfig).setSocketTimeout(DEFAULT_TIMEOUT_SECOND * 1000).setConnectTimeout(DEFAULT_TIMEOUT_SECOND * 1000).setConnectionRequestTimeout(DEFAULT_TIMEOUT_SECOND * 1000).build();
        }

        return requestConfig;

    }

    /**
     * 构造请求参数
     *
     * @param params
     * @return
     */
    private static List<NameValuePair> buildParam(Map<String, ?> params) {
        List<NameValuePair> values = new ArrayList<NameValuePair>();
        for (Map.Entry<String, ?> entity : params.entrySet()) {
            BasicNameValuePair pare = new BasicNameValuePair(entity.getKey(), entity.getValue().toString());
            values.add(pare);
        }
        return values;
    }




    /**
     * from common-utilities
     *
     * @param url
     * @param params
     * @param second 0 means default(20)
     * @param headers
     * @return
     */
    public static HttpClientResponse getData(String url, Map<String, Object> params, int second, Header[] headers) {

        // build request
        if (params != null && !params.isEmpty()) {
            List<NameValuePair> values = buildParam(params);
            String str = URLEncodedUtils.format(values, DEFAULTCHARSET);
            if (url.indexOf('?') > -1) {
                url += '&' + str;
            } else {
                url += '?' + str;
            }
        }
        HttpGet httpget = new HttpGet(url);

        return doExecute(httpget, second, headers);

    }



    /**
     * from common-utilities
     *
     * @param url
     * @param params
     * @param second 0 means default(20)
     * @param headers
     * @return
     */
    public static HttpClientResponse postFormData(String url, Map<String, ?> params, int second, Header[] headers) {


        HttpPost httpPost = new HttpPost(url);

        if (params != null && !params.isEmpty()) {
            List<NameValuePair> values = buildParam(params);
            try {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(values, DEFAULTCHARSET);
                httpPost.setEntity(entity);
            } catch (UnsupportedEncodingException e) {
            	HttpClientResponse clientReturn = new HttpClientResponse();
                clientReturn.setError("[" + url + "]" + " UnsupportedEncodingException to " + DEFAULTCHARSET);
                return clientReturn;
            }
        }
        return doExecute(httpPost, second, headers);

    }

    /**
     * from common-utilities
     * 需指定 Content type：httpost.setHeader("Content-type", "application/json");
     * 否则默认使用 Content type 'text/plain;charset=UTF-8'
     *
     * @param url
     * @param second 0 means default(20)
     * @param headers
     * @return
     */
    public static HttpClientResponse postJsonData(String url, String jsonData, int second, Header[] headers) {
        HttpPost httpPost = new HttpPost(url);
        if (StringUtils.isNotBlank(jsonData)) {
            try {
                byte entitybytes[] = jsonData.getBytes(DEFAULTCHARSET);
                httpPost.setEntity(new ByteArrayEntity(entitybytes, JSON_CONTENT_TYPE));
            } catch (Exception e) {
                logger.error("postJsonData error. params is [{}] , [{}], [{}]",url,jsonData,second,e);
                HttpClientResponse clientReturn = new HttpClientResponse();
                clientReturn.setError("[" + url + "]" + " UnsupportedEncodingException to " + DEFAULTCHARSET);
                return clientReturn;
            }
        }

        return doExecute(httpPost, second, headers);
    }

    /**
     * PUT
     *
     * @param url
     * @param second
     * @param headers
     * @return
     */
    public static HttpClientResponse putJsonData(String url, String jsonData, int second, Header[] headers) {
        HttpPut httpPut = new HttpPut(url);

        if (null == jsonData) {
            jsonData = "";
        }

        httpPut.setEntity(new StringEntity(jsonData, DEFAULTCHARSET));

        httpPut.setHeader(CONTENT_TYPE, DEFAULT_CONTENT_TYPE);

        return doExecute(httpPut, second, headers);

    }


    /**
     * HTTP 调用
     *
     * @param request
     * @param second
     * @param headers
     * @return
     */
    private static HttpClientResponse doExecute(final HttpRequestBase request, int second, Header[] headers) {
        // 返回值
        CloseableHttpResponse httpResponse = null;
        String url = request.getURI().toString();
        HttpClientResponse ret = new HttpClientResponse();

        try {
            request.setConfig(makeLocalRequestConfig(second));
            if (!CollectionUtils.isEmpty(headers)) {
                request.setHeaders(headers);
            }

            // 强制不使用 长连接。
            request.addHeader(new BasicHeader("Connection", "close"));


			if (logger.isDebugEnabled())
				logger.debug("begin to execute url:{}", url);
            httpResponse = httpClient.execute(request);

            ret.setStatusCode(httpResponse.getStatusLine().getStatusCode());
            ret.setEntity(EntityUtils.toString(httpResponse.getEntity()));
        } catch (Exception e) {
        	ret.setError(e.getMessage());
            logger.warn("post data failed . {}" , url, e);
        } finally {
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    logger.warn("httpResponse.close() failed");
                }
            }
        }

        return ret;
    }
}
