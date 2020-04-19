package com.zaomianbao.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description NEW->RUNNABLE->TIMED_WAITING->RUNNABLE->TERMINATED
 * @Author zaomianbao
 * @Date 2019/5/31
 **/
@Slf4j
public class ThreadStateNRTWRT {

    //锁
    private static final Object lock=new Object();

    public static void main(String[] args) {

        //展示线程
        Thread showThread = new Thread(ThreadStateNRTWRT::run);
        log.info(showThread.getName()+ ":" + showThread.getState());
        showThread.start();
        log.info(showThread.getName()+ ":" + showThread.getState());
        while (true){
            if(showThread.getState()==Thread.State.TIMED_WAITING){
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
        synchronized (lock){
            try {
                Thread.sleep(100);
                lock.wait(1);
            } catch (InterruptedException e) {
                log.error(e.getMessage(),e);
                Thread.currentThread().interrupt();
            }
        }
        log.info(Thread.currentThread().getName()+ ":" + Thread.currentThread().getState());

    }

}
