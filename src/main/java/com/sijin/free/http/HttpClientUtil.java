package com.sijin.free.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
/*
 * 利用HttpClient进行post请求的工具类
 */
public class HttpClientUtil {
    public String  doGet(String url,String charset, Map<String, String> headerMap){
        HttpClient httpClient = null;
        HttpGet httpGet = null;
        String result = null;
        try{
            httpClient = new SSLClient();
            httpGet = new HttpGet(url);
            if(!headerMap.isEmpty()){
                for(Map.Entry<String,String> entry : headerMap.entrySet()){
                    httpGet.setHeader(entry.getKey(),entry.getValue());
                }
            }
            HttpResponse response = httpClient.execute(httpGet);
            if(response != null){
                HttpEntity resEntity = response.getEntity();
                if(resEntity != null){
                    if(charset == null){
                        InputStream instreams = resEntity.getContent();
                        result= convertStreamToString(instreams);
                    }else {
                        result = EntityUtils.toString(resEntity,charset);
                    }
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return result;
    }


    public String doPost(String url, Map<String, String> map, String charset, Map<String, String> headerMap){
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try{
            httpClient = new SSLClient();
            httpPost = new HttpPost(url);
            if(!headerMap.isEmpty()){
                for(Map.Entry<String,String> entry : headerMap.entrySet()){
                    httpPost.setHeader(entry.getKey(),entry.getValue());
                }
            }

            //设置参数
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator iterator = map.entrySet().iterator();
            while(iterator.hasNext()){
                Entry<String,String> elem = (Entry<String, String>) iterator.next();
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



        public static String convertStreamToString(InputStream is) {
            StringBuilder sb1 = new StringBuilder();
            byte[] bytes = new byte[4096];
            int size = 0;

            try {
                while ((size = is.read(bytes)) > 0) {
                    String str = new String(bytes, 0, size, "UTF-8");
                    sb1.append(str);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return sb1.toString();
        }

}