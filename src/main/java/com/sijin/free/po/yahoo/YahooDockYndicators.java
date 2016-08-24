package com.sijin.free.po.yahoo;

import java.util.List;

/**
 * Created by sijinzhang on 16/8/24.
 */

public class YahooDockYndicators {
    private List<YahooDockYndicatorsQuote> quote;
    private List<YahooDockYndicatorsUnadjclose> unadjclose;

    public List<YahooDockYndicatorsQuote> getQuote() {
        return quote;
    }

    public void setQuote(List<YahooDockYndicatorsQuote> quote) {
        this.quote = quote;
    }

    public List<YahooDockYndicatorsUnadjclose> getUnadjclose() {
        return unadjclose;
    }

    public void setUnadjclose(List<YahooDockYndicatorsUnadjclose> unadjclose) {
        this.unadjclose = unadjclose;
    }
}
