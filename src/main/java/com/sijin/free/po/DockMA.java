package com.sijin.free.po;

import java.math.BigDecimal;

/**
 * Created by sijinzhang on 16/6/9.
 */
public class DockMA {
    private BigDecimal ma5;

    private BigDecimal ma10;

    private BigDecimal ma20;

    private BigDecimal ma30;

    private String code;

    private String name;

    private Double price;

    private Double lowPirce;

    private Double highPirce;


    private BigDecimal range5;  //五日均价与当期间偏离程度
    private BigDecimal range10; //十日均价与当期间偏离程度
    private BigDecimal range20;
    private BigDecimal range30;

    public BigDecimal getMa5() {
        return ma5;
    }

    public void setMa5(BigDecimal ma5) {
        this.ma5 = ma5;
    }

    public BigDecimal getMa10() {
        return ma10;
    }

    public void setMa10(BigDecimal ma10) {
        this.ma10 = ma10;
    }

    public BigDecimal getMa20() {
        return ma20;
    }

    public void setMa20(BigDecimal ma20) {
        this.ma20 = ma20;
    }

    public BigDecimal getMa30() {
        return ma30;
    }

    public void setMa30(BigDecimal ma30) {
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

    public BigDecimal getRange5() {
        return range5;
    }

    public void setRange5(BigDecimal range5) {
        this.range5 = range5;
    }

    public BigDecimal getRange10() {
        return range10;
    }

    public void setRange10(BigDecimal range10) {
        this.range10 = range10;
    }

    public BigDecimal getRange20() {
        return range20;
    }

    public void setRange20(BigDecimal range20) {
        this.range20 = range20;
    }

    public BigDecimal getRange30() {
        return range30;
    }

    public void setRange30(BigDecimal range30) {
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

    @Override
    public String toString() {
        return "DockMA{" +
                code+"[" + name +"]" +
                ", ma5=" + ma5 +
                ", range5=" + range5 +
                ", ma10=" + ma10 +
                ", range10=" + range10 +
                ", ma20=" + ma20 +
                ", range20=" + range20 +
                ", ma30=" + ma30 +
                ", range30=" + range30 +
                ", price=" + price +
                ", lowprice=" + lowPirce +
                ", highPirce=" + highPirce +
                '}';
    }
}
