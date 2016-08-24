package com.sijin.free.service;

import com.sijin.free.po.DockInfo;
import com.sijin.free.po.DockMA;
import com.sijin.free.strategy.Strategy;
import com.sijin.free.util.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by sijinzhang on 16/7/14.
 */
public class StrategyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StrategyService.class);
    private static final Logger monitorLog = LoggerFactory.getLogger("monitorLog");

    public static void handle(Strategy strategy) throws Exception{
        if(strategy == null){return;}
        List<DockInfo> list  = new ArrayList<DockInfo>();
        if(strategy.getUseFileType().getType() == 1){ //获取输入信息
            list = FileUtil.loadDockFromConf(null);
        }else if(strategy.getUseFileType().getType() == 2){
            list = FileUtil.loadDockList("/Users/sijinzhang/study/free-info/whitelist.properties");
        }else if(strategy.getUseFileType().getType() == 3){
            list = FileUtil.loadDockList("/Users/sijinzhang/git/datapp/free-info/src/main/resources/gainian.properties");

        }else {
            list = DockListUtil.getDockList();
        }
        if(CollectionUtils.isEmpty(list)){return;}

        List<DockMA> dockMAList = TaskService.executeYahoTask(list,null);   //多线程爬取结果
        if(CollectionUtils.isEmpty(dockMAList)){return;}

        if(strategy.getCompartorType() != 0) {  //排序
            Comparator comparator = new ComparatorListSort(strategy.getCompartorType());
            Collections.sort(dockMAList, comparator);
        }

        LOGGER.info("\r\n"+ "统计结果：" + "\r\n" );
        for(DockMA d : dockMAList){     //生成统计日志
            System.out.println(d.toString());
            monitorLog.info(d.toString());
        }
        if(!StringUtils.isEmpty(strategy.getLogfile())){
            FileUtil.renameLogFile(strategy.getLogfile());
        }

        if(strategy.isGenerhtml()){ //是否生成html
            List<String> ma= new ArrayList<String>();
            for(DockMA dockma : dockMAList){
                if(dockma.getMa5().doubleValue() >= dockma.getPrice() && dockma.getMa10().doubleValue() >=dockma.getPrice() &&
                        dockma.getMa20().doubleValue() >= dockma.getPrice() && dockma.getMa30().doubleValue() >=dockma.getPrice()){
                    ma.add(dockma.getCode());
                    HtmlUtil.generateHtml(ma, strategy.getfName());
                    if(strategy.isSendMail()){  //是否发报警
                        MailUtil.sendMessage("低于均线统计监控",dockma.toString());
                    }
                }
            }

        }
    }

}
