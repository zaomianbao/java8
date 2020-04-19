package com.zaomianbao.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * @Description NEW->RUNNABLE->WAITING->RUNNABLE->TERMINATED
 * @Author zaomianbao
 * @Date 2019/5/31
 **/
@Slf4j
public class ThreadStateNRWRTLockSupport {

    public static void main(String[] args) {

        //展示线程
        Thread showThread = new Thread(ThreadStateNRWRTLockSupport::run);
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
        LockSupport.unpark(showThread);

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
        LockSupport.park();
        log.info(Thread.currentThread().getName()+ ":" + Thread.currentThread().getState());

    }

}
