package com.sijin.demo.callback;

/**
 * Created by sijinzhang on 16/8/3.
 */
public class MeetingSystem {
    public void search(Integer num, String time, IApply iApply){
        System.out.println("人数：" + num);
        System.out.println("时间："+ time);
        String result = "2701会议室";
        iApply.doApply(num, time,result);
    }
}
