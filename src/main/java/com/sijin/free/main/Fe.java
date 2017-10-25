package com.sijin.free.main;

import com.sijin.free.po.DockInfo;
import com.sijin.free.util.*;
import org.apache.commons.collections.CollectionUtils;
import org.jsoup.helper.StringUtil;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by sijinzhang on 16/8/24.
 *增加高频监控
 * 每次只有十个
 *
 *
 */
public class Fe {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Fe.class);
    static HttpClientPoolUtill httpClient = new HttpClientPoolUtill(2,1);
    static Map<String,Long>  map = new HashMap<String,Long>();
    public static String url ="http://hq.sinajs.cn/list=";
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        try {
            all();
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("i have finished it,spends ----->" + (System.currentTimeMillis() - start) / 1000 + "s");
    }

    private static void all() throws Exception {


        while (true){
            if(!checkTime()){
                LOGGER.info("未到执行时间，睡眠1分钟");
                Thread.sleep(60000);
                continue;
            }

            List<DockInfo> list = FileUtil.loadDockList("/Users/sijinzhang/codeStudy/study/free-info/src/main/resources/abc.properties");
            if(!CollectionUtils.isEmpty(list)){
                try {
                    StringBuffer sb = new StringBuffer();
                    Map<String,DockInfo> map = new HashMap<>();
                    for(DockInfo dockInfo : list){
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
                        if(inf == null || inf.getCode()== null){{
                            continue;
                        }
                        }
                        try {
                            if(map.get(inf.getCode()).getMywantbuy() != null) {
                                inf.setMywantbuy(map.get(inf.getCode()).getMywantbuy());
                            }else {
                                inf.setMywantbuy(1.0D);
                            }
                            if(map.get(inf.getCode()).getMywantsale() != null){
                                inf.setMywantsale(map.get(inf.getCode()).getMywantsale());
                            }else {
                                inf.setMywantsale(100.0D);
                            }
                            if(map.get(inf.getCode()).getHaveBuy() !=  null){
                                inf.setHaveBuy(map.get(inf.getCode()).getHaveBuy());
                            }else {
                               // inf.setHaveBuy(40D);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            System.out.println(inf.toString());
                        }

                        resultList.add(inf);
                    }
                    while (true) {
                        try {
                            handle(resultList);
                            break;
                        }catch (Exception e){
                            LOGGER.info("error----------");
                        }

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
            Thread.sleep(5000);
        }
    }

    private static boolean checkTime() throws Exception{
        Date d = new Date();
        String date =  DateUtils.formatDate(d);
        Date dateStart_morining = DateUtils.parseDateTime(date + " 09:30:00");
        Date dateEnd_morining = DateUtils.parseDateTime(date + " 11:30:00");

        Date dateStart_afternoon = DateUtils.parseDateTime(date + " 13:00:00");
        Date dateEnd_afternoon = DateUtils.parseDateTime(date + " 15:00:00");
        if((d.after(dateStart_morining) && d.before(dateEnd_morining)) || (d.after(dateStart_afternoon) && d.before(dateEnd_afternoon))){
            return true;
        }

        return false;
    }

    private static void handle(List<DockInfo> dockInfolist) {
        String subject = "";
        List<String> messageList = new ArrayList<String>();
        for(DockInfo dockInfo : dockInfolist){
            if(dockInfo.gethPrice() == 0){
                continue;
            }
            String checkLess = checkless(dockInfo);
            if(!StringUtil.isBlank(checkLess)){
               // subject = dockInfo.getName() + "[" + dockInfo.getCode() + "]" + " price is " + dockInfo.getPrice() + ",less than " + dockInfo.getMywantbuy() ;
                dockInfo.setMailMessage(checkLess);
                messageList.add(checkLess);
            }
            String checkmore = checkmore(dockInfo);
            if(!StringUtil.isBlank(checkmore)){
                //subject = dockInfo.getName() + "[" + dockInfo.getCode() + "]" + " price is " + dockInfo.getPrice() + ",more than " + dockInfo.getMywantsale() ;
                dockInfo.setMailMessage(checkmore);
                messageList.add(checkLess);
            }
        }

        Collections.sort(dockInfolist, new Comparator<DockInfo>() {
            public int compare(DockInfo o1, DockInfo o2) {
                if (o1.getPrice() - o2.getPrice() >= 0) {
                    return 1;
                }
                if (o1.getPrice() - o2.getPrice() < 0) {
                    return -1;
                }

                return 0;
            }
        });

        for(int i=0;i<dockInfolist.size();i++){
            System.out.println("["+ (i+1) +"]->"+ dockInfolist.get(i));
            if(dockInfolist.get(i).getMailMessage()!=null){
                if(map.containsKey(dockInfolist.get(i).getCode())){
                    Long t1 = map.get(dockInfolist.get(i).getCode());
                    Long t2 = System.currentTimeMillis();
                    if((t2-t1)/1000 < 300) {
                        continue;
                    }
                }
                //MailUtil.sendMessage(dockInfolist.get(i).getMailMessage(), dockInfolist.get(i).toString());
                map.put(dockInfolist.get(i).getCode(),System.currentTimeMillis());
            }
        }

        for (String s : messageList){
            System.out.println(s);
        }

        System.out.println("------------------------------------>" + DateUtils.formatDateTime(new Date()) + "<-------------------------------------");
    }

    private static String checkmore(DockInfo dockInfo) {

        String msg =  "";
        if(dockInfo.getPrice().compareTo(dockInfo.getMywantsale()) >=0 ){
            msg = dockInfo.getName().concat("高出卖出价").concat("["+ dockInfo.getMywantsale().toString() +"]");
            return msg;
        }
        if(dockInfo.getHaveBuy() != null ){
            if( dockInfo.getPrice() > dockInfo.getHaveBuy() && (dockInfo.getPrice() - dockInfo.getHaveBuy()) / dockInfo.getPrice() >=0.0235){
                msg = dockInfo.getName().concat("高于买入价").concat("["+ dockInfo.getMywantsale().toString() +"]");
                return msg;
            }
        }

        return msg;
    }

    private static String checkless(DockInfo dockInfo) {
        String msg ="";
        if(dockInfo.getPrice().compareTo(dockInfo.getMywantbuy()) <=0 ){
            msg = dockInfo.getName().concat("低于入手价").concat("["+ dockInfo.getMywantsale().toString() +"]");
            return msg;
        }
        if(dockInfo.getHaveBuy() != null ){
            if(dockInfo.getHaveBuy() > dockInfo.getPrice() && (dockInfo.getHaveBuy() - dockInfo.getPrice()) / dockInfo.getPrice() >= 0.0175){
                msg = dockInfo.getName().concat("高于买入价").concat("["+ dockInfo.getMywantsale().toString() +"]");
                return msg;
            }
        }
        return msg;
    }
}


























