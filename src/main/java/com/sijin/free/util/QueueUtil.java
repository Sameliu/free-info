package com.sijin.free.util;

import com.sijin.free.po.DockInfo;
import com.sijin.free.po.DockMA;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by sijinzhang on 16/12/23.
 */
public class QueueUtil {

    public static ArrayBlockingQueue<DockInfo> queue = new ArrayBlockingQueue<DockInfo>(1000);


    public static void add(DockInfo dockInfo){
        queue.add(dockInfo);
    }


}
