package com.zaomianbao.java8.lambda;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.function.Consumer;

/**
 * @Description Java 8 Lambda 表达式
 * @Author zaomianbao
 * @Date 2019/5/24

    lambda表达式定义的是操作或者说是动作。
    并且其返回值是一个Functional Interface对象。
    同时可以作为参数传入到方法中执行，而执行的具体动作就取决于我们书写的lambda表达式定义了什么动作。
    lambda表达式本质就是一个匿名函数。
    lambda表达式使Java在具有面向对象的同时也具有函数式编程的可能性。

    以下是lambda表达式的重要特征:

    可选类型声明：不需要声明参数类型，编译器可以统一识别参数值。
    可选的参数圆括号：一个参数无需定义圆括号，但多个参数需要定义圆括号。
    可选的大括号：如果主体包含了一个语句，就不需要使用大括号。
    可选的返回关键字：如果主体只有一个表达式返回值则编译器会自动返回值，大括号需要指定明表达式返回了一个数值。

 **/
@Slf4j
public class LambdaDemo {

    @Test
    public void test1(){
        LambdaDemo tester = new LambdaDemo();

        // 类型声明
        MathOperation addition = (int a, int b) -> a + b;

        // 不用类型声明
        MathOperation subtraction = (a, b) -> a - b;

        // 大括号中的返回语句
        MathOperation multiplication = (int a, int b) -> {
            a++;
            b++;
            return a * b;
        };

        // 没有大括号及返回语句
        MathOperation division = (int a, int b) -> a / b;
        
        log.info("10 + 5 = {}" , tester.operate(10, 5, addition));
        log.info("10 - 5 = {}" , tester.operate(10, 5, subtraction));
        log.info("10 x 5 = {}" , tester.operate(10, 5, multiplication));
        log.info("10 / 5 = {}" , tester.operate(10, 5, division));

        // 不用括号
        GreetingService greetService1 = message -> log.info("Hello {}" , message);

        // 用括号
        GreetingService greetService2 = (message) -> log.info("Hello {}" , message);

        greetService1.sayMessage("zaomianbao1");
        greetService2.sayMessage("zaomianbao2");
    }

    /**

      作为lambda表达式形参的外部变量不用强制使用final修饰，但是即使不用修饰，该变量也不允许被修改

     */
    @Test
    public void test2(){

        final int num = 1;
        Converter s1 = (param) -> log.info(String.valueOf(param + num));
        s1.convert(2);

        //可以不用强制声明final
        int number = 1;
        Converter s2 = (param) -> log.info(String.valueOf(param + number));
        s2.convert(2);
        //number++;
    }

    @Test
    public void test3(){

        //lambda表达式的返回值是一个Functional Interface对象，我们可以看到，只要符合定义的规则，返回的对象可以是不同的，重点是定义的动作
        GreetingService one = x -> log.info(x);
        Consumer<String> two = x -> log.info(x);
    }

    interface MathOperation {
        int operation(int a, int b);
    }

    interface GreetingService {
        void sayMessage(String message);
    }

    private int operate(int a, int b, MathOperation mathOperation){
        return mathOperation.operation(a, b);
    }

    public interface Converter {
        void convert(int i);
    }
}
