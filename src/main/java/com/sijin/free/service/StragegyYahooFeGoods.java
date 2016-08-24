package com.sijin.free.service;

import com.sijin.free.constant.UseFileType;
import com.sijin.free.strategy.Strategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sijinzhang on 16/6/8.
 */
public class StragegyYahooFeGoods {
    private static final Logger LOGGER = LoggerFactory.getLogger(StragegyYahooFeGoods.class);
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        try {
            all();
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("i have finished it,spends ----->" + (System.currentTimeMillis() -start) /1000 + "s");
    }

    private static void all() throws Exception {
        Strategy strategy = new Strategy();
        strategy.setCompartorType(2);
        strategy.setUseFileType(UseFileType.CONFIGFILE);
        strategy.setIsGenerhtml(true);
        strategy.setfName("ma.html");
        strategy.setIsSendMail(false);
        strategy.setLogfile("logs/StragegyYahooFeGoods.log");
        StrategyService.handle(strategy);
    }


}
