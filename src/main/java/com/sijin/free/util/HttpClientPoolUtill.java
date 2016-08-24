package com.sijin.free.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * http工具类
 * Author: baichuan - xiajun
 * Date: 16/03/15 11:01
 */
public class HttpClientPoolUtill {
    private PoolingHttpClientConnectionManager cm;
    private CloseableHttpClient httpClient;

    public HttpClientPoolUtill(int total, int max) {
        this.init(total,max);
    }

    /**
     * 设置本实利访问目标host的连接数大小
     *
     * @param host 目标host
     * @param max  最大连接数
     */
    public HttpClientPoolUtill(HttpHost host, int max) {
        this.init(50,10);
        this.addRoute(host, max);
    }

    private void init(int total,int max) {
        cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(total);
        cm.setDefaultMaxPerRoute(max);
        httpClient = HttpClients.custom().setKeepAliveStrategy(getKeepAliveStrat()).setConnectionManager(cm).setRetryHandler(new DefaultHttpRequestRetryHandler(10,true)).build();
    }

    /**
     * 添加漏油规则
     *
     * @param host
     */
    public void addRoute(HttpHost host, int max) {
        cm.setMaxPerRoute(new HttpRoute(host), max);
    }

    public String get(String url, String charset, int readTimeout, int connTimeout) throws Exception {
        String res = null;
        HttpGet httpget = new HttpGet(url);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(readTimeout).setConnectTimeout(connTimeout).build();
        httpget.setConfig(requestConfig);
        HttpContext context = HttpClientContext.create();
        try {
            CloseableHttpResponse response = httpClient.execute(httpget, context);
            try {
                HttpEntity entity = response.getEntity();
                res = EntityUtils.toString(entity, charset);
            } finally {
                response.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
        return res;
    }

    public String post(String url,Map<String,String> map,String charset){
        HttpPost httpPost = null;
        String result = null;
        try{
            httpPost = new HttpPost(url);
            //设置参数
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator iterator = map.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<String,String> elem = (Map.Entry<String, String>) iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));
            }
            if(list.size() > 0){
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);
                httpPost.setEntity(entity);
            }
            HttpResponse response = httpClient.execute(httpPost);
            if(response != null){
                HttpEntity resEntity = response.getEntity();
                if(resEntity != null){
                    result = EntityUtils.toString(resEntity,charset);
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * 设置keepAlive
     *
     * @return
     */
    private ConnectionKeepAliveStrategy getKeepAliveStrat() {
        ConnectionKeepAliveStrategy keepAliveStrat = new DefaultConnectionKeepAliveStrategy() {
            @Override
            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                long keepAlive = super.getKeepAliveDuration(response, context);
                if (keepAlive == -1) {
                    keepAlive = 10000;//10s
                }
                return keepAlive;
            }
        };
        return keepAliveStrat;
    }

    public String get(String url) throws Exception {
        return this.get(url, "utf-8", 2500, 3000);
    }
}
