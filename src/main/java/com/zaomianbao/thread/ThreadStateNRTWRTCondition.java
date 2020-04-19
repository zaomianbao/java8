package com.zaomianbao.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description NEW->RUNNABLE->TIMED_WAITING->RUNNABLE->TERMINATED
 * @Author zaomianbao
 * @Date 2019/5/31
 **/
@Slf4j
public class ThreadStateNRTWRTCondition {
    //锁
    private static Lock lock=new ReentrantLock();
    private static Condition condition=lock.newCondition();

    public static void main(String[] args) {

        //展示线程
        Thread showThread = new Thread(ThreadStateNRTWRTCondition::run);
        log.info(showThread.getName()+ ":" + showThread.getState());
        showThread.start();
        log.info(showThread.getName()+ ":" + showThread.getState());

        //循环读取展示线程状态，直到读到展示线程状态为TIMED_WAITING。
        while (true){
            if(Thread.State.TIMED_WAITING==showThread.getState()){
                log.info(showThread.getName()+ ":" + showThread.getState());
                break;
            }
        }
        try {
            showThread.join();
        } catch (InterruptedException e) {
            log.error(e.getMessage(),e);
            Thread.currentThread().interrupt();
        }

        //线程执行完毕打印状态。
        log.info(showThread.getName()+ ":" + showThread.getState());
    }

    private static void run() {
        //等待
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            log.error(e.getMessage(),e);
            Thread.currentThread().interrupt();
        }
        lock.lock();
        try{
            try {
                if (condition.await(1, TimeUnit.MILLISECONDS)){
                    log.info("及时返回释放");
                }else {
                    log.info("超时未返回释放");
                }
            } catch (InterruptedException e) {
                log.error(e.getMessage(),e);
                Thread.currentThread().interrupt();
            }
        }finally {
            lock.unlock();
        }
        log.info(Thread.currentThread().getName()+ ":" + Thread.currentThread().getState());
    }

}
