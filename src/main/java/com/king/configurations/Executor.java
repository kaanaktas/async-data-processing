package com.king.configurations;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Created by kaktas on 15/10/2020
 */
public class Executor {

    public static ExecutorService executor = Executors.newFixedThreadPool(3, new ThreadFactory() {
        int count = 1;

        @Override
        public java.lang.Thread newThread(Runnable runnable) {
            return new java.lang.Thread(runnable, "custom-executor-" + count++);
        }
    });
}
