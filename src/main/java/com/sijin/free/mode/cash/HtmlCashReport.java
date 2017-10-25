package com.sijin.free.mode.cash;

import com.sijin.free.po.DockInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sijinzhang on 16/11/15.
 */
public class HtmlCashReport {

    static List<DockInfo> inCash1List =  new ArrayList<>();
    static List<DockInfo> inCash3List =  new ArrayList<>();
    static List<DockInfo> inCash5List =  new ArrayList<>();
    static List<DockInfo> outCash3List =  new ArrayList<>();
    static List<DockInfo> outCash5List =  new ArrayList<>();



    public static void main(String[] args) {
        report();

    }

    private static void report() {
        inCash1List =  getCashList(1);


    }

    private static List<DockInfo> getCashList(int i) {





        return null;
    }

    private static void html(){

    }
}
