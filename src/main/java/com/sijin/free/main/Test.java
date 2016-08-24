package com.sijin.free.main;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sijin.free.po.yahoo.YahooDock;
import com.sijin.free.util.HttpClientPoolUtill;

/**
 * Created by sijinzhang on 16/5/17.
 */
public class Test {
    static HttpClientPoolUtill httpClient = new HttpClientPoolUtill(100,50);
    public static void main(String[] args) {
        String url = "https://query2.finance.yahoo.com/v8/finance/chart/600460.SS?formatted=true&crumb=s8TWg1rzwZX&lang=en-US&region=US&interval=1d&events=div%7Csplit&range=90d&corsDomain=finance.yahoo.com";
        try {
            String result = httpClient.get(url);
            JSONObject chat = (JSONObject) JSON.parseObject(result).get("chart");
            JSONArray jsonArray = (JSONArray) chat.get("result");
//            String input = jsonArray.get(0).toString().replaceAll("\\[\\{", "{").replaceAll("\\}\\]","}");
//            YahooDock yahooDock = JSON.parseObject(input, YahooDock.class);

            String input = jsonArray.get(0).toString();
            YahooDock yahooDock = JSON.parseObject(input, YahooDock.class);
            System.out.println(input);
        } catch (Exception e) {
            e.printStackTrace();
        }


//        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy", Locale.ENGLISH);
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        try {
//            Date date = dateFormat.parse("Aug 28, 2015");
//            System.out.println(df.format(date));
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
    }
}
