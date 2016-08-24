package com.sijin.free.po;

import java.text.NumberFormat;

/**
 * Created by sijinzhang on 16/5/6.
 */
public class DockInfo {
    private String name;

    private String code;

    private Double sPrice;

    private Double yesterdayprice;

    private Double price;

    private Double hPrice;

    private Double lPrice;

    private String datep;

    private String timep;

    private Double mywantbuy;//期望买入

    private Double mywantsale;//期望卖出


    private String mailMessage; //通知信息

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getsPrice() {
        return sPrice;
    }

    public void setsPrice(Double sPrice) {
        this.sPrice = sPrice;
    }

    public Double getYesterdayprice() {
        return yesterdayprice;
    }

    public void setYesterdayprice(Double yesterdayprice) {
        this.yesterdayprice = yesterdayprice;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double gethPrice() {
        return hPrice;
    }

    public void sethPrice(Double hPrice) {
        this.hPrice = hPrice;
    }

    public Double getlPrice() {
        return lPrice;
    }

    public void setlPrice(Double lPrice) {
        this.lPrice = lPrice;
    }

    public String getDatep() {
        return datep;
    }

    public void setDatep(String datep) {
        this.datep = datep;
    }

    public String getTimep() {
        return timep;
    }

    public void setTimep(String timep) {
        this.timep = timep;
    }

    public Double getMywantbuy() {
        return mywantbuy;
    }

    public void setMywantbuy(Double mywantbuy) {
        this.mywantbuy = mywantbuy;
    }

    public Double getMywantsale() {
        return mywantsale;
    }

    public void setMywantsale(Double mywantsale) {
        this.mywantsale = mywantsale;
    }


    public String getMailMessage() {
        return mailMessage;
    }

    public void setMailMessage(String mailMessage) {
        this.mailMessage = mailMessage;
    }

    @Override
    public String toString() {
        return "DockInfo{" +
                code + "["+ name + "]" +
                ", price='" + price + '\'' +
                ", mywantbuy='" + mywantbuy + '\'' +
                ", lPrice='" + lPrice + '\'' +
                ", mywantsale='" + mywantsale + '\'' +
                ", hPrice='" + hPrice + '\'' +
                ", sPrice='" + sPrice + '\'' +
                ", yesterdayprice='" + yesterdayprice + '\'' +
                ", datep='" + datep + '\'' +
                ", timep='" + timep + '\'' +

                '}';
    }
}
