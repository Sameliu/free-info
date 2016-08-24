package com.sijin.free.input;

import com.sijin.free.http.HttpClientUtil;
import com.sijin.free.po.DockInfo;
import com.sijin.free.util.FileUtil;
import com.sijin.free.util.HttpClientPoolUtill;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sijinzhang on 16/7/18.
 */
public class LoadStockToXUEQIU {


    public static void main(String[] args) throws InterruptedException {
        Map<String,String> headerMap = new HashMap<String, String>();

        headerMap.put("Accept", "*/*");
        headerMap.put("Accept-Encoding", "gzip, deflate, br");
        headerMap.put("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
        headerMap.put("cache-control", "no-cache");
        headerMap.put("Connection", "keep-alive");
        headerMap.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        headerMap.put("Cookie", "s=x0c13tiklp; xq_a_token=de90555db6be8468f9a557415617881f70b443e6; xqat=de90555db6be8468f9a557415617881f70b443e6; xq_r_token=d1a3ea03b24ded7fb10c12c8d638b00099a41b40; xq_is_login=1; u=3421799886; xq_token_expire=Thu%20Aug%2011%202016%2008%3A46%3A16%20GMT%2B0800%20(CST); bid=754085c15977a54bb49961eb90a228e3_iqpvt57y; __utmt=1; snbim_minify=true; __utma=1.220775250.1468716362.1468716362.1468849785.2; __utmb=1.4.10.1468849785; __utmc=1; __utmz=1.1468716362.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); Hm_lvt_1db88642e346389874251b5a1eded6e3=1468716362,1468849623; Hm_lpvt_1db88642e346389874251b5a1eded6e3=1468849819");
        headerMap.put("Host", "xueqiu.com");
        headerMap.put("Origin", "https://xueqiu.com");
        headerMap.put("Referer", "https://xueqiu.com/portfolios");
        headerMap.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2774.3 Safari/537.36");
        headerMap.put("X-Requested-With", "XMLHttpRequest");



        List<DockInfo> list =  FileUtil.loadDockFromConf(null);
        for(DockInfo dockInfo : list){
            String url = "https://xueqiu.com/service/poster";
            Map<String,String> createMap = new HashMap<String,String>();
            createMap.put("url","/stock/portfolio/addstock.json");
            createMap.put("data[pids]","1");
            createMap.put("data[pnames]","沪深");
            createMap.put("data[code]",dockInfo.getCode());
            createMap.put("data[comment]","");
            createMap.put("data[sprice]","");
            createMap.put("data[bprice]","");
            createMap.put("data[targetpercent]","7");
            createMap.put("data[isnotice]","1");
            createMap.put("data[_]","1468849933322");
            HttpClientUtil httpClientUtil = new HttpClientUtil();
            String httpOrgCreateTestRtn = httpClientUtil.doPost(url, createMap, "utf-8",headerMap);
            Thread.sleep(2000);
            System.out.println(dockInfo.getCode() +"添加成功");
            System.out.println("result:" + httpOrgCreateTestRtn);

        }
    }
}
