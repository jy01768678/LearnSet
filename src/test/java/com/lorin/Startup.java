package com.lorin;

import org.apache.commons.lang.time.StopWatch;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by baidu on 16/7/4.
 */
public class Startup {

    private static ShutdownHook shutdownHook = null;

    public static void main(String[] args) {
        StopWatch sw = new StopWatch();
        sw.start();
        try {
            LoadServer.start();
        } finally {
            registerShutdownHook();
            sw.stop();
        }
    }

    /**
     * 增加kill信号量捕获，平滑stop
     */
    private static void registerShutdownHook() {
        if (null == shutdownHook) {
            shutdownHook = new ShutdownHook();
        }
        Runtime.getRuntime().addShutdownHook(shutdownHook);

    }

    static class ShutdownHook extends Thread {
        public void run() {
            LoadServer.close();
        }
    }

    static class LoadServer {
        private static ClassPathXmlApplicationContext context = null;

        static void close() {
            if (null != shutdownHook) {
                Runtime.getRuntime().removeShutdownHook(shutdownHook);
            }
            if (null != context) {
                context.close();
            }
        }

        static void start() {
            System.out.println("start......");
        }
    }
}
