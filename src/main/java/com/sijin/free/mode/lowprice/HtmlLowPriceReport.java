package com.sijin.free.mode.lowprice;

import com.sijin.free.constant.UseFileType;
import com.sijin.free.service.StrategyService;
import com.sijin.free.strategy.Strategy;

/**
 *
 * 价格范围在一定范围的，同时价格低于90天均价的
 * Created by sijinzhang on 16/11/18.
 */
public class HtmlLowPriceReport {

    public static void main(String[] args) {

        Strategy strategy = new Strategy();
        strategy.setHtmlFileName("mode/lowprice-configfile-low.html");
        strategy.setIsGenerhtml(true);
        strategy.setIsSendMail(false);
        strategy.setPriceStart(10D);
        strategy.setPriceEnd(60D);
        HtmlLowPriceFunction function = new HtmlLowPriceFunction();
        strategy.setReport(function);
        strategy.setUseFileType(UseFileType.DEFAULT);
        strategy.setWeight(0.01);
        try {
            StrategyService.handle(strategy);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}



