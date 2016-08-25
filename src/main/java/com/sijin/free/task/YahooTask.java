package com.sijin.free.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sijin.free.po.DockMA;
import com.sijin.free.po.yahoo.YahooDock;
import com.sijin.free.util.DateUtils;
import com.sijin.free.util.HttpClientPoolUtill;
import com.sijin.free.util.SystemConfig;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.Callable;

/**
 * Created by sijinzhang on 16/6/10.
 */
public class YahooTask implements Callable<DockMA> {
    private static final Logger LOGGER = LoggerFactory.getLogger(YahooTask.class);
    static HttpClientPoolUtill httpClient = new HttpClientPoolUtill(50,25);
    public static String url ="http://hq.sinajs.cn/list=";

    private String code;
    private String name;

    private int threadNum;
    static DecimalFormat df = new DecimalFormat("######0.00");
    public YahooTask(String code, String name, int threadNum) {
        this.code = code;
        this.name = name.trim();
        this.threadNum = threadNum;

    }

    public DockMA call() throws Exception {
        Thread.sleep(new Random().nextInt(2000) + 1);
        Thread.currentThread().setName("thread[" + threadNum + "]");
        DockMA dockMA = null;

        try {
            String sockCode = code.substring(2,code.length()).concat(".").concat(code.substring(0, 2));
            sockCode = sockCode.replaceAll("sh", "SS");
            dockMA = deal(sockCode);
            // dockMA = dealNew(sockCode);
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
        dockMA.setHighPirce(new Double(df.format(phighlist.get(phighlist.size() - 1))));
        Double low = plowlist.get(0);
        //Double weight = Double.valueOf(SystemConfig.getValue("weight","1"));
        dockMA.setLowPirce(new Double(df.format(low)));

        Double deviation = (dockMA.getLowPirce() - dockMA.getPrice()) / dockMA.getPrice(); //最低价高于当前价偏离情况
        dockMA.setDeviation(deviation);
        return dockMA;
    }


    private DockMA deal(String sockCode) throws Exception{
        DockMA dockMA = new DockMA();
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
        String _url = "http://real-chart.finance.yahoo.com/table.csv?s={0}&a={1}&b={2}&c={3}&d={4}&b={5}&c={6}&g=d&ignore=.csv";
        String url = MessageFormat.format(_url, sockCode.toUpperCase(), a, b, c, d, e, f);
        String s = httpClient.get(url, "gbk", 5000, 3000);
        if(!StringUtils.isBlank(s)){     //Date,      Open, High, Low,  Close,Volume,Adj Close
            String[] arr = s.split("\n");
            if(arr.length >=2 && !arr[0].contains("Date")){
                LOGGER.info("bad data is " + code + "[" +name+"]");
                return dockMA;
            }
            List<Double> priceList = new ArrayList<Double>();
            Double high =0D;
            Double low =999D;
            for(int i =1 ;i<arr.length; i++){
                String[] info = arr[i].split(",");
                if(Integer.valueOf(info[5]).intValue() !=0){
                    if(high < Double.valueOf(info[2])){
                        high = Double.valueOf(info[2]);
                    }
                    if(low > Double.valueOf(info[3])){
                        low = Double.valueOf(info[3]);
                    }
                    priceList.add(Double.valueOf(info[4]));
                    LOGGER.info(code+"["+name+"]["+info[0]+"]["+info[1]+"]----->" + info[4]);
                }
                if (priceList.size() >=30){
                    break;
                }
            }
            dockMA.setHighPirce(high);
            dockMA.setLowPirce(low);
            dockMA.setPrice(priceList.get(0));
            Double deviation = (dockMA.getLowPirce() - dockMA.getPrice()) / dockMA.getPrice(); //最低价高于当前价偏离情况
            dockMA.setDeviation(new Double(df.format(deviation)));
            dockMA = setPriceAndRage(dockMA,priceList);
        }else {
            LOGGER.info("bad data is " + code + "[" +name+"]");
        }

        return dockMA;
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
                if(i<30){sum30 += plist.get(i);}
               if(i>=30){
                   break;
               }
            }
            Double ma5 = sum5 / 5;
            Double ma10 = sum10 / 10;
            Double ma20 = sum20 / 20;
            Double ma30 = sum30 / 30;
            Double range5 = (ma5 - pnow) / pnow; //五日均价高于当前价多少
            Double range10 = (ma10- pnow) / pnow; //十日均价高于当前价多少
            Double range20 = (ma20 - pnow) / pnow; //二十日均价高于当前价多少
            Double range30 = (ma30 - pnow) / pnow; //三十日均价高于当前价多少
            dockMA.setMa5(new Double(df.format(ma5)));
            dockMA.setMa10(new Double(df.format(ma10)));
            dockMA.setMa20(new Double(df.format(ma20)));
            dockMA.setMa30(new Double(df.format(ma30)));
            dockMA.setRange5(new Double(df.format(range5)));
            dockMA.setRange10(new Double(df.format(range10)));
            dockMA.setRange20(new Double(df.format(range20)));
            dockMA.setRange30(new Double(df.format(range30)));
            dockMA.setPrice(new Double(df.format(pnow)));
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
