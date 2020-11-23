package com.async.configurations;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class Executor {

    private Executor(){
    }

    public static ExecutorService customExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), new ThreadFactory() {
        int count = 1;

        @Override
        public java.lang.Thread newThread(Runnable runnable) {
            return new java.lang.Thread(runnable, "custom-executor-" + count++);
        }
    });
}
