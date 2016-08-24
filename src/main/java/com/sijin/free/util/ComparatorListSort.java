package com.sijin.free.util;

import com.sijin.free.po.DockMA;

import java.util.Comparator;

/**
 * Created by sijinzhang on 16/7/14.
 */
public class ComparatorListSort implements Comparator<DockMA> {
    private int type;

    public ComparatorListSort(int type) {
        this.type = type;
    }


    public int compare(DockMA o1, DockMA o2) {
        switch (type){
            case 2:
                return sortPrice(o1, o2);
            case 5:
                return sortRange5(o1,o2);
            case 10:
                return sortRange10(o1,o2);
            case 20:
                return sortRange20(o1, o2);
            case 30:
                return sortRange30(o1,o2);
                default:
                return 0;
        }
    }

    private int sortPrice(DockMA o1, DockMA o2) {
        if (o1.getPrice().doubleValue() > o2.getPrice().doubleValue()) {
            return -1;
        }
        if (o1.getPrice().doubleValue() == o2.getPrice().doubleValue()) {
            return 0;
        }
        if (o1.getPrice().doubleValue() < o2.getPrice().doubleValue()) {
            return 1;
        }
        return 0;
    }

    private int sortRange5(DockMA o1, DockMA o2) {
        if (o1.getRange5().doubleValue() > o2.getRange5().doubleValue()) {
            return -1;
        }
        if (o1.getRange5().doubleValue() == o2.getRange5().doubleValue()) {
            return 0;
        }
        if (o1.getRange5().doubleValue() < o2.getRange5().doubleValue()) {
            return 1;
        }
        return 0;
    }

    private int sortRange10(DockMA o1, DockMA o2) {
        if (o1.getRange10().doubleValue() > o2.getRange10().doubleValue()) {
            return -1;
        }
        if (o1.getRange10().doubleValue() == o2.getRange10().doubleValue()) {
            return 0;
        }
        if (o1.getRange10().doubleValue() < o2.getRange10().doubleValue()) {
            return 1;
        }
        return 0;
    }

    private int sortRange20(DockMA o1, DockMA o2) {
        if (o1.getRange20().doubleValue() > o2.getRange20().doubleValue()) {
            return -1;
        }
        if (o1.getRange20().doubleValue() == o2.getRange20().doubleValue()) {
            return 0;
        }
        if (o1.getRange20().doubleValue() < o2.getRange20().doubleValue()) {
            return 1;
        }
        return 0;
    }

    private int sortRange30(DockMA o1, DockMA o2) {
        if (o1.getRange30().doubleValue() > o2.getRange30().doubleValue()) {
            return -1;
        }
        if (o1.getRange30().doubleValue() == o2.getRange30().doubleValue()) {
            return 0;
        }
        if (o1.getRange30().doubleValue() < o2.getRange30().doubleValue()) {
            return 1;
        }
        return 0;
    }
}
