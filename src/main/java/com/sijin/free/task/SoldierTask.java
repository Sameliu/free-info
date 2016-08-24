package com.sijin.free.task;

import com.sijin.free.po.DockMA;
import com.sijin.free.po.RedSoldier;
import com.sijin.free.po.RedSoldierItem;
import com.sijin.free.util.DateUtils;
import com.sijin.free.util.HttpClientPoolUtill;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by sijinzhang on 16/6/10.
 */
public class SoldierTask implements Callable<RedSoldier> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SoldierTask.class);
    static HttpClientPoolUtill httpClient = new HttpClientPoolUtill(200,50);


    private String code;
    private String name;

    private int threadNum;

    public SoldierTask(String code, String name, int threadNum) {
        this.code = code;
        this.name = name;
        this.threadNum = threadNum;
    }

    public RedSoldier call() throws Exception {
        Thread.sleep(50);
        Thread.currentThread().setName("thread ------[" + threadNum + "]------>>>>>  ");
        RedSoldier redSoldier = new RedSoldier();
        try {
            redSoldier  =  cell(code, name);
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.info("bad data is " + code + ",name is " +name);
        }
        return redSoldier;
    }


    public static RedSoldier cell(String code, String name)throws Exception {
        RedSoldier redSoldier = new RedSoldier();
        Date date = new Date();
        List<RedSoldierItem> redSoldierItemList = new ArrayList<RedSoldierItem>();
        for(int i=0;i<=5; i++) {
            int wk = DateUtils.getChineseWeekday(date);
            if(wk ==  7 || wk == 1 ){
                continue;
            }
            String d = DateUtils.formatDate(DateUtils.addDays(new Date(), -i));
            Double price = getstockinfobyDay(code, d);
            if(price.doubleValue() > -1D){
                LOGGER.info("code->"+code + "name->"+ name + "[" +d + "--->" + price +"]");
                RedSoldierItem redSoldierItem = new RedSoldierItem();
                redSoldierItem.setDate(d);
                redSoldierItem.setPrice(price);
                redSoldierItemList.add(redSoldierItem);
            }
            if(redSoldierItemList.size() >=4){
                break;
            }
        }
        redSoldier.setCode(code);
        redSoldier.setName(name);
        redSoldier.setRedSoldierItemList(redSoldierItemList);
        redSoldier.setPrice(redSoldierItemList.get(0).getPrice());
        return redSoldier;
    }

    public static Double getstockinfobyDay(String code, String d) throws Exception {
        Double price = -1D;
        String url = "http://market.finance.sina.com.cn/downxls.php?date="+d +"&symbol="+code;
        String s = httpClient.get(url, "gbk", 250000, 30000);
        if(!StringUtils.isBlank(s)){
            String[] arr = s.split("\n");
            if(arr.length >=2){
                String[] info = arr[1].split("\t");
                if(info.length >=2){
                    price = Double.valueOf(info[1]);
                }
            }
        }
        return price;
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
