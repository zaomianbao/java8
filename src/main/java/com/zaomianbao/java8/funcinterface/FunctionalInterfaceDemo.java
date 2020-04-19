package com.zaomianbao.java8.funcinterface;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.*;

/**
 * @Description Java 8 Functional Interface
 * @Author zaomianbao
 * @Date 2019/5/25
 **/
@Slf4j
public class FunctionalInterfaceDemo {

    @Test
    public void test1(){

        //lambda表达式不能是泛型的，但是与lambda表达式关联的函数式接口可以是泛型的
        MyFunctionInterfaceOne<String> a = x -> log.info(x);
        MyFunctionInterfaceTwo<String> b = x -> System.out.println(x);

        FunctionalInterfaceDemo.arraySay(b, Arrays.asList("zao","mian","bao"));
    }

    private static <T> void arraySay(MyFunctionInterfaceTwo<T> operation , List<T> list ){
        if (list instanceof RandomAccess){
            for (int i = 0; i < list.size(); i++) {
                operation.saySomething(list.get(i));
            }
        }else {
            Iterator<T> iterator = list.iterator();
            for (int i = 0; i < list.size(); i++) {
                T next = iterator.next();
                operation.saySomething(next);
            }
        }
    }

}

/**
 * 所谓的函数式接口，首先是一个接口，然后在这个接口里面只能有一个抽象方法
 * 这种类型的接口也称为SAM接口，即Single Abstract Method Interfaces
 * Java 8为函数式接口引入了一个新注解@FunctionalInterface，主要用于编译级错误检查，加上该注解，当你写的接口不符合函数式接口定义的时候，编译器会报错
 */
@FunctionalInterface
interface MyFunctionInterfaceOne<T>{

    void saySomething(T t);

}

@FunctionalInterface
interface MyFunctionInterfaceTwo<T>{


    void saySomething(T t);

    /**
     * 函数式接口里是可以包含默认方法，因为默认方法不是抽象方法，其有一个默认实现，所以是符合函数式接口的定义的；
     */
    default void sayHello(){
        System.out.println("hello");
    }

    /**
     * 函数式接口里是可以包含静态方法，因为静态方法不能是抽象方法，是一个已经实现了的方法，所以是符合函数式接口的定义的；
     */
    static void sayGoodbye(){
        System.out.println("goodbye");
    }

    /**
     * 函数式接口里是可以包含Object里的public方法，这些方法对于函数式接口来说，不被当成是抽象方法（虽然它们是抽象方法）；
     * 因为任何一个函数式接口的实现，默认都继承了Object类，包含了来自java.lang.Object里对这些抽象方法的实现；
     * @param anObject
     * @return
     */
    @Override
    boolean equals(Object anObject);

}
