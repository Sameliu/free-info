package com.sijin.free.service;

import com.sijin.free.constant.UseFileType;
import com.sijin.free.strategy.Strategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sijinzhang on 16/6/8.
 */
public class StragegyYahooGoods {
    private static final Logger LOGGER = LoggerFactory.getLogger(StragegyYahooGoods.class);
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
        strategy.setPriceStart(10D);
        strategy.setPriceEnd(45D);
        strategy.setUseFileType(UseFileType.CONFIGFILE);
        strategy.setIsGenerhtml(true);
        strategy.setHtmlFileName("low.html");
        strategy.setIsSendMail(false);
        StrategyService.handle(strategy);
    }


}
