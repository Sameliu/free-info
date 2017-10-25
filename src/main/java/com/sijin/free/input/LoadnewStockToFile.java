package com.sijin.free.input;

import com.sijin.free.po.DockMA;
import com.sijin.free.util.CovertUtil;
import com.sijin.free.util.HtmlUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.text.html.HTML;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sijinzhang on 16/11/3.
 */
public class LoadnewStockToFile {

    public static String url = "http://quote.cfi.cn/drawchartany.aspx?nodeid=223&pageindex=";
    public static StringBuffer buffer = new StringBuffer();
    public static List<DockMA> codeList = new ArrayList<>();
    static BufferedWriter bufferedWriter = null;

    static {
        try {
            bufferedWriter = new BufferedWriter(new FileWriter("/Users/sijinzhang/git/datapp/free-info/src/test/resources/newstock.properties"));
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public static void main(String[] args){

        try {
            for(int i=1;i<=25;i++){
                String _url = url+i;
                handle(_url);
            }
            HtmlUtil.generateSimpleHtml(codeList,"newstock.html");
//            for(DockMA d : codeList){
//                bufferedWriter.write(d.getCode().concat(",").concat(d.getName()));
//                bufferedWriter.newLine();
//            }
//            bufferedWriter.flush();
//            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("结束");
        System.exit(0);
    }

    private static void handle(String url) throws IOException {
        Document document = Jsoup.connect(url).get();
        Elements table = document.select("table");
        table.select("td[colspan=\"14\"]").remove();

        Elements hrefs = table.select("a");
        for(Element a : hrefs){
            String code  = parse("\\d+", a.attr("href"));
            DockMA d = new DockMA();
            d.setCode(CovertUtil.covertCode(code));
            d.setName(a.text());
            codeList.add(d);
        }

        buffer.append(table.outerHtml());
    }

    public static String parse(String regex,String input){
        Pattern p =  Pattern.compile(regex);
        Matcher m = p.matcher(input);
        if(m.find()){
            return m.group(0);
        }
        return "";
    }
}


























