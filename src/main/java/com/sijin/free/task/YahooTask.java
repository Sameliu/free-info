package com.sijin.free.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sijin.free.po.DockInfo;
import com.sijin.free.po.DockMA;
import com.sijin.free.po.yahoo.YahooDock;
import com.sijin.free.util.CovertUtil;
import com.sijin.free.util.DateUtils;
import com.sijin.free.util.HttpClientPoolUtill;
import com.sijin.free.util.SystemConfig;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.Callable;

/**
 * Created by sijinzhang on 16/6/10.
 */
public class YahooTask implements Callable<DockMA> {
    private static final Logger LOGGER = LoggerFactory.getLogger(YahooTask.class);
    static HttpClientPoolUtill httpClient = new HttpClientPoolUtill(50,25);
    static NumberFormat nf   =   NumberFormat.getPercentInstance();
    static DecimalFormat df = new DecimalFormat("00.00");
    public static String url ="http://hq.sinajs.cn/list=";

    private String code;
    private String name;

    private int threadNum;

    public YahooTask(String code, String name, int threadNum) {
        this.code = code;
        this.name = name.trim();
        this.threadNum = threadNum;
        nf.setMinimumFractionDigits(2);
    }

    public DockMA call() throws Exception {
        Thread.sleep(new Random().nextInt(1000) + 1);
        Thread.currentThread().setName("thread[" + threadNum + "]");
        DockMA dockMA = null;

        try {
            String sockCode = code.substring(2,code.length()).concat(".").concat(code.substring(0, 2));
            sockCode = sockCode.replaceAll("sh", "SS");
            //dockMA = deal(sockCode);
            dockMA = dealNew(sockCode);
            dockMA.setCode(code);
            dockMA.setName(name);
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.info("bad data is " + code + ",name is " +name);

        }
        //LOGGER.info(dockMA.toString());
        return dockMA;
    }


    private DockMA dealNew(String sockCode) throws Exception{
        DockMA dockMA = new DockMA();
       String url = "https://query2.finance.yahoo.com/v8/finance/chart/{0}?formatted=true&crumb=s8TWg1rzwZX&lang=en-US&region=US&interval=1d&events=div%7Csplit&range=3mo&corsDomain=finance.yahoo.com";
       String _url = MessageFormat.format(url,sockCode);
        String result = httpClient.get(_url,"utf-8", 5000, 3000);
        JSONObject chat = (JSONObject) JSON.parseObject(result).get("chart");
        JSONArray jsonArray = (JSONArray) chat.get("result");
        YahooDock yahooDock = JSON.parseObject(jsonArray.get(0).toString(), YahooDock.class);
        List<Double> plist = yahooDock.getIndicators().getQuote().get(0).getClose();
        List<Double> plowlist = yahooDock.getIndicators().getQuote().get(0).getLow();
        List<Double> phighlist = yahooDock.getIndicators().getQuote().get(0).getHigh();
        Collections.sort(plowlist);
        Collections.sort(phighlist);
        Collections.reverse(plist);
        dockMA = setPriceAndRage(dockMA, plist);
        dockMA.setHighPirce(phighlist.get(phighlist.size()-1));
        Double low = plowlist.get(0);
        Double weight = Double.valueOf(SystemConfig.getValue("weight","1"));
        dockMA.setLowPirce(low*weight);
        return dockMA;
    }


    private DockMA deal(String sockCode) throws Exception{
        List<Double> plist = getPriceList(sockCode);
        DockMA dockMA = new DockMA();
        dockMA = setPriceAndRage(dockMA,plist);
       return dockMA;
    }

    private List<Double> getPriceList(String sockCode) throws Exception {

        String dstart = DateUtils.formatDate(DateUtils.addDays(new Date(), -90));
        String dend = DateUtils.formatDate(DateUtils.addDays(new Date(), 0));
        String[] dstartArr = dstart.split("-");
        String[] dendArr = dend.split("-");
        String a =dstartArr[2];
        String b =dstartArr[1];
        String c =dstartArr[0];
        String d =dendArr[2];
        String e =dendArr[1];
        String f =dendArr[0];

        List<Double> priceList = new ArrayList<Double>();
        String _url = "http://real-chart.finance.yahoo.com/table.csv?s={0}&a={1}&b={2}&c={3}&d={4}&b={5}&c={6}&g=d&ignore=.csv";
        String url = MessageFormat.format(_url, sockCode.toUpperCase(), a, b, c, d, e, f);

        String s = httpClient.get(url, "gbk", 5000, 3000);

        if(!StringUtils.isBlank(s)){     //Date,      Open, High, Low,  Close,Volume,Adj Close
            String[] arr = s.split("\n");
            if(arr.length >=2 && !arr[0].contains("Date")){
                LOGGER.info("bad data is " + code + "[" +name+"]");
                return priceList;
            }

            for(int i =1 ;i<arr.length; i++){
                String[] info = arr[i].split(",");
                if(Integer.valueOf(info[5]).intValue() !=0){
                    priceList.add(Double.valueOf(info[4]));
                    LOGGER.info(code+"["+name+"]["+info[0]+"]["+info[1]+"]----->" + info[4]);
                }
                if (priceList.size() >=30){
                    break;
                }
            }
        }else {
            LOGGER.info("bad data is " + code + "[" +name+"]");
        }
        return priceList;
    }

    private DockMA setPriceAndRage(DockMA dockMA,List<Double> plist){
        try {
            if(plist ==null || plist.size() == 0){
                return dockMA;
            }

            Double pnow = 0D;
            Double sum5 = 0D;
            Double sum10 = 0D;
            Double sum20 = 0D;
            Double sum30 = 0D;
            for(int i=0;i<plist.size();i++){
                if(i == 0){pnow = plist.get(i);}
                if(i<5){sum5 += plist.get(i);}
                if(i<10){sum10 += plist.get(i);}
                if(i<20){sum20 += plist.get(i);}
                sum30 += plist.get(i);
            }
            BigDecimal ma5 = new BigDecimal(df.format(sum5 / 5));
            BigDecimal ma10 = new BigDecimal(df.format(sum10 / 10));
            BigDecimal ma20 = new BigDecimal(df.format(sum20 / 20));
            BigDecimal ma30 = new BigDecimal(df.format(sum30 / 30));
            BigDecimal range5 = new BigDecimal(df.format((ma5.doubleValue() - pnow) / pnow)); //五日均价与当前价偏离情况
            BigDecimal range10 = new BigDecimal(df.format((ma10.doubleValue() - pnow) / pnow)); //十日均价与当前价偏离情况
            BigDecimal range20 = new BigDecimal(df.format((ma20.doubleValue() - pnow) / pnow)); //二十日均价与当前价偏离情况
            BigDecimal range30 = new BigDecimal(df.format((ma30.doubleValue() - pnow) / pnow)); //三十日均价与当前价偏离情况
            dockMA.setMa5(ma5);
            dockMA.setMa10(ma10);
            dockMA.setMa20(ma20);
            dockMA.setMa30(ma30);
            dockMA.setRange5(range5);
            dockMA.setRange10(range10);
            dockMA.setRange20(range20);
            dockMA.setRange30(range30);
            dockMA.setPrice(pnow);
        }catch (Exception e){
            e.printStackTrace();
        }

        return dockMA;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getThreadNum() {
        return threadNum;
    }

    public void setThreadNum(int threadNum) {
        this.threadNum = threadNum;
    }
}
