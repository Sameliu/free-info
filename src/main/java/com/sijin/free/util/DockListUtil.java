package com.sijin.free.util;

import com.sijin.free.po.DockInfo;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sijinzhang on 16/6/19.
 */
public class DockListUtil {
    //http://www.szse.cn/szseWeb/ShowReport.szse?SHOWTYPE=xlsx&CATALOGID=1110&tab2PAGENUM=1&ENCODE=1&TABKEY=tab2
    //http://query.sse.com.cn/security/stock/downloadStockListFile.do?csrcCode=&stockCode=&areaName=&stockType=1
    static HttpClientPoolUtill httpClient = new HttpClientPoolUtill(1,2);
    static String url_all = "http://ctxalgo.com/api/stocks";


    public static List<DockInfo> getDockListDefaultAll() throws Exception{
        List<DockInfo> list = new ArrayList<DockInfo>();
        String[] strstock;
        String dock_result = httpClient.get(url_all, "gbk", 250000, 30000,null);
        if(StringUtils.isNotBlank(dock_result)){
            String ss = dock_result.substring(dock_result.indexOf("{") + 1, dock_result.indexOf("}"));
            String[] arr = ss.split(",");
            int length = arr.length;
            for(int i=0;i<length; i++){
                DockInfo dockInfo = new DockInfo();
                strstock = arr[i].split(":");
                if(strstock.length >=2){
                    String name = convert(strstock[1].replaceAll("\"",""));
                    if(name.contains("ST")){continue;}
                    dockInfo.setCode(strstock[0].replaceAll("\"","").trim());
                    dockInfo.setName(name);
                    list.add(dockInfo);
                }
            }
        }
        return list;
    }

    public static Map<String, DockInfo> getDockMap(HttpClientPoolUtill httpClient) throws Exception{

        Map<String, DockInfo> map = new HashMap<String, DockInfo>();
        String[] strstock;
        String dock_result = httpClient.get(url_all, "gbk", 250000, 30000,null);
        if(StringUtils.isNotBlank(dock_result)){
            String ss = dock_result.substring(dock_result.indexOf("{") + 1, dock_result.indexOf("}"));
            String[] arr = ss.split(",");
            int length = arr.length;
            for(int i=0;i<length; i++){
                DockInfo dockInfo = new DockInfo();
                strstock = arr[i].split(":");
                if(strstock.length >=2){
                    dockInfo.setCode(strstock[0].replaceAll("\"","").trim());
                    dockInfo.setName(convert(strstock[1].replaceAll("\"","")));
                    map.put(dockInfo.getCode(),dockInfo);
                }
            }
        }

        return map;
    }


    public static String convert(String utfString){
        StringBuilder sb = new StringBuilder();
        int i = -1;
        int pos = 0;
        while((i=utfString.indexOf("\\u", pos)) != -1){
            sb.append(utfString.substring(pos, i));
            if(i+5 < utfString.length()){
                pos = i+6;
                sb.append((char)Integer.parseInt(utfString.substring(i+2, i+6), 16));
            }
        }
        return sb.toString().trim();
    }
}
