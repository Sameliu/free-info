package com.sijin.free.input;

import com.sijin.free.http.HttpClientUtil;
import com.sijin.free.po.DockInfo;
import com.sijin.free.util.FileUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sijinzhang on 16/7/18.
 */
public class LoadStockTo10JQKA {


    public static void main(String[] args) throws InterruptedException {
        Map<String,String> headerMap = new HashMap<String, String>();

        headerMap.put("Accept", "text/javascript, application/javascript, application/ecmascript, application/x-ecmascript, */*; q=0.01");
        headerMap.put("Accept-Encoding", "gzip, deflate, sdch");
        headerMap.put("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
        headerMap.put("Connection", "keep-alive");
        headerMap.put("Cookie", "__utma=156575163.1852195103.1466342197.1466342197.1466342197.1; __utmz=156575163.1466342197.1.1.utmcsr=10jqka.com.cn|utmccn=(referral)|utmcmd=referral|utmcct=/; concern=a%3A5%3A%7Bs%3A6%3A%22stock%2F%22%3Bs%3A24%3A%22%25BB%25A6%25C9%25EE%25B9%25C9%25CA%25D0%22%3Bs%3A12%3A%22stock%2Fzjhhy%2F%22%3Bs%3A30%3A%22%25D6%25A4%25BC%25E0%25BB%25E1%25D0%25D0%25D2%25B5%22%3Bs%3A12%3A%22stock%2Fthshy%2F%22%3Bs%3A30%3A%22%25CD%25AC%25BB%25A8%25CB%25B3%25D0%25D0%25D2%25B5%22%3Bs%3A9%3A%22stock%2Ffl%2F%22%3Bs%3A12%3A%22%25B7%25D6%25C0%25E0%22%3Bs%3A9%3A%22stock%2Fdy%2F%22%3Bs%3A24%3A%22%25B5%25D8%25D3%25F2%25B0%25E5%25BF%25E9%22%3B%7D; spversion=20130314; historystock=600807%7C*%7C300033%7C*%7C002514%7C*%7C000970%7C*%7C300264; user=MDrVxbquWEE6Ok5vbmU6NTAwOjM1MTU5ODUzNjo3LDExMTExMTExMTExLDQwOzQ0LDExLDQwOzYsMSw0MDs1LDEsNDA6Ojo6MzQxNTk4NTM2OjE0Njg4NTcwNDI6OjoxNDY2NjEwOTAwOjg2NDAwOjA6NDg1NzJkNTRjMDY1ZmQwNjQ3Mjk3ZDA5YTU1ZGNlNTk6ZGVmYXVsdF8yOjE%3D; userid=341598536; u_name=%D5%C5%BA%AEXA; escapename=%25u5f20%25u5bd2XA; ticket=be666c51a527c5a543c3bf3e07481e14; Hm_lvt_78c58f01938e4d85eaf619eae71b4ed1=1467382466,1467461560,1467731666,1468856930; Hm_lpvt_78c58f01938e4d85eaf619eae71b4ed1=1468857046; Hm_lvt_f79b64788a4e377c608617fba4c736e2=1466610814,1466610943,1468856933,1468857046; Hm_lpvt_f79b64788a4e377c608617fba4c736e2=1468857046");
        headerMap.put("Host", "stock.10jqka.com.cn");
        headerMap.put("Referer", "http://stock.10jqka.com.cn/my/");
        headerMap.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2774.3 Safari/537.36");
        headerMap.put("X-Requested-With","XMLHttpRequest");
        String url = "";
        List<DockInfo> list =  FileUtil.loadDockFromConf(null);
        for(DockInfo dockInfo : list){
            String code = dockInfo.getCode().replace("sh","").replace("sz","");
            url = "http://stock.10jqka.com.cn/self.php?stockcode="+code+"&op=add&&jsonp=jQuery18307287785667915558_1468857045457&_=1468857098171";
            HttpClientUtil httpClientUtil = new HttpClientUtil();
            String httpOrgCreateTestRtn = httpClientUtil.doGet(url, "utf-8",headerMap);
            Thread.sleep(2000);
            System.out.println(dockInfo.getCode()+": ["+dockInfo.getName() +"] -->添加成功");
            System.out.println("result:" + httpOrgCreateTestRtn);

        }
    }
}
