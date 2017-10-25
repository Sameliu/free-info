package com.sijin.free.service;

import com.sijin.free.constant.UseFileType;
import com.sijin.free.strategy.Strategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sijinzhang on 16/6/8.
 *
 * 扫描当前价最接近历史最低价的
 * 输入：白名单
 * 输出：按照接近程度排序
 * 接近1个点的生成html 报表：StragegyYahooFeGoods.html
 *
 *
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
        strategy.setUseFileType(UseFileType.CONFIGFILE);
        strategy.setIsGenerhtml(true);
        strategy.setHtmlFileName("常用Conf名单的均线.html");
        strategy.setIsSendMail(false);
        StrategyService.handle(strategy);
    }


}
