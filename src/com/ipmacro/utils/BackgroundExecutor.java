/**
 * Copyright (C) 2010-2011 fire
 * 
 * 开启线程池执行短期异步任务
 */
package com.ipmacro.utils;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BackgroundExecutor {

    private static Executor executor = Executors.newCachedThreadPool();

    public static void execute(Runnable runnable) {
            executor.execute(runnable);
    }
    
    public static void setExecutor(Executor executor) {
            BackgroundExecutor.executor = executor;
    }
    
}
