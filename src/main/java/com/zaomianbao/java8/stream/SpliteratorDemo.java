package com.zaomianbao.java8.stream;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;

/**
 * @Description SpliteratorDemo
 * @Author zaomianbao
 * @Date 2019/6/3
 **/
@Slf4j
public class SpliteratorDemo {

    @Test
    public void test1(){
        List<String> arrs = new ArrayList<>();
        arrs.add("a");
        arrs.add("b");
        arrs.add("c");
        arrs.add("d");
        arrs.add("e");
        arrs.add("f");
        arrs.add("h");
        arrs.add("i");
        arrs.add("j");
        Spliterator<String> a =  arrs.spliterator();
        //此时结果：a:0-9（index-fence）
        Spliterator<String> b = a.trySplit();
        //此时结果：b:0-4,a:4-9
        Spliterator<String> c = a.trySplit();
        //此时结果：b:0-4,c:4-6,a:6-9
        Spliterator<String> d = a.trySplit();
        //此时结果：b:0-4,c:4-6,d:6-7,a:7-9
        Spliterator<String> e = a.trySplit();
        //此时结果：b:0-4,c:4-6,d:6-7,e:7-8,a:8-9
        Spliterator<String> f = a.trySplit();
        //此时结果：b:0-4,c:4-6,d:6-7,e:7-8,a:8-9,f:null
    }

}
