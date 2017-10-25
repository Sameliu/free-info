package com.sijin.free.util;

import com.sijin.free.po.DockInfo;
import org.apache.commons.lang.StringUtils;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by sijinzhang on 16/7/2.
 */
public class FileUtil {


    /**
     * 从配置文件读取
     * @return
     */
    public static List<DockInfo> loadDockFromConf(String path) {
        if(path == null){
            path = "/Users/sijinzhang/study/free-info/conf.properties";
        }

        List<DockInfo> list = new ArrayList<DockInfo>();
        Map<String,DockInfo> map = new HashMap<>();
        String str = null;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path));
            while((str = br.readLine()) != null) {
                if(str.startsWith("#")|| StringUtils.isEmpty(str)){
                    continue;
                }
                DockInfo dockInfo  = new DockInfo();
                String[] p = str.split(",");
                dockInfo.setCode(p[0]);
                dockInfo.setMywantbuy(Double.valueOf(p[1]));
                dockInfo.setMywantsale(Double.valueOf(p[2]));
                dockInfo.setName(p[3]);
                if(p.length >=5){
                    dockInfo.setHaveBuy(Double.valueOf(p[4]));
                }
                map.put(dockInfo.getCode(), dockInfo);
            }
        } catch (Exception e) {
            System.out.println(str);
            e.printStackTrace();
        }finally {
            if(br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        for(Map.Entry<String,DockInfo> entry : map.entrySet()){
            list.add(entry.getValue());
        }

        return list;
    }




    /**
     * 加载白名单
     * @param filePath
     * @return
     */
    public static List<DockInfo> loadDockList(String filePath) {
        List<DockInfo> whriteList  = new ArrayList<DockInfo>();
        Map<String,DockInfo> map = new HashMap<>();
        String str = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));

            while((str = br.readLine()) != null) {
                if(!StringUtils.isEmpty(str) && !str.startsWith("#")){
                    String[] p = str.split(",");
                    if(p.length <1){continue;}
                    String first = p[0].substring(0,1);
                    Pattern pattern = Pattern.compile("[a-zA-Z0-9]");
                    DockInfo dockInfo = new DockInfo();
                    if(pattern.matcher(first).matches()){
                        String c = "";
                        if(p[0].startsWith("s")){
                            c = p[0];
                        }else {
                            c = p[0].startsWith("6") ? "sh" + p[0] : "sz" + p[0];
                        }
                        dockInfo.setCode(c);
                    }else {
                        dockInfo.setCode(p[0]);
                    }
                    if(p.length >=3){
                        dockInfo.setMywantbuy(Double.parseDouble(p[1]));
                        dockInfo.setMywantsale(Double.parseDouble(p[2]));
                    }
                    map.put(dockInfo.getCode(), dockInfo);
                }
            }
        } catch (IOException e) {
            System.out.println(str);
            e.printStackTrace();
        }

        for(Map.Entry<String,DockInfo> entry : map.entrySet()){
            whriteList.add(entry.getValue());
        }
        return  whriteList;
    }
}
