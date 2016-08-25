package com.sijin.free.util;

import com.sijin.free.po.DockMA;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sijinzhang on 16/7/18.
 */
public class HtmlUtil {

    private static StringBuilder sb = new StringBuilder();
    private static PrintStream printStream = null;
    static NumberFormat df   =   NumberFormat.getPercentInstance();

    private static String dir ="reptor/";

    public static void generateHtml(List<DockMA> list,String fileName){
        df.setMinimumFractionDigits(2);
        File f = new File(dir+ fileName);
        html(list,f);
    }


    public static void html(List<DockMA> list, File f) {
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
        sb.append("TH{text-align:center;border-right:solid 1 #000000;border-bottom:solid 10 #000000;}");
        sb.append("TD{text-align:center;font:normal;border-right:solid 1 #000000;border-bottom:solid 10 #000000;}");
        sb.append("</style></head>");
        sb.append("<body bgcolor=\"#FFF8DC\">");
        sb.append("<div align=\"center\">");
        sb.append("<br/>");
        sb.append("<br/>");


    }


    private static void content(List<DockMA> list) {
        sb.append("共有" + list.size() + "个");
        sb.append("<table border=\"1\">");
        sb.append("<tr>");
        sb.append("<th>序号</th>");
        sb.append("<th>k线图</th>").append("<th>MA5<br/>R5</th>").append("<th>MA10<br/>R10</th>");
        sb.append("<th>MA20<br/>R20</th>").append("<th>MA30<br/>R30</th>");
        sb.append("<th>price</th>").append("<th>low<br />high</th>").append("<th>deviation</th>");
        sb.append("<th width='90px' align='center'>操作</th>");
        sb.append("</tr>");
        for(int i =0;i<list.size();i++){
            sb.append("<tr>");
            sb.append("<td>" + (i + 1) + "</td>");
            sb.append("<td>");
            sb.append("<img src =\"http://image.sinajs.cn/newchart/daily/n/"+list.get(i).getCode()+".gif\"");
            sb.append("</td>");
            sb.append(dockTd(list.get(i)));
            sb.append("<td align='center'>");
            sb.append("<a target=\"_blank\" href=\"http://f10.eastmoney.com/f10_v2/CompanyManagement.aspx?code="+list.get(i).getCode()+"\">高管</a><br/><br/>");
            sb.append("<a target=\"_blank\" href=\"http://f10.eastmoney.com/f10_v2/FinanceAnalysis.aspx?code="+list.get(i).getCode()+"\">财报</a><br/><br/>");
            sb.append("<a target=\"_blank\" href=\"http://f10.eastmoney.com/f10_v2/OperationsRequired.aspx?code="+list.get(i).getCode()+"\">必读</a><br/><br/>");
            sb.append("<a target=\"_blank\" href=\"https://xueqiu.com/S/"+list.get(i).getCode()+"\">雪球</a><br/><br/>");
            sb.append("</td>");
            sb.append("</tr>");
        }
        sb.append("</table>");
    }

    private static String dockTd(DockMA dockMA) {
        StringBuffer b = new StringBuffer();
        b.append("<td>").append(dockMA.getMa5()).append("<br />----<br />").append(df.format(dockMA.getRange5())).append("</td>");
        b.append("<td>").append(dockMA.getMa10()).append("<br />----<br />").append(df.format(dockMA.getRange10())).append("</td>");
        b.append("<td>").append(dockMA.getMa20()).append("<br />----<br />").append(df.format(dockMA.getRange20())).append("</td>");
        b.append("<td>").append(dockMA.getMa30()).append("<br />----<br />").append(df.format(dockMA.getRange30())).append("</td>");
        b.append("<td style=\"background:#D580FE\">").append(dockMA.getPrice()).append("</td>");
        b.append("<td>").append(dockMA.getLowPirce()).append("<br />----<br />").append(dockMA.getHighPirce()).append("</td>");
        b.append("<td>").append(df.format((dockMA.getHighPirce() - dockMA.getLowPirce())/dockMA.getPrice())).append("<br />----<br />").append(df.format(dockMA.getDeviation())).append("</td>");
        return b.toString();
    }


    private static void end() {
        sb.append("<br/><br/>");
        sb.append("</div></body></html>");
        printStream.println(sb.toString());
    }
}
