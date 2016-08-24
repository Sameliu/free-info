package com.sijin.free.po.yahoo;

import java.util.List;

/**
 * Created by sijinzhang on 16/8/24.
 */
public class YahooDock {

    private List<Long> timestamp;

    private YahooDockYndicators indicators;

    public List<Long> getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(List<Long> timestamp) {
        this.timestamp = timestamp;
    }

    public YahooDockYndicators getIndicators() {
        return indicators;
    }

    public void setIndicators(YahooDockYndicators indicators) {
        this.indicators = indicators;
    }

}
