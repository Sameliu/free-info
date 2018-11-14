package com.sijin.wencai.love.you;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sijin.free.http.HttpClientUtil;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 思进
 * @Description:
 * @Date: 上午12:27 2018/11/9
 */
public class WenCai {

    private static  HttpClientUtil http = new HttpClientUtil();
    private static String url ="https://www.iwencai.com/data-robot/get-fusion-data";


    private static String url_info = "https://www.iwencai.com/stockpick/cache?token={0}&p=1&perpage=70&changeperpage=1";

    public static void main(String[] args) {
        Map<String,String> header = new HashMap<>();
        header = (Map) JSONObject.parse("{\"Accept\":\"application/json, text/javascript, */*; q=0.01\",\n" +
                "            \"Accept-Encoding\":\"gzip, deflate, br\",\n" +
                "            \"Accept-Language\":\"zh-CN,zh;q=0.9\",\n" +
                "            \"Connection\":\"keep-alive\",\n" +
                "            'User-Agent': \"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36\",\n" +
                "           \"Cookie\":\"other_uid=Ths_iwencai_Xuangu_bw6sqz857g7fnn9j3acqtnj24z6ixgc0; other_uname=xeaql1ikwj; cid=ah9g6plt5rqqr7o2b43dqb02f71508420186; ComputerID=ah9g6plt5rqqr7o2b43dqb02f71508420186; OUTFOX_SEARCH_USER_ID_NCOO=196040945.3663267; PHPSESSID=341c556a23700b3791706bfa8532a1af; user=MDp6aGFuZ2hhbl8xOTg6Ok5vbmU6NTAwOjQ0MDA5OTMwMzo3LDExMTExMTExMTExLDQwOzQ0LDExLDQwOzYsMSw0MDs1LDEsNDA7MSwxLDQwOzIsMSw0MDszLDEsNDA7NSwxLDQwOzgsMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDEsNDA6Mzo6OjQzMDA5OTMwMzoxNTQxNjk3NzE2Ojo6MTUxNDI2MzUwMDo2MDQ4MDA6MDoxODQ1NTM4MGVmNWFlNGJkNjQ2MTNjOWQwNTA0NWMxZWY6ZGVmYXVsdF8yOjA%3D; userid=430099303; u_name=zhanghan_198; escapename=zhanghan_198; ticket=fafa788b37255569b25e6615b5898b9b; v=AgATDu3TTydZgjMjWjTqtBFF14XRieQ8hmw4V3qRz2Ku6q6zIpm049Z9COHJ\"}");

//        Map<String,String> params = new HashMap<>();
//        params.put("w","科创概念股有哪些");
//       String rs =  http.doPost(url,params,"utf-8",header);
//       String token = JSON.parseObject(rs).getJSONObject("data").getJSONObject("wencai_data").getJSONObject("result").getString("token");
//
//        System.out.println(token);
//        System.out.println(token);
//       String _url =  MessageFormat.format(url_info,token);
//        System.out.println(_url);

        String _url = "https://www.iwencai.com/stockpick/cache?token=fb90748a2e45b37744df551d6814204f&p=1&perpage=70&changeperpage=1";
        String rsss =http.doGet(_url,"utf-8",header);
        System.out.println(rsss);

    }
}
