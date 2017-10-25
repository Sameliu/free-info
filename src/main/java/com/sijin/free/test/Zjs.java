package com.sijin.free.test;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by sijinzhang on 16/11/7.
 */
public class Zjs {

    public static void main(String[] args) {
        DecimalFormat a = new DecimalFormat("#0.00%");


        System.out.println(0.01/6.68);
        System.out.println(a.format(0.01 / 6.68));

        double f = 3.1566;
        BigDecimal b = new BigDecimal(f);
        double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        System.out.println(f1);
    }
}
