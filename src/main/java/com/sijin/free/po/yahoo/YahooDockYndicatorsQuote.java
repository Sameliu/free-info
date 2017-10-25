package com.sijin.free.po.yahoo;

import java.util.DoubleSummaryStatistics;
import java.util.List;

/**
 * Created by sijinzhang on 16/8/24.
 */
public class YahooDockYndicatorsQuote {
    private List<Double> close;
    private List<Double> open;
    private List<Double> low;
    private List<Double> volume;
    private List<Double> high;

    public List<Double> getClose() {
        return close;
    }

    public void setClose(List<Double> close) {
        this.close = close;
    }

    public List<Double> getOpen() {
        return open;
    }

    public void setOpen(List<Double> open) {
        this.open = open;
    }

    public List<Double> getLow() {
        return low;
    }

    public void setLow(List<Double> low) {
        this.low = low;
    }

    public List<Double> getVolume() {
        return volume;
    }

    public void setVolume(List<Double> volume) {
        this.volume = volume;
    }

    public List<Double> getHigh() {
        return high;
    }

    public void setHigh(List<Double> high) {
        this.high = high;
    }
}