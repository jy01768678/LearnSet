package com.lorin.patterns.asyncinvocation;

import java.util.concurrent.Callable;

/**
 * Created by lorin on 2018/4/21.
 */
public class App {
    public static void main(String[] args) throws Exception {
        AsyncExecutor asyncExecutor = new ThreadAsyncExecutor();

        AsyncResult<Integer> asyncResult1 = asyncExecutor.startProcess(() -> {
            Thread.sleep(500);
            System.out.println("Task completed with: " + 10);
            return 10;
        });
        AsyncResult<String> asyncExecutor2 = asyncExecutor.startProcess(lazyval("test", 300));
        AsyncResult<Long> asyncResult3 = asyncExecutor.startProcess(lazyval(50L, 700));

        AsyncResult<Integer> asyncResult4 = asyncExecutor.startProcess(lazyval(20, 400), callback("Callback result 4"));
        AsyncResult<String> asyncResult5 = asyncExecutor.startProcess(lazyval("callback", 600), callback("Callback result 5"));

        // emulate processing in the current thread while async tasks are running in their own threads
        Thread.sleep(350); // Oh boy I'm working hard here
        System.out.println("Some hard work done");

        // wait for completion of the tasks
        Integer result1 = asyncExecutor.endProcess(asyncResult1);
        String result2 = asyncExecutor.endProcess(asyncExecutor2);
        Long result3 = asyncExecutor.endProcess(asyncResult3);
        asyncResult4.await();
        asyncResult5.await();

        // log the results of the tasks, callbacks log immediately when complete
        System.out.println("Result 1: " + result1);
        System.out.println("Result 2: " + result2);
        System.out.println("Result 3: " + result3);
    }

    private static <T> Callable<T> lazyval(T value, long delayMillis) {
        return () -> {
            Thread.sleep(delayMillis);
            System.out.println("Task completed with: " + value);
            return value;
        };
    }

    private static <T> AsyncCallback<T> callback(String name) {
        return (value, ex) -> {
            if (ex.isPresent()) {
                System.out.println(name + " failed: " + ex.map(Exception::getMessage).orElse(""));
            } else {
                System.out.println(name + ": " + value);
            }
        };
    }
}
