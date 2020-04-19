package com.zaomianbao.java8.stream;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @Description Reduce Demo
 * @Author zaomianbao
 * @Date 2019/6/11
 **/
@Slf4j
public class ReduceDemo {

    @Test
    public void test1(){
        String concat1 = Stream.of("A", "B", "C", "D").reduce("", String::concat);
        log.info("拼接1的值为：{}",concat1);

        Optional<String> concat2Optional = Stream.of("A", "B", "C", "D").reduce(String::concat);
        String concat2 = concat2Optional.get();
        log.info("拼接2的值为：{}",concat2);

        List<Integer> numList = Arrays.asList(1, 2, 3, 4, 5, 6);
        log.info("Integer类型集合中的值为:{}",numList);
        List<String> result = numList.stream().reduce(
                new ArrayList<String>(),
                (a, b) -> {
                    a.add("element-" + Integer.toString(b));
                    return a;
                },
                (a, b) -> null);
        log.info("String类型集合中的值为:{}",result);

        //输出结果为：1716   (10+1)*(10+2)*(10+3)
        Integer parallelResult1 = Stream.of(1,2,3).parallel().reduce(10, (x, y) -> x + y, (x, y) -> x * y);
        log.info("并行下打印结果:{}",parallelResult1);
        //输出结果为16     10+1+2+3
        Integer result1 = Stream.of(1,2,3).reduce(10, (x, y) -> x + y, (x, y) -> x * y);
        log.info("非并行下打印结果:{}",result1);
        Integer result2 = Stream.of(1,2,3).reduce(10, (x, y) -> x + y);
        log.info("非并行下重载方法一打印结果:{}",result2);

        log.info("非并行累加结果:{}",Stream.of(1,2,3,4,5).reduce(0,(x,y) -> x + y,(x,y) -> x + y));
        log.info("并行累加结果:{}",Stream.of(1,2,3,4,5).parallel().reduce(0,(x,y) -> x + y,(x,y) -> x + y));

    }

}
