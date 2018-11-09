package com.sijin.free.main;



import com.sijin.free.util.HttpClientPoolUtill;
import com.sijin.free.util.SystemConfig;

import java.awt.*;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 思进
 * @Description:
 * @Date: 下午11:01 2018/1/29
 */
public class SpiderByQuestionV1 {

    //stockpick_diag&ts=1&w=\d+
    //http://www.iwencai.com/stockpick/robot-search?w=%E5%B8%82%E7%9B%88%E7%8E%87%E5%A4%A7%E4%BA%8E100%E5%B0%8F%E4%BA%8E120&querytype=stock&robotResultPerpage=30
    static  String url ="http://www.iwencai.com/data-robot/chat/?source=Ths_iwencai_Xuangu&question={0}&user_id=&log_info=%7B%22other_info%22%3A%22%7B%5C%22eventId%5C%22%3A%5C%22iwencai_pc_send_click%5C%22%2C%5C%22ct%5C%22%3A1520342981353%7D%22%2C%22other_utype%22%3A%22random%22%2C%22other_uid%22%3A%22Ths_iwencai_Xuangu_bw6sqz857g7fnn9j3acqtnj24z6ixgc0%22%7D&user_name=ssss&version=1.3&_={1}";
    static HttpClientPoolUtill httpClient = new HttpClientPoolUtill(2,1);
    public static void main(String[] args) {


        String _url = MessageFormat.format(url,"市盈率大于100小于120", System.currentTimeMillis());
        String result = getResult(_url);
    }

    private static String getResult(String url) {
        String result = "";
        try {
            result = httpClient.get(url,buildMap());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static Map<String, String> buildMap() {

        Map<String, String> map = new HashMap<>();
        map.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        map.put("Accept-Encoding","gzip, deflate");
        map.put("Accept-Language","zh-CN,zh;q=0.9,en;q=0.8");
        map.put("Cache-Control","max-age=0");
        map.put("Connection","keep-alive");
        map.put("Cookie","___rl__test__cookies=1520341010423; other_uid=Ths_iwencai_Xuangu_bw6sqz857g7fnn9j3acqtnj24z6ixgc0; other_uname=xeaql1ikwj; cid=ah9g6plt5rqqr7o2b43dqb02f71508420186; ComputerID=ah9g6plt5rqqr7o2b43dqb02f71508420186; guideState=1; OUTFOX_SEARCH_USER_ID_NCOO=196040945.3663267; PHPSESSID=1059c19203f856a66ae5f30892c39b75; v=AhoJQAMRFIvg_pgn9QQO9jVMbcs4S54lEM8SySSTxq14l7R9DNvuNeBfYtr3");
        map.put("Host","www.iwencai.com");
        map.put("Upgrade-Insecure-Requests","1");
        map.put("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");

        return  map;
    }


}
