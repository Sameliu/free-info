package com.sijin.free.service;

import com.sijin.free.po.DockInfo;
import com.sijin.free.util.FileUtil;
import com.sijin.free.util.HtmlUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sijinzhang on 16/8/2.
 */
public class GenerHtmlService {

    public static void main(String[] args) {
        List<DockInfo> list = FileUtil.loadDockFromConf(null);
        List<String> ma = new ArrayList<>();
        for(DockInfo d :list){
            ma.add(d.getCode());
        }
        System.out.println(ma.size());
        HtmlUtil.generateHtml(ma, "ma.html");

    }
}
