package com.zaomianbao.phaser;

import lombok.extern.slf4j.Slf4j;

/**
 * 题目：5个学生参加考试，一共有三道题，要求所有学生到齐才能开始考试
 * ，全部做完第一题，才能继续做第二题，后面类似。
 *
 * Phaser有phase和party两个重要状态，
 * phase表示阶段，party表示每个阶段的线程个数，
 * 只有每个线程都执行了phaser.arriveAndAwaitAdvance();
 * 才会进入下一个阶段，否则阻塞等待。
 * 例如题目中5个学生(线程)都条用phaser.arriveAndAwaitAdvance();就进入下一题
 * @author liujun
 */
@Slf4j
public class StudentTaskExecuter {

    public static void main(String[] args) {
        MyPhaser phaser = new MyPhaser();
        StudentTask[] studentTask = new StudentTask[5];
        for (int i = 0; i < studentTask.length; i++) {
            studentTask[i] = new StudentTask(phaser);
            phaser.register();	//注册一次表示phaser维护的线程个数
        }

        Thread[] threads = new Thread[studentTask.length];
        for (int i = 0; i < studentTask.length; i++) {
            threads[i] = new Thread(studentTask[i], "Student "+i);
            threads[i].start();
        }

        //等待所有线程执行结束
        for (int i = 0; i < studentTask.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                log.error(e.getMessage(),e);
                Thread.currentThread().interrupt();
            }
        }
        log.info("Phaser has finished:{}",phaser.isTerminated());

    }

}
