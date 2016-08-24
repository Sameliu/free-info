package com.sijin.free.po;

/**
 * Created by sijinzhang on 16/6/19.
 */
public class WhiteInputPo {

    private String stockname;
    private String stockcode;
    private String zxj;

    public String getStockname() {
        return stockname;
    }

    public void setStockname(String stockname) {
        this.stockname = stockname;
    }

    public String getStockcode() {
        return stockcode;
    }

    public void setStockcode(String stockcode) {
        this.stockcode = stockcode;
    }

    public String getZxj() {
        return zxj;
    }

    public void setZxj(String zxj) {
        this.zxj = zxj;
    }

    @Override
    public String toString() {
        return "WhiteInputPo{" +
                "stockname='" + stockname + '\'' +
                ", stockcode='" + stockcode + '\'' +
                ", zxj='" + zxj + '\'' +
                '}';
    }


    /***
     *
     *
     * {
     "data": [
     {
     "stockname": "贵航股份",
     "stockcode": "600523",
     "zxj": "16.81",
     "zde": "0.08",
     "zdf": "0.48",
     "zs": "16.73",
     "jk": "16.70",
     "zgj": "17.05",
     "zdj": "16.58",
     "cjl": "42625",
     "cje": "7177.69",
     "jlr": "-244.68",
     "hsl": "1.48",
     "rtime": "2016-06-19 15:30:09"
     },
     {
     "stockname": "横店东磁",
     "stockcode": "002056",
     "zxj": "16.40",
     "zde": "-0.28",
     "zdf": "-1.68",
     "zs": "16.68",
     "jk": "17.01",
     "zgj": "17.15",
     "zdj": "16.18",
     "cjl": "759621",
     "cje": "126362.42",
     "jlr": "1582.10",
     "hsl": "9.24",
     "rtime": "2016-06-19 15:30:09"
     }
     ],
     "news": "",
     "name": "",
     "explain": "",
     "explainAll": ""
     }
     *
     *
     */


}
