package com.chan.servercache.threadpool;

import java.util.concurrent.*;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/6/7 16:05
 * FileName: ThreadPoolUtils
 * Description: 线程工具类
 */
public class ThreadPoolUtils {
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(16, 16, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<>(4096), new ThreadPoolExecutor.CallerRunsPolicy());

    /**
     * execute task in thread pool
     */
    public static void execute(Runnable command) {
        executor.execute(command);
    }

    public static <T> Future<T> shumit(Callable<T> task) {
        return executor.submit(task);
    }

    public static void shutdown() {
        if (executor != null) {
            executor.shutdown();
        }
    }

}
