package com.zaomianbao.binary;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @Description 位运算demo
 * @Author zaomianbao
 * @Date 2019/6/3
 **/
@Slf4j
public class BitOperationDemo {

    /**
     * 位运算自动类型提升
     */
    @Test
    public void test1(){

        //byte类型的数值在位运算符下得到的是一个int类型的数值,当然,也可以使用更多字节的long类型来接收
        byte a = -128;
        byte b = 63;
        int result1 = a << 1;
        int result2 = a >> 1;
        int result3 = a >>> 1;
        int result4 = a & b;
        int result5 = a | b;
        int result6 = a ^ b;
        int result7 = ~ a;

        //short也一样
        short c = 64;
        short d = 63;
        result1 = c << 1;
        result2 = c >> 1;
        result3 = c >>> 1;
        result4 = c & d;
        result5 = c | d;
        result6 = c ^ d;
        result7 = ~ c;

        //char也一样
        char e = 'a';
        char f = 'b';
        result1 = e << 1;
        result2 = e >> 1;
        result3 = e >>> 1;
        result4 = e & f;
        result5 = e | f;
        result6 = e ^ f;
        result7 = ~ e;

        //long类型数值在位运算符下得到的是一个long类型的数值,也只能使用long类型来接受
        long g = 10;
        long h = 11;
        long result8 = g << 1;
        long result9 = g >> 1;
        long result11 = g >>> 1;
        long result12 = g & h;
        long result13 = g | h;
        long result14 = g ^ h;
        long result15 = ~ g;

        //强转会直接截取字节，从而导致丢失精度
        byte result16 = (byte)(a << 1);
        byte result17 = (byte)(a >> 1);
        byte result18 = (byte)(a >>> 1);
        byte result19 = (byte)(a & b);
        byte result20 = (byte)(a | b);
        byte result21 = (byte)(a ^ b);
        byte result22 = (byte)(~ a);

    }

    @Test
    public void test2(){
        int a = 2147483647;
        int b = -2147483648;
        log.info(Integer.toBinaryString(a));
        log.info(Integer.toBinaryString(b));
        log.info(Integer.toBinaryString(108));
        log.info(Integer.toBinaryString(-256));
        log.info(Integer.toBinaryString(0));
        int c = 0b1111111111111111111111111111111;
        int d = 0b01111111111111111111111111111111;
        int e = 0b1101100000;
        int f = 0b11111111111111111111111111111111;
        log.info(c+"");
        log.info(d+"");
        log.info(e+"");
        log.info(f+"");
    }

    @Test
    public void test3(){
        int a = 1000000001;
        int b = 200001922;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            int c = (a + b)/2;
            a ++;
            b ++;
        }
        long end = System.currentTimeMillis();
        log.info((float) (end - start) / 1000 + "秒");

        start = System.currentTimeMillis();
        int e = 1000000001;
        int f = 300001922;
        for (int i = 0; i < 10000000; i++) {
            int c = (e & f) + ((e ^ f) >> 1);
            e ++;
            f ++;
        }
        end = System.currentTimeMillis();
        log.info((float) (end - start) / 1000 + "秒");
    }

    @Test
    public void test4(){
        String a = "sadfsdfsdfhfghf123dfgfg";
        System.out.println(a);
        int key = 324545231;
        byte[] bytes = a.getBytes();
        for (int i = 0; i < bytes.length-1; i++) {
            bytes[i] = (byte)(bytes[i] ^ key);
        }
        String b = new String(bytes);
        System.out.println(b);

        for (int i = 0; i < bytes.length-1; i++) {
            bytes[i] = (byte)(bytes[i] ^ key);
        }
        String c = new String(bytes);
        System.out.println(c);
    }

}
