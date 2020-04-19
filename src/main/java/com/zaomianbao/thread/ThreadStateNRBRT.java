package com.zaomianbao.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description NEW->RUNNABLE->BLOCKED->RUNNABLE->TERMINATED
 * @Author zaomianbao
 * @Date 2019/5/31
 **/
@Slf4j
public class ThreadStateNRBRT {

    //锁
    private static final Object lock=new Object();
    //锁定标志
    private static volatile boolean lockFlag=true;
    //执行顺序
    private static volatile int order=0;


    public static void main(String[] args) {
        //辅助线程，制造synchronized状态。
        Thread assistantThread = new Thread(ThreadStateNRBRT::run1);
        assistantThread.start();
        try {
            //保证assistantThread先执行。
            Thread.sleep(10);
        } catch (InterruptedException e) {
            log.error(e.getMessage(),e);
            Thread.currentThread().interrupt();
        }
        Thread showThread = new Thread(ThreadStateNRBRT::run2);
        log.info(showThread.getName()+ ":" + showThread.getState());
        showThread.start();
        log.info(showThread.getName()+ ":" + showThread.getState());
        //因为无法判断显示线程何时执行，所以循环直到显示线程执行。
        while (true){
            if(showThread.getState()==Thread.State.BLOCKED){
                log.info(showThread.getName()+ ":" + Thread.State.BLOCKED);
                lockFlag=false;
                break;
            }
        }
        //等待两个线程执行完毕。
        try {
            assistantThread.join();
            showThread.join();
        } catch (InterruptedException e) {
            log.error(e.getMessage(),e);
            Thread.currentThread().interrupt();
        }
        //线程执行完毕打印状态。
        log.info(showThread.getName()+ ":" + showThread.getState());
    }

    private static void run1() {
        int i = 0 ;
        while (i < 1000) {
            //保证先进入同步范围。
            if (order == 0) {
                //锁定一定时间
                synchronized (lock){
                    //启动另一个同步
                    order=1;
                    while (lockFlag) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            log.error(e.getMessage(),e);
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            }
            i++;
        }

    }

    private static void run2() {
        int i = 0;
        while (i < 1000) {
            //保证后进入同步范围。
            if (order == 1) {
                synchronized (lock){
                    log.info(Thread.currentThread().getName()+ ":" + Thread.currentThread().getState());
                }
                break;
            }
            i++;
        }
    }

}
