package com.zaomianbao.math;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @Description MathDemo
 * @Author zaomianbao
 * @Date 2019/7/16
 **/
@Slf4j
public class MathDemo {


    @Test
    public void test1(){
        double exp = Math.exp(2.0);
        log.info("exp值为：{}",exp);
    }

    @Test
    public void test2(){
        log.info("------Complementation------");
        log.info("7 % 4 = {}",7 % 4);
        log.info("(-7) % 4 = {}",(-7) % 4);
        log.info("7 % (-4) = {}",7 % (-4));
        log.info("(-7) % (-4) = {}",(-7) % (-4));

        log.info("------IEEEremainder------");
        log.info("Math.IEEEremainder(7,4) = {}",Math.IEEEremainder(7,4));
        log.info("Math.IEEEremainder(-7,4) = {}",Math.IEEEremainder(-7,4));
        log.info("Math.IEEEremainder(7,-4) = {}",Math.IEEEremainder(7,-4));
        log.info("Math.IEEEremainder(-7,-4) = {}",Math.IEEEremainder(-7,-4));

        log.info("------Modulo Operation------");
        log.info("Math.floorMod(7, 4) = {}",Math.floorMod(7, 4));
        log.info("Math.floorMod(-7, 4) = {}",Math.floorMod(-7, 4));
        log.info("Math.floorMod(7, -4) = {}",Math.floorMod(7, -4));
        log.info("Math.floorMod(-7, -4) = {}",Math.floorMod(-7, -4));
    }

    @Test
    public void test3(){
        long a,b,c,d,e,f,g,h,i,j,x;
        for(long z=1000000000L;z<10000000000L;z++){
            a= z%10;
            b= z/10%10;
            c= z/100%10;
            d= z/1000%10;
            e= z/10000%10;
            f= z/100000%10;
            g= z/1000000%10;
            h= z/10000000%10;
            i= z/100000000%10;
            j= z/1000000000%10;
            x = a*a*a*a*a*a*a*a*a*a+
                b*b*b*b*b*b*b*b*b*b+
                c*c*c*c*c*c*c*c*c*c+
                d*d*d*d*d*d*d*d*d*d+
                e*e*e*e*e*e*e*e*e*e+
                f*f*f*f*f*f*f*f*f*f+
                g*g*g*g*g*g*g*g*g*g+
                h*h*h*h*h*h*h*h*h*h+
                i*i*i*i*i*i*i*i*i*i+
                j*j*j*j*j*j*j*j*j*j;
            if(x==z){
                log.info("水仙花数为：{}",x);
            }
        }
    }

}
