package org.asocframework.bundle;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author jiqing
 * @version $Id:InitUtils , v1.0 2020/7/2 上午11:50 jiqing Exp $
 * @desc
 */
public class InitUtils {


    public static final Map<String, Long> cardServices = new ConcurrentHashMap();

    public static volatile Map<String, ReadWriteLock> initLock = new ConcurrentHashMap();

    public static Long getConsumerBean(Long appId) {

        //TODO:如果有一个接口挂了，岂不是每个都要挂？？？初始化，新发布
        // 服务未初始化时进行初始化，并行下读写等待
        String key = appId + "";
        Long consumerBean = cardServices.get(key);
        if (consumerBean == null) {
            LoadingLock loadingLock = getLock(appId);
            if (!loadingLock.isLoading) {
                //loadingLock.writeLock();
                try {
                    //初始化，写入
                    System.out.println("write");
                    Thread.sleep(1000);
                    cardServices.put(key, consumerBean);
                } catch (Exception e) {
                    //设置10分钟后可进行重试
                    return null;
                } finally {
                    loadingLock.writeUnlock();
                }
            } else {
                loadingLock.readLock();
                //初始化，读等待
                System.out.println("read");
                consumerBean = cardServices.get(key);
                loadingLock.readUnlock();
            }
        }
        return consumerBean;
    }

    protected static LoadingLock getLock(Long appId) {
        String key = appId + "";
        ReadWriteLock createLock = initLock.get(key);
        LoadingLock loadingLock;
        if (createLock == null) {
            synchronized (initLock) {
                createLock = initLock.get(key);
                if (createLock == null) {
                    createLock = new ReentrantReadWriteLock();
                    loadingLock = new LoadingLock(createLock, false);
                    loadingLock.writeLock();
                    initLock.put(key, createLock);
                    return loadingLock;
                } else {
                    return new LoadingLock(createLock, true);
                }
            }
        } else {
            return new LoadingLock(createLock, true);
        }
    }

    static class LoadingLock {

        ReadWriteLock createLock;

        boolean isLoading;

        public LoadingLock(ReadWriteLock createLock, boolean isLoading) {
            this.createLock = createLock;
            this.isLoading = isLoading;
        }

        public void writeLock() {
            createLock.writeLock().lock();
        }

        public void writeUnlock() {
            createLock.writeLock().unlock();
        }

        public void readLock() {
            createLock.readLock().lock();
        }

        public void readUnlock() {
            createLock.readLock().unlock();
        }

        public ReadWriteLock getCreateLock() {
            return createLock;
        }

        public void setCreateLock(ReadWriteLock createLock) {
            this.createLock = createLock;
        }

        public boolean isLoading() {
            return isLoading;
        }

        public void setLoading(boolean loading) {
            isLoading = loading;
        }
    }

    static class TaskThread extends Thread {

        CyclicBarrier barrier;

        public TaskThread(CyclicBarrier barrier) {
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try {
                //System.out.println(getName() + " 到达栅栏 B");
                barrier.await();
                getConsumerBean(11111L);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("sssss");

        int threadNum = 100;
        CyclicBarrier barrier = new CyclicBarrier(threadNum, new Runnable() {

            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " 完成最后任务");
            }
        });

        for (int i = 0; i < threadNum; i++) {
            new TaskThread(barrier).start();
        }

    }


}
