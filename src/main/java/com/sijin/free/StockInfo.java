package com.sijin.free;

import com.sijin.free.util.CovertUtil;

import java.text.MessageFormat;

/**
 * Created by sijinzhang on 16/12/27.
 */
public class StockInfo {
    //300223,600566,300045,603005，300288
    static String code = "000970";
    static String xueqiu_url  = "https://xueqiu.com/S/{0}";
    static String eastmoney_url = "http://quote.eastmoney.com/{0}.html";
    static String doctor_url= "http://doctor.10jqka.com.cn/{0}/";
    static String iwencai_url = "http://www.iwencai.com/stockpick/search?typed=1&preParams=&ts=1&f=3&qs=index_rewrite&selfsectsn=&querytype=&searchfilter=&tid=stockpick&w={0}";
    static String iwencai_liyun_url = "http://www.iwencai.com/stockpick/search?tid=stockpick&qs=stockpick_diag&ts=1&w={0}";
    static String gainian_url = "http://www.iwencai.com/stockpick/search?typed=1&preParams=&ts=1&f=1&qs=result_rewrite&selfsectsn=&querytype=&searchfilter=&tid=stockpick&w={0}+%E6%89%80%E5%B1%9E%E6%A6%82%E5%BF%B5";
    static String caiwu_url = "http://stockpage.10jqka.com.cn/{0}/finance/";
    static String dongfang_url = "http://data.eastmoney.com/stockcomment/{0}.html";


    public static void main(String[] args) {

        String _code = CovertUtil.covertCode(code);
        System.out.println("雪球：" + MessageFormat.format(xueqiu_url, _code));
        System.out.println();
        System.out.println("爱问财竞争分析：" + MessageFormat.format(iwencai_url,code));
        System.out.println();
        System.out.println("爱问企业利润分析：" + MessageFormat.format(iwencai_liyun_url,code));
        System.out.println();
        System.out.println("同花顺牛股诊断: " + MessageFormat.format(doctor_url, code));
        System.out.println();
        System.out.println("东方财富网: " + MessageFormat.format(eastmoney_url, _code));
        System.out.println();
        System.out.println("所属概念: " + MessageFormat.format(gainian_url, code));
        System.out.println();
        System.out.println("财务分析: " + MessageFormat.format(caiwu_url, code));
        System.out.println();
        System.out.println("机构参与度: " + MessageFormat.format(dongfang_url, code));
        System.out.println();
    }
}
