package com.sijin.free.main;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Author: 思进
 * @Description:
 * @Date: 下午11:36 2018/5/7
 */
public class GoldRunToHome {
    public static void main(String[] args) {
        String str = null;
        String path = "/Users/sijinzhang/codeStudy/study/free-info/src/main/resources/golds/all.txt";
        List<String> codes = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            while((str = br.readLine()) != null) {
                codes.addAll(parseStr(str));
            }
            html(codes);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static List<String> parseStr(String str) {
        List<String> codes = new ArrayList<>();
        JSONObject jsonObject = JSON.parseObject(str);
        Set<String> set = jsonObject.keySet();
        for (String key : set){
            String[] p = key.split("\\.");
            codes.add(p[1].toLowerCase() + p[0]);
            System.out.println(key);
        }
        return codes;
    }

    private static void html(List<String> codes) throws FileNotFoundException {
        File f = new File("/Users/sijinzhang/codeStudy/study/free-info/src/main/resources/golds/all.html");
        String dir  = f.getPath().substring(0,f.getPath().lastIndexOf("/"));
        File fdir = new File(dir);
        if(!fdir.exists()){
            fdir.mkdirs();
        }
        PrintStream printStream =  new PrintStream(new FileOutputStream(f.getPath()));
        String sb = content(codes);
        printStream.print(sb);
    }

    private static String content(List<String> codes) {
        String tdCon = "";
        String c = "";
        StringBuilder sb = new StringBuilder();
        sb.append("<table border=1><tr>");
        int total = codes.size() % 3 == 0 ? codes.size() / 3 : codes.size() / 3 + 1;
        int current =1;
        for(int i=0;i<codes.size(); i++){
            if(i % 3 ==0 && i!=0){
                ++current;
                sb.append("</tr><tr>");
            }
            c = codes.get(i).substring(2);

            tdCon =   "<p>" + current + "/" +total +"</p>";
            tdCon += "<a target='_blank' href = 'http://www.iwencai.com/stockpick/search?typed=1&preParams=&ts=1&f=1&qs=1&selfsectsn=&querytype=&searchfilter=&tid=stockpick&w="
                    .concat(c)
                    .concat("'>")
                    .concat(c);


            sb.append("<td><img width='500px' src ='http://image.sinajs.cn/newchart/weekly/n/").append(codes.get(i)).append(".gif'></td>");
            sb.append("<td>").append(tdCon).append("</td>");
        }
        sb.append("</tr></table>");
        return sb.toString();
    }
}
