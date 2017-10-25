package com.sijin.free.mode.lowprice;

import com.sijin.free.po.DockMA;
import com.sijin.free.strategy.Report;
import com.sijin.free.strategy.Strategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sijinzhang on 16/12/1.
 */
public class HtmlLowPriceFunction implements Report {


    public List<DockMA> yunsuan(List<DockMA> list, Strategy strategy) {

        List<DockMA> newList = new ArrayList<>();

        for(DockMA dockma : list){
//            if(dockma.getMa5().doubleValue() >= dockma.getPrice() && dockma.getMa10().doubleValue() >=dockma.getPrice() &&
//                    dockma.getMa20().doubleValue() >= dockma.getPrice() && dockma.getMa30().doubleValue() >=dockma.getPrice()){
//                newList.add(dockma);
//            }
            if(strategy.getPriceStart() != null && strategy.getPriceStart() > dockma.getPrice()){
                continue;
            }
            if(strategy.getPriceEnd() != null && strategy.getPriceEnd() < dockma.getPrice()){
                continue;
            }
            if(strategy.getWeight().compareTo(0D) > 0){
                if(dockma.getPrice() <= dockma.getLowPirce().doubleValue()*(1+strategy.getWeight())){
                    newList.add(dockma);
                }
            }
        }
        System.out.println("function finish~~");
        return newList;
    }
}
