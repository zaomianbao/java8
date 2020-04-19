package com.zaomianbao.phaser;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Phaser;

/***
 *  下面说说Phaser的高级用法，在Phaser内有2个重要状态，分别是phase和party。
 *  phase就是阶段，初值为0，当所有的线程执行完本轮任务，同时开始下一轮任务时，
 *  意味着当前阶段已结束，进入到下一阶段，phase的值自动加1。party就是线程，
 *  party=4就意味着Phaser对象当前管理着4个线程。Phaser还有一个重要的方法经常需要被重载，
 *  那就是boolean onAdvance(int phase, int registeredParties)方法。此方法有2个作用：
 *  1、当每一个阶段执行完毕，此方法会被自动调用，因此，重载此方法写入的代码会在每个阶段执行完毕时执行，
 *  相当于CyclicBarrier的barrierAction。
 *  2、当此方法返回true时，意味着Phaser被终止，因此可以巧妙的设置此方法的返回值来终止所有线程。
 * @author liujun
 */
@Slf4j
public class MyPhaser extends Phaser {

    @Override
    protected boolean onAdvance(int phase, int registeredParties) {	//在每个阶段执行完成后回调的方法

        switch (phase) {
            case 0:
                return studentArrived();
            case 1:
                return finishFirstExercise();
            case 2:
                return finishSecondExercise();
            case 3:
                return finishExam();
            default:
                return true;
        }

    }

    private boolean studentArrived(){
        log.info("学生准备好了,学生人数：{}" , getRegisteredParties());
        return false;
    }

    private boolean finishFirstExercise(){
        log.info("第一题所有学生做完");
        return false;
    }

    private boolean finishSecondExercise(){
        log.info("第二题所有学生做完");
        return false;
    }

    private boolean finishExam(){
        log.info("第三题所有学生做完，结束考试");
        return true;
    }

}
