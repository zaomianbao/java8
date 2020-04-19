package com.zaomianbao.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description NEW->RUNNABLE->WAITING->RUNNABLE->TERMINATED
 * @Author zaomianbao
 * @Date 2019/5/31
 **/
@Slf4j
public class ThreadStateNRWRT {

    //锁
    private static final Object lock=new Object();

    public static void main(String[] args) {
        //展示线程
        Thread showThread = new Thread(ThreadStateNRWRT::run);
        log.info(showThread.getName()+ ":" + showThread.getState());
        showThread.start();
        log.info(showThread.getName()+ ":" + showThread.getState());
        //循环读取展示线程状态，直到读到展示线程状态为WAITING，才让辅助线程唤醒等待线程。
        while (true){
            if(showThread.getState()==Thread.State.WAITING){
                log.info(showThread.getName()+ ":" + showThread.getState());
                break;
            }
        }
        synchronized (lock){
            lock.notifyAll();
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
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.error(e.getMessage(),e);
            Thread.currentThread().interrupt();
        }
        synchronized (lock){
            try {
                lock.wait();
            } catch (InterruptedException e) {
                log.error(e.getMessage(),e);
                Thread.currentThread().interrupt();
            }
        }
        log.info(Thread.currentThread().getName()+ ":" + Thread.currentThread().getState());
    }

}

