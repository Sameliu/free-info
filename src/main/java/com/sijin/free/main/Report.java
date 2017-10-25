package com.sijin.free.main;

import com.sijin.free.po.DockInfo;
import com.sijin.free.util.*;

import java.text.DecimalFormat;
import java.util.*;

/**
 * 每天发统计邮件
 * Created by sijinzhang on 16/8/24.
 */
public class Report {

    static HttpClientPoolUtill httpClient = new HttpClientPoolUtill(2,1);
    static DecimalFormat a = new DecimalFormat("0.00%");
    public static String url ="http://hq.sinajs.cn/list=";
    public static void main(String[] args) {
        try {
            handle();
        }catch (Exception e){
            e.printStackTrace();
        }
        System.exit(0);
    }

    private static void handle() throws Exception{
        List<DockInfo> dockInfoList =  FileUtil.loadDockFromConf("/Users/sijinzhang/git/datapp/free-info/src/main/resources/fe.properties");
        StringBuffer sb = new StringBuffer();
        Map<String,DockInfo> map = new HashMap<>();
        for(DockInfo dockInfo : dockInfoList){
            sb.append(dockInfo.getCode()).append(",");
            map.put(dockInfo.getCode(),dockInfo);
        }
        String posturl = url+ sb.deleteCharAt(sb.length()-1).toString();
        String result = httpClient.get(posturl);
        String arrp[] = result.split(";\n");
        List<DockInfo> resultList = new ArrayList<>();
        for(String r : arrp){
            DockInfo inf = new DockInfo();
            inf = CovertUtil.covertToDockInfo(r);
            inf.setMywantbuy(map.get(inf.getCode()).getMywantbuy());
            inf.setMywantsale(map.get(inf.getCode()).getMywantsale());
            resultList.add(inf);
        }

        String title = "每日统计[" + DateUtils.formatDate(new Date()) + "]";
        StringBuffer sbf = new StringBuffer("<P><P>" + title + "<P><P>");
        sbf.append("<table style=\"width:100%;\" cellpadding=\"05\" cellspacing=\"0\" border=\"1\" bordercolor=\"#000000\"><tbody>");
        sbf.append("<tr><th>名字</th><th>代码</th><th>卖出</th><th>买入</th><th>当前价</th><th>偏离</th><th>偏离度</th></tr>");
        for(DockInfo info : resultList){
            sbf.append("<tr>");
            sbf.append(genTd(info.getName())).append(genTd(info.getCode()))
                    .append(genTd(info.getMywantsale())).append(genTd(info.getMywantbuy())).append(genTd(info.getPrice()));
            Double s = Math.round((info.getPrice() - info.getMywantbuy())*100)/100.0;
            String d = a.format(Double.valueOf(s) /info.getPrice());
            sbf.append(genTd(s));
            sbf.append(genTd(d));
            sbf.append("</tr>");
        }
        sbf.append("</tbody></table>");
        MailUtil.sendMessage(title,sbf.toString());
    }

    private static String genTd(Object name) {
        return "<td>".concat(name.toString()).concat("</td>");
    }
}
