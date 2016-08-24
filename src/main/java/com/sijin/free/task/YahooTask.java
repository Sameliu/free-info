package com.sijin.free.task;

import com.sijin.free.po.DockInfo;
import com.sijin.free.po.DockMA;
import com.sijin.free.util.CovertUtil;
import com.sijin.free.util.DateUtils;
import com.sijin.free.util.HttpClientPoolUtill;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
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
        Thread.sleep(new Random().nextInt(1000)+1);
        Thread.currentThread().setName("thread["+ threadNum +"]");
        DockMA dockMA = new DockMA();
        dockMA.setCode(code);
        dockMA.setName(name);
        try {
            List<Double> plist = cell();
            dockMA = deal(plist);    //3.计算统计
        }catch (Exception e){
            try {
                List<Double> plist = cell();
                dockMA = deal(plist);    //3.计算统计
            }catch (Exception ee){
                ee.printStackTrace();
                LOGGER.info("bad data is " + code + ",name is " +name);
            }

        }
        return dockMA;
    }

    public List<Double> cell()throws Exception {
        String sockCode = code.substring(2,code.length()).concat(".").concat(code.substring(0, 2));
        sockCode = sockCode.replaceAll("sh","SS");


        List<Double> priceList = getPriceList(sockCode);
        return priceList;
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


    private DockMA deal(List<Double> plist) throws Exception{

        DockMA dockMA = new DockMA();
        dockMA.setCode(code);
        dockMA.setName(name);
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
        LOGGER.info(code + "["+name+"]:" +" ma30="+ ma30 +",range="+nf.format(range30)+",ma20="+ma20 +",range="+nf.format(range20)+",ma10="+ma10+",range="+nf.format(range10)+",ma5=" +ma5+",range="+nf.format(range5)+",prce="+pnow +",range="+nf.format(range20));
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
