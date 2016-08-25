package com.sijin.free.service;

import com.sijin.free.po.DockInfo;
import com.sijin.free.po.DockMA;
import com.sijin.free.po.RedSoldier;
import com.sijin.free.task.SinaTask;
import com.sijin.free.task.SoldierTask;
import com.sijin.free.task.YahooTask;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.*;

/**
 * Created by sijinzhang on 16/7/14.
 */
public class TaskService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);
    static ExecutorService executor = new ThreadPoolExecutor(3, Integer.MAX_VALUE,
            60000L, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>());


    public static List<RedSoldier> executeSoldierTask(List<DockInfo> list) throws Exception{

        List<RedSoldier> dockInfoList = new ArrayList<RedSoldier>();
        int size = list.size();
        LOGGER.info("all docklist size is ---->" + size);
        List<SoldierTask> taskList = new ArrayList<SoldierTask>();

        int j = 1;
        for(int i=0; i<size; i++){
            if(list.get(i).getCode().equals("sh000001") ||list.get(i).getCode().equals("sz399001") ){
                continue;
            }
            SoldierTask task = new SoldierTask(list.get(i).getCode(),list.get(i).getName(),i);
            taskList.add(task);
        }
        List<Future<RedSoldier>> resultList= executor.invokeAll(taskList);
        for (int i=0; i<resultList.size(); i++){
            Future<RedSoldier> future=resultList.get(i);
            RedSoldier redSoldier = future.get();
            if(redSoldier.getPrice() ==null || Double.valueOf(redSoldier.getPrice()).compareTo(0D) <= 0 ){
                LOGGER.info("stop data::" + redSoldier.getCode() + "[ " + redSoldier.getName() + "]");
                continue;
            }
            dockInfoList.add(redSoldier);
        }
        return dockInfoList;
    }


    public static List<DockInfo> executeSinaTask(List<DockInfo> list) throws  Exception{

        List<DockInfo> dockInfoList = new ArrayList<DockInfo>();
        int size = list.size();
        LOGGER.info("all docklist size is ---->" + size);
        List<SinaTask> taskList = new ArrayList<SinaTask>();

        int j = 1;
        for(int i=0; i<size; i++){
            if(list.get(i).getCode().equals("sh000001") ||list.get(i).getCode().equals("sz399001") ){
                continue;
            }
            SinaTask task = new SinaTask(list.get(i),j);
            taskList.add(task);
        }
        List<Future<DockInfo>> resultList= executor.invokeAll(taskList);
        for (int i=0; i<resultList.size(); i++){
            Future<DockInfo> future=resultList.get(i);
            DockInfo dockInfo = future.get();
            if(Double.valueOf(dockInfo.getPrice()).compareTo(0D) == 0 ){
                LOGGER.info("stop data::" + dockInfo.getCode() + "[ " + dockInfo.getName() + "]");
                continue;
            }
            dockInfoList.add(dockInfo);
        }
        return dockInfoList;
    }


    public static List<DockMA> executeYahoTask(List<DockInfo> list,Map<String,String> blackmap) throws  Exception{

        List<DockMA> dockMAList = new ArrayList<DockMA>();
        int size = list.size();
        LOGGER.info("all docklist size is ---->" + size);
        List<YahooTask> taskListfirst=new ArrayList<YahooTask>();
        List<YahooTask> taskListsecond=new ArrayList<YahooTask>();

        int j = 1;
        for(int i=0; i<size; i++){
            if(list.get(i).getCode().equals("sh000001") ||list.get(i).getCode().equals("sz399001") ){
                continue;
            }
            String code = list.get(i).getCode();
            String name = list.get(i).getName();
            if(blackmap !=null && blackmap.containsKey(code)){
                LOGGER.info("ingore " + code +"[" + name + "]");
                continue;
            }
            LOGGER.info("add " + code + "[" + name + "]");
            YahooTask task = new YahooTask(code,name,j);

            j++;
            if(i < 1500){
                taskListfirst.add(task);
            }
            if(i > 1500){
                taskListsecond.add(task);
            }

        }
        List<Future<DockMA>> resultList= executor.invokeAll(taskListfirst);
        if (!CollectionUtils.isEmpty(taskListsecond)) {
            Thread.sleep(2000L);
            List<Future<DockMA>> resultListSecond=executor.invokeAll(taskListsecond);
            executor.shutdown();

            resultList.addAll(resultListSecond);
        }else {
            executor.shutdown();
        }

        for (int i=0; i<resultList.size(); i++){
            Future<DockMA> future=resultList.get(i);
            DockMA dma=future.get();
            if(dma.getMa30() == null || dma.getMa30().doubleValue() == 0 ){
                LOGGER.info("bad data::" + dma.getCode() + "[ " + dma.getName() + "]");
                continue;
            }
            dockMAList.add(dma);
        }
        return dockMAList;
    }


}
