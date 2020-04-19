package com.zaomianbao.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description NEW->RUNNABLE->WAITING->RUNNABLE->TERMINATED
 * @Author zaomianbao
 * @Date 2019/5/31
 **/
@Slf4j
public class ThreadStateNRWRTLock {

    //锁
    private  static Lock lock=new ReentrantLock();
    //锁定标志
    private static volatile boolean lockFlag=true;
    //执行顺序
    private static volatile int order=0;

    public static void main(String[] args) {

        //展示线程
        Thread showThread = new Thread(ThreadStateNRWRTLock::run1);
        log.info(showThread.getName()+ ":" + showThread.getState());
        showThread.start();
        log.info(showThread.getName()+ ":" + showThread.getState());
        //辅助线程，制造synchronized状态。
        Thread assistantThread = new Thread(ThreadStateNRWRTLock::run2);
        assistantThread.start();
        //循环读取展示线程状态，直到读到展示线程状态为WAITING，才让辅助线程退出同步。
        while (true){
            if(showThread.getState()==Thread.State.WAITING){
                log.info(showThread.getName()+ ":" + showThread.getState());
                lockFlag=false;
                break;
            }
        }
        try {
            showThread.join();
            assistantThread.join();
        } catch (InterruptedException e) {
            log.trace(e.getMessage(),e);
            Thread.currentThread().interrupt();
        }
        //线程执行完毕打印状态。
        log.info(showThread.getName()+ ":" + showThread.getState());
    }

    private static void run2() {
        while (true) {
            //保证先进入同步范围。
            if (order == 0) {
                //加锁
                lock.lock();
                try {
                    order=1;
                    while (lockFlag) {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            log.error(e.getMessage(),e);
                            Thread.currentThread().interrupt();
                        }
                    }
                }finally {
                    lock.unlock();
                }
                break;
            }
        }
    }

    private static void run1() {
        while (true){
            //保证后进入同步范围。
            if (order==1){
                lock.lock();
                try{
                    log.info(Thread.currentThread().getName()+ ":" + Thread.currentThread().getState());
                }finally {
                    lock.unlock();
                }
                break;
            }
        }
    }
}
