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

    public static void handle(Strategy strategy) throws Exception{
        if(strategy == null){return;}
        List<DockInfo> list  = null;
        if(strategy.getUseFileType().getType() == 1){ //获取输入信息
            list = FileUtil.loadDockFromConf(null);
        }else if(strategy.getUseFileType().getType() == 2){
            list = FileUtil.loadDockList("/Users/sijinzhang/study/free-info/whitelist.properties");
        }else if(strategy.getUseFileType().getType() == 3){
            list = FileUtil.loadDockList("/Users/sijinzhang/git/datapp/free-info/src/main/resources/gainian.properties");
        }else {
            list = DockListUtil.getDockListDefaultAll();
        }
        if(CollectionUtils.isEmpty(list)){return;}

        List<DockMA> dockMAList = TaskService.executeYahoTask(list,null);   //多线程爬取结果
        if(CollectionUtils.isEmpty(dockMAList)){return;}
        while (true){   //如果有失败的，进行重试
            if(RemedyUtil.empty()){
                break;
            }
            List<DockInfo> reList = RemedyUtil.toArrayList();
            LOGGER.info("睡眠5秒，然后处理List,大小是{}",reList.size());
            Thread.sleep(5000);
            dockMAList.addAll(TaskService.executeYahoTask(reList, null));
            RemedyUtil.clear();

        }
        List<DockMA> newList = strategy.getReport().yunsuan(dockMAList,strategy);

        if(strategy.getCompartorType() != 0) {  //排序
            Comparator comparator = new ComparatorListSort(strategy.getCompartorType());
            Collections.sort(newList, comparator);
        }


        if(strategy.isGenerhtml()){ //是否生成html
            HtmlUtil.generateHtml(newList, strategy.getHtmlFileName());
        }

        if(strategy.isSendMail()){ //是否发送邮件
            MailUtil.sendMessage("低于均线统计监控",newList.toString());
        }
    }

}
