package com.sijin.free.service;

import com.sijin.free.po.DockInfo;
import com.sijin.free.util.*;

import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

/**
 * Created by sijinzhang on 16/5/6.
 */
public class GoodNewsService {

    static  HttpClientPoolUtill httpClient = new HttpClientPoolUtill(50,10);

    public static void main(String[] args) {
        try {
            List<DockInfo> list =  FileUtil.loadDockFromConf(null);
            getnews(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getnews(List<DockInfo> list) throws  Exception{
        String subject = "";
        List<String> messageList = new ArrayList<String>();
        List<DockInfo> dockInfolist = TaskService.executeSinaTask(list);
        for(DockInfo dockInfo : dockInfolist){
            if(dockInfo.getlPrice().compareTo(dockInfo.getMywantbuy()) <=0 &&
                    dockInfo.getPrice().compareTo(dockInfo.getMywantbuy()) <=0 ){
                subject = dockInfo.getName() + "[" + dockInfo.getCode() + "]" + " price is " + dockInfo.getPrice() + ",less than " + dockInfo.getMywantbuy() ;
                dockInfo.setMailMessage(subject);
                messageList.add(subject);
            }
            if(dockInfo.gethPrice().compareTo(dockInfo.getMywantsale()) >=0 &&
                    dockInfo.getPrice().compareTo(dockInfo.getMywantsale()) >=0 ){
                subject = dockInfo.getName() + "[" + dockInfo.getCode() + "]" + " price is " + dockInfo.getPrice() + ",more than " + dockInfo.getMywantsale() ;
                dockInfo.setMailMessage(subject);
                messageList.add(subject);
            }
        }

        Collections.sort(dockInfolist, new Comparator<DockInfo>() {
            public int compare(DockInfo o1, DockInfo o2) {
                if (o1.getPrice() - o2.getPrice() >=0 ) {
                    return 1;
                }
                if (o1.getPrice() - o2.getPrice() < 0){
                    return -1;
                }

                return 0;
            }
        });

        for(int i=0;i<dockInfolist.size();i++){
            System.out.println("["+ (i+1) +"]->"+ dockInfolist.get(i));
            if(dockInfolist.get(i).getMailMessage()!=null){
                MailUtil.sendMessage(dockInfolist.get(i).getMailMessage(),dockInfolist.get(i).toString());
            }
        }

        for (String s : messageList){
            System.out.println(s);
        }

        System.out.println("------------------------------------>" + DateUtils.formatDateTime(new Date()) + "<-------------------------------------");
    }
}
