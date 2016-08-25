package com.sijin.free.service;

import com.sijin.free.po.DockInfo;
import com.sijin.free.po.RedSoldier;
import com.sijin.free.po.RedSoldierItem;
import com.sijin.free.util.FileUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sijinzhang on 16/7/26.
 */
public class SoldierService {

    public static void main(String[] args) {
        List<DockInfo> dockInfoList = FileUtil.loadDockFromConf(null);
        try {
            List<RedSoldier> list = TaskService.executeSoldierTask(dockInfoList);
            cell(list);
            System.exit(0);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private static void cell(List<RedSoldier> list) {
        System.out.println("开始计算");
        List<RedSoldier> newlist = new ArrayList<>();
        for(RedSoldier redSoldier : list){
            List<RedSoldierItem> itemList = redSoldier.getRedSoldierItemList();
            if(itemList.size()>=4){
                if(itemList.get(0).getPrice() > itemList.get(1).getPrice() &&
                        itemList.get(1).getPrice()  > itemList.get(2).getPrice()  &&
                        itemList.get(2).getPrice() > itemList.get(3).getPrice()) {
                    System.out.println("出现－－－" + redSoldier.getCode() + "==" + redSoldier.getName());
                    newlist.add(redSoldier);
                }else {
                    System.out.println("未出现－－－" + redSoldier.getCode() +"==" + redSoldier.getName());
                }
            }
        }
        System.out.println("count:" + newlist.size());
        for(RedSoldier r : newlist){
            System.out.println(r.toString());
        }
    }
}
