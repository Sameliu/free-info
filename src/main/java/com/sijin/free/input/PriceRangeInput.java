package com.sijin.free.input;

import com.sijin.free.po.DockInfo;
import com.sijin.free.po.DockMA;
import com.sijin.free.util.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sijinzhang on 16/9/23.
 */
public class PriceRangeInput {
    static HttpClientPoolUtill httpClient = new HttpClientPoolUtill(2,1);
    public static String url ="http://hq.sinajs.cn/list=";

    static List<DockMA> mailist = new ArrayList<DockMA>();
    public static void main(String[] args) {

        try {
            List<DockInfo>  list =  DockListUtil.getDockListDefaultAll();
             input(list);
            HtmlUtil.generateSimpleHtml(mailist,"PriceRange6-10-weekly.html");
        }catch (Exception e){
            e.printStackTrace();

        }


    }

    private static void input(List<DockInfo> list) throws Exception{
        List<DockInfo> newList = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            newList.add(list.get(i));
            if(newList.size()>100){
                handle(newList);
                newList.clear();
            }
        }
        if(newList.size()>0){
            handle(newList);
        }
    }

    private static void handle(List<DockInfo> list) throws Exception{
        StringBuffer sb = new StringBuffer();
        Map<String,DockInfo> map = new HashMap<>();
        for(DockInfo dockInfo : list){
            sb.append(dockInfo.getCode()).append(",");
            map.put(dockInfo.getCode(),dockInfo);
        }
        String posturl = url+ sb.deleteCharAt(sb.length()-1).toString();
        String result = httpClient.get(posturl);
        String arrp[] = result.split(";\n");
        for(String r : arrp){
            DockInfo inf = CovertUtil.covertToDockInfo(r);
            if(inf != null && inf.getPrice()> 6 && inf.getPrice()< 10){
                DockMA dockMA = new DockMA();
                dockMA.setCode(inf.getCode());
                mailist.add(dockMA);
                System.out.println(inf.toString());
            }
        }
    }
}
