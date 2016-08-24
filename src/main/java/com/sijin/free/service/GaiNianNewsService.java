package com.sijin.free.service;

import com.sijin.free.constant.UseFileType;
import com.sijin.free.strategy.Strategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sijinzhang on 16/8/18.
 *
 * 追踪概念的相关信息
 *
 *
 */
public class GaiNianNewsService{
    private static final Logger LOGGER = LoggerFactory.getLogger(GaiNianNewsService.class);
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
        strategy.setUseFileType(UseFileType.GAINIAN);
        strategy.setIsGenerhtml(true);
        strategy.setfName("gainian.html");
        strategy.setIsSendMail(true);
        strategy.setLogfile("logs/GaiNianNewsService.log");
        StrategyService.handle(strategy);
    }

}
