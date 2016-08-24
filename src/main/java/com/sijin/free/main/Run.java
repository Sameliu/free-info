package com.sijin.free.main;

import com.sijin.free.service.GoodNewsService;

/**
 * Created by sijinzhang on 16/6/13.
 */
public class Run {
    public static void main(String[] args) {
        try {
            GoodNewsService.main(null);
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
