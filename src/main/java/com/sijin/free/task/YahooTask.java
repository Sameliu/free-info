package com.sijin.free.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sijin.free.po.DockInfo;
import com.sijin.free.po.DockMA;
import com.sijin.free.po.yahoo.YahooDock;
import com.sijin.free.util.DateUtils;
import com.sijin.free.util.HttpClientPoolUtill;
import com.sijin.free.util.RemedyUtil;
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
        Thread.sleep(new Random().nextInt(3000) + 1);
        Thread.currentThread().setName("thread[" + threadNum + "]");
        DockMA dockMA = new DockMA();
        dockMA.setCode(code);
        dockMA.setName(name);
        try {
            String sockCode = code.substring(2,code.length()).concat(".").concat(code.substring(0, 2));
            sockCode = sockCode.replaceAll("sh", "SS");
            //dockMA = deal(sockCode,dockMA);
            dockMA = dealNew(sockCode,dockMA);
        }catch (Exception e){
            LOGGER.info("bad data cod is:{},name is:{}", code, name);

        }
        LOGGER.info("thread finish {}[{}]",code , name);
        return dockMA;
    }


    private DockMA dealNew(String sockCode, DockMA dockMA) throws Exception{
        Thread.sleep(new Random().nextInt(4000) + 1);
        String url = "https://query2.finance.yahoo.com/v8/finance/chart/{0}?formatted=true&crumb=s8TWg1rzwZX&lang=en-US&region=US&interval=1d&events=div%7Csplit&range=3mo&corsDomain=finance.yahoo.com";
        String _url = MessageFormat.format(url,sockCode);
        //System.out.println(_url);
        Map<String,String> headerMap = new HashMap<String, String>();
        headerMap.put(":authority","query2.finance.yahoo.com");
        headerMap.put(":path","/v8/finance/chart/002202.sz?formatted=true&crumb=s8TWg1rzwZX&lang=en-US&region=US&interval=1d&events=div%7Csplit&range=3mo&corsDomain=finance.yahoo.com");
        headerMap.put(":scheme","https");
        headerMap.put("accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        headerMap.put("accept-encoding","gzip, deflate, sdch, br");
        headerMap.put("accept-language","zh-CN,zh;q=0.8,en;q=0.6");
        headerMap.put("cache-control","max-age=0");
        headerMap.put("cookie","B=8nrrem1blv9lc&b=3&s=hs; PRF=t%3D600460.SS%252B600115.SS");
        headerMap.put("upgrade-insecure-requests","1");
        headerMap.put("user-agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2906.0 Safari/537.36");
        String result = "";
        for(int i=0;i<3;i++){
            try {
                Thread.sleep((i+1)*2000);
                result = httpClient.get(_url,"utf-8", 5000, 3000,headerMap);
                break;
            }catch (Exception e){
                LOGGER.info("请求发生{}次错误,info:[{}->{}]",(i+1),code,name);
                if(i == 2){
                    DockInfo dockInfo = new DockInfo();
                    dockInfo.setCode(code);
                    dockInfo.setName(name);
                    RemedyUtil.add(dockInfo);
                    LOGGER.info("put it to voector~");
                }
               LOGGER.error("Exception is {}", e.getMessage());
                continue;
            }

        }
        JSONObject chat = (JSONObject) JSON.parseObject(result).get("chart");
        JSONArray jsonArray = (JSONArray) chat.get("result");
        if(jsonArray.size() == 0){
            return dockMA;
        }
        YahooDock yahooDock = JSON.parseObject(jsonArray.get(0).toString(), YahooDock.class);
        List<Double> plist = filter(yahooDock.getIndicators().getQuote().get(0).getClose());
        List<Double> plowlist = filter(yahooDock.getIndicators().getQuote().get(0).getLow());
        List<Double> phighlist = filter(yahooDock.getIndicators().getQuote().get(0).getHigh());
        Collections.sort(plowlist);
        Collections.sort(phighlist);
        Collections.reverse(plist);
        dockMA = setPriceAndRage(dockMA, plist);
        dockMA.setHighPirce(phighlist.get(phighlist.size() - 1));
        Double low = plowlist.get(0);
        //Double weight = Double.valueOf(SystemConfig.getValue("weight","1"));
        dockMA.setLowPirce(low);

        Double deviation = (dockMA.getLowPirce() - dockMA.getPrice()) / dockMA.getPrice(); //最低价高于当前价偏离情况
        dockMA.setDeviation(deviation);
        return dockMA;
    }

    private List<Double> filter(List<Double> list) {
        List<Double> tmp = new ArrayList<>();
        for(int i =0; i< list.size(); i++){
            if(list.get(i) != null){
                tmp.add(new Double(df.format(list.get(i))));
            }
        }
        return tmp;
    }


    private DockMA deal(String sockCode, DockMA dockMA) throws Exception{
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
        String s = httpClient.get(url, "gbk", 5000, 3000,null);
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
            dockMA.setPrice(Double.parseDouble(priceList.get(0).toString()));
            Double deviation = (dockMA.getLowPirce() - dockMA.getPrice()) / dockMA.getPrice(); //最低价高于当前价偏离情况
            dockMA.setDeviation(deviation);
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
