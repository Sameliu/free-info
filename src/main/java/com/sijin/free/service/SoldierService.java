package com.sijin.free.service;

import com.sijin.free.po.DockInfo;
import com.sijin.free.po.RedSoldier;
import com.sijin.free.po.RedSoldierItem;
import com.sijin.free.util.FileUtil;

import java.util.List;

/**
 * Created by sijinzhang on 16/7/26.
 */
public class SoldierService {

    public static void main(String[] args) {
        List<DockInfo> dockInfoList = FileUtil.loadDockList("/Users/sijinzhang/study/free-info/conf.properties");
        try {
            List<RedSoldier> list = TaskService.executeSoldierTask(dockInfoList);
            cell(list);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private static void cell(List<RedSoldier> list) {
        for(RedSoldier redSoldier : list){
            List<RedSoldierItem> itemList = redSoldier.getRedSoldierItemList();
            if(itemList.size()>=4){
                if(itemList.get(0).getPrice() > itemList.get(1).getPrice() &&
                        itemList.get(1).getPrice()  > itemList.get(2).getPrice()  &&
                        itemList.get(2).getPrice() > itemList.get(3).getPrice() )

                System.out.println(redSoldier.toString());
            }


        }
    }
}
