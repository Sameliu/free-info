package com.sijin.free.po;

/**
 * Created by sijinzhang on 16/7/26.
 */
public class RedSoldierItem {

    private Double price;

    private Double hPrice;

    private Double lPrice;

    private String date;


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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "RedSoldierItem{" +
                "price=" + price +
                ", hPrice=" + hPrice +
                ", lPrice=" + lPrice +
                ", date='" + date + '\'' +
                '}';
    }
}
