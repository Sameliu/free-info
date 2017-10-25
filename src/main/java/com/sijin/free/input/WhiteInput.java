package com.sijin.free.input;

import ch.qos.logback.core.pattern.ConverterUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sijin.free.po.DockInfo;
import com.sijin.free.po.WhiteInputPo;
import com.sijin.free.util.CovertUtil;
import com.sijin.free.util.DockListUtil;
import com.sijin.free.util.HttpClientPoolUtill;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sijinzhang on 16/6/19.
 */
public class WhiteInput {

    static HttpClientPoolUtill httpClient = new HttpClientPoolUtill(40,20);
    static String reg  = "\"data\":(.*(\\s.*)*\\]).\"rtime";
    static BufferedWriter bufferedWriter = null;
    static Map<String, DockInfo> map = new HashMap<String, DockInfo>();
    static List<String> existslist  = new ArrayList<String>();
    static {
        try {
            bufferedWriter = new BufferedWriter(new FileWriter("/Users/sijinzhang/study/free-info/whitelist.properties"));
            map = DockListUtil.getDockMap(httpClient);

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    //概念系
    static String jg_url  = "http://q.10jqka.com.cn/interface/stock/detail/zdf/desc/{0}/3/jg";  //军工
    static String jcdl_url  = "http://q.10jqka.com.cn/interface/stock/detail/zdf/desc/{0}/3/jcdl";  //集成电路
    static String mygn_url  = "http://q.10jqka.com.cn/interface/stock/detail/zdf/desc/{0}/3/mygn";  //马云
    static String znyl_url  = "http://q.10jqka.com.cn/interface/stock/detail/zdf/desc/{0}/3/znyl";  //智能医疗
    static String jnhb_url  = "http://q.10jqka.com.cn/interface/stock/detail/zdf/desc/{0}/3/jnhb";  //节能环保


    //行业系
    static String bdtjyj_url  = "http://q.10jqka.com.cn/interface/stock/detail/zxj/desc/{0}/1/bdtjyj";  //半导体及元件
    static String jsjyy_url  = "http://q.10jqka.com.cn/interface/stock/detail/zxj/desc/{0}/1/jsjyy";  //计算机应用
    static String ylqxfw_url  = "http://q.10jqka.com.cn/interface/stock/detail/zxj/desc/{0}/1/ylqxfw";  //医疗器械服务
    static String hbgc_url  = "http://q.10jqka.com.cn/interface/stock/detail/zxj/desc/{0}/1/hbgc";  //环保工程


    //证监会
    static String zjh_url = "http://q.10jqka.com.cn/interface/stock/detail/zdf/desc/{0}/4/xxjsy"; //信息技术



    public static void main(String[] args) {

        try{
            write("证监会信息技术行业", zjh_url, 6);
            write("军工概念", jg_url, 3);
            write("集成电路概念", jcdl_url, 1);
            write("马云概念", mygn_url, 1);
            write("智能医疗", znyl_url, 1);
            write("节能环保", jnhb_url, 1);

            write("半导体及元件行业", bdtjyj_url, 2);
            write("计算机应用行业", jsjyy_url, 2);
            write("医疗器械服务行业", ylqxfw_url, 1);
            write("环保工程行业", hbgc_url, 2);
            bufferedWriter.close();
        }catch (Exception e){
            e.printStackTrace();

        }

    }

    public static void write(String gn,String url,int total) throws Exception{
        System.out.println(url);
        bufferedWriter.write("############"+gn+"开始##########");
        bufferedWriter.newLine();
        for (int i=1;i<=total;i++){
            String _url = MessageFormat.format(url,i);
            writeWhiteList(_url);
        }
        bufferedWriter.write("############"+gn+"结束##########");
        bufferedWriter.newLine();
    }


    private static void writeWhiteList(String url) throws Exception {

        String result = httpClient.get(url, "utf-8", 25000, 30000,null);
        String _result = result.substring(result.indexOf("\"data\""), result.indexOf("\"cate\""));
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(_result);
        String input = "";
        while (m.find()) {
            input = m.group(1);
        }
        List<WhiteInputPo> list = JSON.parseArray(input, WhiteInputPo.class);
        for(WhiteInputPo whiteInputPo : list) {
            if(existslist.contains(whiteInputPo.getStockcode())){
                continue;
            }
            existslist.add(whiteInputPo.getStockcode());
            String code = CovertUtil.covertCode(whiteInputPo.getStockcode());
            if(whiteInputPo.getStockcode() == null || map.get(code) == null ){
                System.out.println(url + "======" + whiteInputPo.toString());
                continue;
            }

            bufferedWriter.write(code.concat(",").concat(map.get(code).getName()));
            bufferedWriter.newLine();
        }
    }
}
