package com.sijin.free.main;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sijin.free.po.yahoo.YahooDock;
import com.sijin.free.util.HttpClientPoolUtill;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by sijinzhang on 16/5/17.
 */
public class Test {
    static NumberFormat nf   =   NumberFormat.getPercentInstance();
    public static void main(String[] args) {
        nf.setMinimumFractionDigits(4);

        System.out.println(nf.format(-0.006920887326611047));



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
