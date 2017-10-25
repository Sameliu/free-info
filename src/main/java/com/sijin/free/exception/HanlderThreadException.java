package com.sijin.free.exception;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by sijinzhang on 16/12/29.
 */
public class HanlderThreadException implements RejectedExecutionHandler {
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.println("任务数量:" + executor.getTaskCount());
    }
}
