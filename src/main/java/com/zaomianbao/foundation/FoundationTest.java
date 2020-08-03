package com.zaomianbao.foundation;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 基础功能测试
 * @Author zaomianbao
 * @Date 2020/6/11
 **/
@Slf4j
public class FoundationTest {

    @Test
    public void testIntegerWithList(){
        List<Integer> list = new ArrayList<>();
        list.add(3000);
        log.info("结果：{}",list.contains(3000));
    }

}
