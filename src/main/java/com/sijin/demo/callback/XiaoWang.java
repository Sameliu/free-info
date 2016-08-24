package com.sijin.demo.callback;

/**
 * Created by sijinzhang on 16/8/3.
 */
public class XiaoWang {

    public IApply iApply = new IApply() {
        @Override
        public void doApply(Integer num, String time, String result) {
            System.out.println("申请结果:" + result);
        }
    };

    public void dowork(Integer num, String time) {
        new MeetingSystem().search(num, time, iApply);
    }

    public static void main(String[] args) {
        XiaoWang xiaoWang = new XiaoWang();

        xiaoWang.dowork(3, "2016-08-01");
    }
}
