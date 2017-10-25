package com.sijin.free.util;

import com.sijin.free.po.DockInfo;

/**
 * Created by sijinzhang on 16/5/6.
 */
public class CovertUtil {


    /**
     * covert
     * @param input
     * @return
     */
    public static DockInfo covertToDockInfo(String input){

        String[] arr = input.split("\"");
        if(arr.length<2){
            return null;
        }
        String str = arr[1];
        String[] strArrDockinfo = str.split(",");
        if(strArrDockinfo.length<4){
            return null;
        }
        DockInfo dockInfo = new DockInfo();
        dockInfo.setName(strArrDockinfo[0]);
        dockInfo.setsPrice(Double.valueOf(strArrDockinfo[1]));
        dockInfo.setYesterdayprice(Double.valueOf(strArrDockinfo[2]));
        dockInfo.setPrice(Double.valueOf(strArrDockinfo[3]));
        dockInfo.sethPrice(Double.valueOf(strArrDockinfo[4]));
        dockInfo.setlPrice(Double.valueOf(strArrDockinfo[5]));
        dockInfo.setDatep(strArrDockinfo[30]);
        dockInfo.setTimep(strArrDockinfo[31]);
        String s = arr[0].split("str_")[1];
        dockInfo.setCode(s.substring(0,s.length()-1));
        dockInfo.setRate((dockInfo.getPrice()-dockInfo.getsPrice())/dockInfo.getsPrice()*100);
        return  dockInfo;
    }

    public static String covertCode(String input){
        String code = "";
        if(input.startsWith("0") || input.startsWith("3")){
            return  "sz" + input;
        }
        if(input.startsWith("6")){
            return  "sh" + input;
        }
        return code;

    }
}
