package com.zaomianbao.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description NEW->RUNNABLE->TERMINATED
 * @Author zaomianbao
 * @Date 2019/5/31
 **/
@Slf4j
public class ThreadStateNRT {

    public static void main(String[] args) {

        Thread thread=new Thread(ThreadStateNRT::printThreadStatus);
        log.info(thread.getName()+ ":" + thread.getState());
        thread.start();
        //等待线程执行完毕。
        try {
            thread.join();
        } catch (InterruptedException e) {
            log.error(e.getMessage(),e);
            Thread.currentThread().interrupt();
        }
        log.info(thread.getName()+ ":" + thread.getState());

    }

    private static void printThreadStatus() {
        log.info(Thread.currentThread().getName()+ ":" + Thread.currentThread().getState());
    }

}
