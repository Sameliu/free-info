package com.sijin.free.util;

import com.sijin.free.po.DockMA;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sijinzhang on 16/7/18.
 */
public class HtmlUtil {

    private static StringBuilder sb = new StringBuilder();
    private static PrintStream printStream = null;

    private static String dir ="reptor/";

    public static void generateHtml(List<String> list,String fileName){

        File f = new File(dir+ fileName);
        html(list,f);
    }


    public static void html(List<String> list, File f) {
        try {
            start(f);
            content(list);
            end();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void start(File f) throws Exception{
        String dir  = f.getPath().substring(0,f.getPath().lastIndexOf("/"));
        File fdir = new File(dir);
        if(!fdir.exists()){
            fdir.mkdirs();
        }
        printStream =  new PrintStream(new FileOutputStream(f.getPath()));
        sb.append("<html>");
        sb.append("<head>");
        sb.append("<title>k线统计</title>");
        sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
        sb.append("<style type=\"text/css\">");
        sb.append("TABLE{border-collapse:collapse;border-left:solid 1 #000000; border-top:solid 1 #000000;padding:15px;}");
        sb.append("TH{border-right:solid 1 #000000;border-bottom:solid 10 #000000;}");
        sb.append("TD{font:normal;border-right:solid 1 #000000;border-bottom:solid 10 #000000;}");
        sb.append("</style></head>");
        sb.append("<body bgcolor=\"#FFF8DC\">");
        sb.append("<div align=\"center\">");
        sb.append("<br/>");
        sb.append("<br/>");


    }


    private static void content(List<String> list) {
        sb.append("<table border=\"1\">");
        for(int i =0;i<list.size();i++){
            sb.append("<tr>");
            sb.append("<td>");
            sb.append("<img src =\"http://image.sinajs.cn/newchart/daily/n/"+list.get(i)+".gif\"");
            sb.append("</td>");
            sb.append("<td>");
            sb.append("<a target=\"_blank\" href=\"http://f10.eastmoney.com/f10_v2/CompanyManagement.aspx?code="+list.get(i)+"\">高管</a><br/><br/>");
            sb.append("<a target=\"_blank\" href=\"http://f10.eastmoney.com/f10_v2/FinanceAnalysis.aspx?code="+list.get(i)+"\">财报</a><br/>");
            sb.append("</td>");
            sb.append("</tr>");
        }
        sb.append("</table>");
    }


    private static void end() {
        sb.append("<br/><br/>");
        sb.append("</div></body></html>");
        printStream.println(sb.toString());
    }
}
