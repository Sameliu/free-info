package com.sijin.free.main;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sijin.free.po.DockMA;
import com.sijin.free.util.HtmlUtil;
import com.sijin.free.util.HttpClientPoolUtill;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: 思进
 * @Description:
 * @Date: 下午3:24 2017/7/12
 */
public class ChooseSocketReport {

    //public static String KEYWORD = "不是ST且市盈率大于12倍且市盈率小于30倍主板净利润增长率大于30股价大于10小于16净资产收益率大于3";
    public static String KEYWORD = "不是ST且市盈率大于12倍且市盈率小于30倍主板净利润增长率大于30股价大于8小于12净资产收益率大于3";

    public static String SEARCH_URL = "http://www.iwencai.com/stockpick/search?ts=1&f=1&qs=stockhome_topbar_click&w=";

    public static String CACHE_URL= "http://www.iwencai.com/stockpick/cache?p=1&perpage=70&changeperpage=1&token=";

    static HttpClientPoolUtill httpClient = new HttpClientPoolUtill(2,1);

    public static void main(String[] args) {
        String url = SEARCH_URL.concat(KEYWORD);
        try {
            Map<String, String> headerMap = getheaderMap();
            System.out.println("search__url is " + url);
            String response = httpClient.get(url,headerMap);
            int total = getTotal(response);
            if(total > 70){
                System.out.println("此次共查询到: " +total +",查询结果大于70");
            }else {
                System.out.println("此次共查询到: " +total );
            }

            String token = getToken(response);
            System.out.println("token is: " + token);
            url = CACHE_URL + token;
            String result = httpClient.get(url);
            System.out.println("cache_url:" + url);
            JSONObject jsonObject = JSONObject.parseObject(result);
            JSONArray array = jsonObject.getJSONArray("result");
            List<DockMA> list = new ArrayList<>();
            String code = "";
            for(int i=0;i<array.size();i++){
                JSONArray jobArr = JSONArray.parseArray(array.getString(i));
                DockMA a = new DockMA();
                String[] str = jobArr.getString(0).split("\\.");
                code = str[1].toLowerCase().concat(str[0]);
                a.setCode(code);
                list.add(a);
            }
            HtmlUtil.generateSimpleHtml(list,"choose.html");
            System.out.println("生成完毕");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static Map<String, String> getheaderMap() {
        Map<String, String> map = new HashMap<>();
        map.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        map.put("Accept-Encoding","gzip, deflate");
        map.put("Accept-Language","zh-CN,zh;q=0.8,en;q=0.6");
        map.put("Cache-Control","max-age=0");
        map.put("Connection:","keep-alive");
        //map.put("Cookie","PHPSESSID=96navg1rjm3v3tajdu6gumcec0; guideState=1; cid=35bc4v4jirkpv9u8bosijb85v31501212914; ComputerID=35bc4v4jirkpv9u8bosijb85v31501212914");
        map.put("Host","www.iwencai.com");
        map.put("Upgrade-Insecure-Requests","1");
        map.put("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36");

        return map;

    }

    private static int getTotal(String response) {
        Document document = Jsoup.parse(response);
        String total = document.select("span.num").text();
        return Integer.valueOf(total);
    }

    private static String getToken(String response) {
        String res = "";
        Pattern p = Pattern.compile("token=(.*?)&info");
        Matcher m = p.matcher(response);
        while (m.find()) {
            res = m.group(1);
            break;
        }
        return res;
    }
}
