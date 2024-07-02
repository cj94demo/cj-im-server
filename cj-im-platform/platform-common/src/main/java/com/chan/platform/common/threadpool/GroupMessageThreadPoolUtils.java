package com.chan.platform.common.threadpool;

import java.util.concurrent.*;

/**
 * Copyright (C), 2024-2024
 * Author: chan
 * Date: 2024/7/2 20:55
 * FileName: GroupMessageThreadPoolUtils
 * Description: 线程池工具类
 */
public class GroupMessageThreadPoolUtils {
    private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(8,
            16,
            120,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(4096),
            new ThreadPoolExecutor.CallerRunsPolicy());

    public static void execute(Runnable task) {
        THREAD_POOL_EXECUTOR.execute(task);
    }

    public static <T> Future<T> submit(Callable<T> task) {
        return THREAD_POOL_EXECUTOR.submit(task);
    }

    public static void shutdown() {
        THREAD_POOL_EXECUTOR.shutdown();
    }
}
