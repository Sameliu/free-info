package com.sijin.free.po;


import java.text.NumberFormat;

/**
 * Created by sijinzhang on 16/6/9.
 */
public class DockMA {
    static NumberFormat df   =   NumberFormat.getPercentInstance();
    private Double ma5;

    private Double ma10;

    private Double ma20;

    private Double ma30;

    private String code;

    private String name;

    private Double price;

    private Double lowPirce;

    private Double deviation; //偏离度

    private Double highPirce;


    private Double range5;  //五日均价与当期间偏离程度
    private Double range10; //十日均价与当期间偏离程度
    private Double range20;
    private Double range30;

    public DockMA() {
        df.setMinimumFractionDigits(4);
    }

    public Double getMa5() {
        return ma5;
    }

    public void setMa5(Double ma5) {
        this.ma5 = ma5;
    }

    public Double getMa10() {
        return ma10;
    }

    public void setMa10(Double ma10) {
        this.ma10 = ma10;
    }

    public Double getMa20() {
        return ma20;
    }

    public void setMa20(Double ma20) {
        this.ma20 = ma20;
    }

    public Double getMa30() {
        return ma30;
    }

    public void setMa30(Double ma30) {
        this.ma30 = ma30;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getRange5() {
        return range5;
    }

    public void setRange5(Double range5) {
        this.range5 = range5;
    }

    public Double getRange10() {
        return range10;
    }

    public void setRange10(Double range10) {
        this.range10 = range10;
    }

    public Double getRange20() {
        return range20;
    }

    public void setRange20(Double range20) {
        this.range20 = range20;
    }

    public Double getRange30() {
        return range30;
    }

    public void setRange30(Double range30) {
        this.range30 = range30;
    }

    public Double getLowPirce() {
        return lowPirce;
    }

    public void setLowPirce(Double lowPirce) {
        this.lowPirce = lowPirce;
    }

    public Double getHighPirce() {
        return highPirce;
    }

    public void setHighPirce(Double highPirce) {
        this.highPirce = highPirce;
    }

    public Double getDeviation() {
        return deviation;
    }

    public void setDeviation(Double deviation) {
        this.deviation = deviation;
    }

    @Override
    public String toString() {
        return "DockMA{" +
                code+"[" + name +"]" +
                ", ma5=" + ma5 +
                ", range5=" + df.format(range5) +
                ", ma10=" + ma10 +
                ", range10=" + df.format(range10) +
                ", ma20=" + ma20 +
                ", range20=" + df.format(range20) +
                ", ma30=" + ma30 +
                ", range30=" + df.format(range30) +
                ", price=" + price +
                ", lowprice=" + lowPirce +
                ", highPirce=" + highPirce +
                ", deviation=" + df.format(deviation) +
                '}';
    }
}
