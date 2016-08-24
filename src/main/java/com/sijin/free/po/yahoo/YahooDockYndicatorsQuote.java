package com.sijin.free.po.yahoo;

import java.util.List;

/**
 * Created by sijinzhang on 16/8/24.
 */
public class YahooDockYndicatorsQuote {
    private List<String> close;
    private List<Float> open;
    private List<Float> low;
    private List<Float> volume;
    private List<Float> high;

    public List<String> getClose() {
        return close;
    }

    public void setClose(List<String> close) {
        this.close = close;
    }

    public List<Float> getOpen() {
        return open;
    }

    public void setOpen(List<Float> open) {
        this.open = open;
    }

    public List<Float> getLow() {
        return low;
    }

    public void setLow(List<Float> low) {
        this.low = low;
    }

    public List<Float> getVolume() {
        return volume;
    }

    public void setVolume(List<Float> volume) {
        this.volume = volume;
    }

    public List<Float> getHigh() {
        return high;
    }

    public void setHigh(List<Float> high) {
        this.high = high;
    }
}