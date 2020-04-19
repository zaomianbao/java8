package com.zaomianbao.phaser;

import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * @Description 学生任务类
 * @Author zaomianbao
 * @Date 2019/5/30
 **/
@Slf4j
public class StudentTask implements Runnable {

    private Phaser phaser;

    public StudentTask(Phaser phaser) {
        this.phaser = phaser;
    }

    @Override
    public void run() {
        log.info(Thread.currentThread().getName()+"到达考试");
        phaser.arriveAndAwaitAdvance();

        log.info(Thread.currentThread().getName()+"做第1题时间...");
        doExercise();
        log.info(Thread.currentThread().getName()+"做第1题完成...");
        phaser.arriveAndAwaitAdvance();

        log.info(Thread.currentThread().getName()+"做第2题时间...");
        doExercise();
        log.info(Thread.currentThread().getName()+"做第2题完成...");
        phaser.arriveAndAwaitAdvance();

        log.info(Thread.currentThread().getName()+"做第3题时间...");
        doExercise();
        log.info(Thread.currentThread().getName()+"做第3题完成...");
        phaser.arriveAndAwaitAdvance();
    }

    private void doExercise() {
        long duration = (long)(Math.random()*10);
        try {
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            log.error(e.getMessage(),e);
            Thread.currentThread().interrupt();
        }
    }

}
