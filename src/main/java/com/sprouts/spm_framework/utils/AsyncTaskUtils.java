package com.sprouts.spm_framework.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 异步线程池的工具类
 * 
 * @author Howson
 * 
 */
public class AsyncTaskUtils {
    static AsyncTaskUtils instance;
    ScheduledExecutorService scheduleService = null;
    ExecutorService normalService = null;
    public BlockingQueue<Runnable> taskList = null;
    private int maxTaskLength = 10000;
    private String workName = "spm-framework-pool";
    private Logger logger = new Logger();

    public AsyncTaskUtils() {
        taskList = new ArrayBlockingQueue<Runnable>(maxTaskLength);
        normalService =
                new ThreadPoolExecutor(10, 10, 3, TimeUnit.SECONDS, taskList,
                        new WorkThreadFactory(workName), new ThreadPoolExecutor.AbortPolicy());
        scheduleService = Executors.newScheduledThreadPool(10);
    }

    public static synchronized AsyncTaskUtils getInstance() {
        if (instance == null) {
            instance = new AsyncTaskUtils();
        }
        return instance;
    }

    /**
     * 璁剧疆鍗曚緥
     * 
     * @param asyncTaskHandler
     */
    public static synchronized void setInstance(AsyncTaskUtils asyncTaskHandler) {
        instance = asyncTaskHandler;
    }

    /**
     * 璋冪敤璇ユ柟娉曠殑绾跨▼浼氬湪鎵ц瀹屾垚涔嬪悗鎵嶄細閫€鍑鸿鏂规硶
     * 
     * @param task
     */
    public synchronized void waitForCompeletion(Runnable task) {
        Future future = scheduleService.submit(task);
        try {
            future.get();
        } catch (InterruptedException e) {
            logger.error("waitForCompeletion:", e);
        } catch (ExecutionException e) {
            logger.error("waitForCompeletion:", e);
        }

    }

    /**
     * 运行一个普通任务
     * 
     * @param task
     * @return
     */
    public synchronized boolean dispatchTask(Runnable task) {
        if (!isServiceAvaliable()) {
            logger.warn("asyncTaskHandler is not available now.");
            return false;
        }

        if (task == null) {
            logger.warn("task is null.");
            return false;
        }
        try {
            normalService.execute(task);
        } catch (Throwable thr) {
            logger.error("", thr);
        }
        return true;

    }

    /**
     * 运行一个延迟任务
     * 
     * @param task
     * @param delayTime
     * @return
     */
    public synchronized boolean dispatchDelayTask(Runnable task, long delayTime) {
        if (!isServiceAvaliable()) {
            logger.warn("asyncTaskHandler is not available now.");
            return false;
        }

        if (task == null) {
            logger.warn("task is null.");
            return false;
        }

        delayTime = delayTime > 0 ? delayTime : 0;

        scheduleService.schedule(task, delayTime, TimeUnit.MILLISECONDS);
        return true;
    }

    /**
     * 运行一个调度任务
     * 
     * @param task
     * @param delayTime，默认毫秒
     * @param frequency，默认毫秒
     * @return
     */
    public synchronized ScheduledFuture dispatchScheduleTask(Runnable task, long delayTime,
            long frequency) {
        if (!isServiceAvaliable()) {
            logger.warn("asyncTaskHandler is not available now.");
            return null;
        }

        if (task == null) {
            logger.warn("task is null.");
            return null;
        }

        delayTime = delayTime > 0 ? delayTime : 0;
        frequency = frequency > 0 ? frequency : 0;

        ScheduledFuture future =
                scheduleService.scheduleAtFixedRate(task, delayTime, frequency, TimeUnit.MINUTES);
        return future;
    }

    /**
     * 关闭线程池
     */
    public synchronized void close() {
        if (scheduleService != null && !scheduleService.isShutdown()) {
            scheduleService.shutdownNow();
        }
        if (normalService != null && !normalService.isShutdown()) {
            normalService.shutdownNow();
        }
    }

    protected synchronized boolean isServiceAvaliable() {
        return normalService != null && !normalService.isShutdown();
    }

    class WorkThreadFactory implements ThreadFactory {
        final ThreadGroup group;
        final AtomicInteger threadNumber = new AtomicInteger(1);
        final String namePrefix;

        WorkThreadFactory(String namePrefix) {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            this.namePrefix = namePrefix;
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + "-" + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon()) t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY) t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }
}
