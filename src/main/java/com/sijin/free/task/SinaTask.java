package com.sijin.free.task;

import com.sijin.free.po.DockInfo;
import com.sijin.free.po.DockMA;
import com.sijin.free.util.CovertUtil;
import com.sijin.free.util.DateUtils;
import com.sijin.free.util.HttpClientPoolUtill;
import com.sijin.free.util.MailUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by sijinzhang on 16/6/10.
 */
public class SinaTask implements Callable<DockInfo> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SinaTask.class);
    static HttpClientPoolUtill httpClient = new HttpClientPoolUtill(50,25);
    public static String url ="http://hq.sinajs.cn/list=";

    private DockInfo dockInfo;

    private int threadNum;

    public SinaTask(DockInfo dockInfo, int threadNum) {
        this.dockInfo =dockInfo;
        this.threadNum = threadNum;
    }

    public DockInfo call() throws Exception {
        Thread.sleep(50);
        Thread.currentThread().setName("thread["+ threadNum +"]");
        DockInfo dockInfo =  httpget();
        return dockInfo;
    }

    public DockInfo httpget(){
        DockInfo inf = new DockInfo();
        String posturl = "";
        try {
            posturl = url+ dockInfo.getCode();
            String result = httpClient.get(posturl);
            inf = CovertUtil.covertToDockInfo(result);
            inf.setMywantbuy(dockInfo.getMywantbuy());
            inf.setMywantsale(dockInfo.getMywantsale());
        } catch (Exception e) {
            System.out.println(posturl);
            e.printStackTrace();
        }
        return  inf;
    }


    public DockInfo getDockInfo() {
        return dockInfo;
    }

    public void setDockInfo(DockInfo dockInfo) {
        this.dockInfo = dockInfo;
    }

    public int getThreadNum() {
        return threadNum;
    }

    public void setThreadNum(int threadNum) {
        this.threadNum = threadNum;
    }
}
