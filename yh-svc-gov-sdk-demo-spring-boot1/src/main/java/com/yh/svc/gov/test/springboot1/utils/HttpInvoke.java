package com.yh.svc.gov.test.springboot1.utils;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class HttpInvoke {
    public static String  doGet(String url, Map<String,Object> params){
        CloseableHttpClient httpclient = null;
        try {
            httpclient = HttpClients.createDefault();
            URIBuilder builder = new URIBuilder(url);

            List<NameValuePair> list = convertParams(params);
            builder.addParameters(list);

            HttpGet get = new HttpGet(builder.build());

            CloseableHttpResponse response1 = httpclient.execute(get);
            String result = EntityUtils.toString(response1.getEntity());
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static String  doPost(String url){
        CloseableHttpClient httpclient = null;
        try {
            httpclient = HttpClients.createDefault();
            HttpPost post = new HttpPost(url);
//            List <NameValuePair> nvps = new ArrayList <NameValuePair>();
//            nvps.add(new BasicNameValuePair("username", "vip"));
//            nvps.add(new BasicNameValuePair("password", "secret"));
//            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            CloseableHttpResponse response1 = httpclient.execute(post);
            String result = EntityUtils.toString(response1.getEntity());
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    private static List<NameValuePair> convertParams(Map<String,Object> params) {

        List<NameValuePair> list = new LinkedList<>();
        if (params == null) {
            return list;
        }
        for(String key : params.keySet()){
            BasicNameValuePair param = new BasicNameValuePair(key, params.get(key).toString());
            list.add(param);
        }
        return list;
    }

    public static void main(String[] args) {
        System.out.println(doGet("http://www.baidu.com",null));
    }
}
