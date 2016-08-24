package com.sijin.free.po;

import java.util.List;

/**
 * Created by sijinzhang on 16/5/6.
 */
public class RedSoldier {
    private String name;

    private String code;

    private Double price;

    private List<RedSoldierItem> redSoldierItemList;

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<RedSoldierItem> getRedSoldierItemList() {
        return redSoldierItemList;
    }

    public void setRedSoldierItemList(List<RedSoldierItem> redSoldierItemList) {
        this.redSoldierItemList = redSoldierItemList;
    }

    @Override
    public String toString() {
        return "RedSoldier{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", price=" + price +
                ", redSoldierItemList=" + redSoldierItemList +
                '}';
    }
}
