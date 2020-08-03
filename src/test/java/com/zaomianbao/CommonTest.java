package com.zaomianbao;

import com.zaomianbao.common.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description CommonTest
 * @Author zaomianbao
 * @Date 2020/6/16
 **/
@Slf4j
public class CommonTest {

    @Test
    public void testCalendar() throws Exception{
        Date time = Calendar.getInstance().getTime();
        log.info("时间：{}",time);
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date startTime = format.parse(format.format(now));
        Date endTime = new Date(startTime.getTime() + 3600 * 24 * 1000);
        log.info("开始:{},结束:{}",startTime,endTime);
    }

    @Test
    public void testBigDecimalToPercent(){

        BigDecimal num1 = new BigDecimal("0");
        BigDecimal num2 = new BigDecimal("133");
        log.info("值：{}",num1.divide(num2,4,BigDecimal.ROUND_HALF_UP));
        NumberFormat percentInstance = NumberFormat.getPercentInstance();
        percentInstance.setMaximumFractionDigits(2);
        log.info("值：{}",percentInstance.format(num1.divide(num2,4,BigDecimal.ROUND_HALF_UP).doubleValue()));

        BigDecimal num3 = new BigDecimal(343).divide(new BigDecimal(343), 4, RoundingMode.HALF_UP);
        log.info("值：{}",percentInstance.format(num3.doubleValue()));
        log.info("值：{}",percentInstance.format(new BigDecimal(1).doubleValue()));

    }

    @Test
    public void testStreamComparing(){
        long now = System.currentTimeMillis();
        Date defaultDate = new Date(now - 300000);
        List<Student> studentList = Arrays.asList(
                Student.builder().birthday(new Date(now - 1000)).createTime(defaultDate).build(),
                Student.builder().birthday(new Date(now + 2000)).createTime(defaultDate).build(),
                Student.builder().birthday(new Date(now - 3000)).createTime(defaultDate).build(),
                Student.builder().birthday(defaultDate).createTime(new Date(now - 4000)).build(),
                Student.builder().birthday(defaultDate).createTime(new Date(now + 5000)).build(),
                Student.builder().birthday(defaultDate).createTime(new Date(now - 6000)).build());
        log.info("排序前：{}",studentList);
        List<Student> collect = studentList.stream().sorted(Comparator.comparing(Student::getBirthday).reversed().thenComparing(Student::getCreateTime).reversed()).collect(Collectors.toList());
        log.info("排序后：{}",collect);
    }

    @Test
    public void testEmptyStreamFilter() {
        List<Student> students = new ArrayList<>();
        List<Student> sdfds = students.stream().filter(item -> item.getName().equals("sdfds")).collect(Collectors.toList());
    }

    @Test
    public void testEmptyStreamCount() {
        List<Student> students = new ArrayList<>();
        long count = students.stream().count();
        log.info("条数为：{}",count);
    }

    @Test
    public void testStringByte() throws UnsupportedEncodingException {
        String a = "485e-9d5a-2e7208c4efe9-cn.dfub.lbocj.biz.common.ErrInterceptor ==> ==>服务出现异常 \n" +
                "[2020-08-03 14:31:18.721] [ERROR] [http-nio-8011-exec-8]--50d0dfcb-a2ae-485e-9d5a-2e7208c4efe9-cn.dfub.lbocj.biz.common.ErrInterceptor ==> ==>服务出现异常 ExceptionResult(code=0, message=null, data=null) null\n" +
                "[2020-08-03 14:31:18.958] [ERROR] [http-nio-8011-exec-2]--1a6ec844-cea3-47cc-bab5-5668d9544f60-cn.dfub.lbocj.biz.common.ErrInterceptor ==> ==>服务出现异常 \n" +
                "[2020-08-03 14:31:18.958] [ERROR] [http-nio-8011-exec-2]--1a6ec844-cea3-47cc-bab5-5668d9544f60-cn.dfub.lbocj.biz.common.ErrInterceptor ==> ==>服务出现异常 ExceptionResult(code=0, message=null, data=null) null\n" +
                "[2020-08-03 14:31:19.165] [ERROR] [http-nio-8011-exec-4]--92160420-29a5-432c-93bb-46dfa36b99ff-cn.dfub.lbocj.biz.common.ErrInterceptor ==> ==>服务出现异常 \n" +
                "[2020-08-03 14:31:19.165] [ERROR] [http-nio-8011-exec-4]--92160420-29a5-432c-93bb-46dfa36b99ff-cn.dfub.lbocj.biz.common.ErrInterceptor ==> ==>服务出现异常 ExceptionResult(code=0, message=null, data=null) null\n" +
                "[2020-08-03 14:31:21.865] [ERROR] [http-nio-8011-exec-10]--e30ecc64-6541-4af7-afd3-1a4def87ad3a-cn.dfub.lbocj.biz.common.ErrInterceptor ==> ==>服务出现异常 \n" +
                "[2020-08-03 14:31:21.866] [ERROR] [http-nio-8011-exec-10]--e30ecc64-6541-4af7-afd3-1a4def87ad3a-cn.dfub.lbocj.biz.common.ErrInterceptor ==> ==>服务出现异常 ExceptionResult(code=0, message=null, data=null) null\n" +
                "[2020-08-03 14:31:22.055] [ERROR] [http-nio-8011-exec-6]--dd21b4ec-83a3-4e23-9181-353bd12dfbab-cn.dfub.lbocj.biz.common.ErrInterceptor ==> ==>服务出现异常 \n" +
                "[2020-08-03 14:31:22.055] [ERROR] [http-nio-8011-exec-6]--dd21b4ec-83a3-4e23-9181-353bd12dfbab-cn.dfub.lbocj.biz.common.ErrInterceptor ==> ==>服务出现异常 ExceptionResult(code=0, message=null, data=null) null\n" +
                "[2020-08-03 14:31:22.262] [ERROR] [http-nio-8011-exec-7]--6d158b3b-af4f-4ad1-9f41-deaf248ac66a-cn.dfub.lbocj.biz.common.ErrInterceptor ==> ==>服务出现异常 \n" +
                "[2020-08-03 14:31:22.262] [ERROR] [http-nio-8011-exec-7]--6d158b3b-af4f-4ad1-9f41-deaf248ac66a-cn.dfub.lbocj.biz.common.ErrInterceptor ==> ==>服务出现异常 ExceptionResult(code=0, message=null, data=null) null\n" +
                "[2020-08-03 14:31:22.553] [ERROR] [http-nio-8011-exec-1]--1f93e784-5ca2-4567-aa4e-32309bc3d295-cn.dfub.lbocj.biz.common.ErrInterceptor ==> ==>服务出现异常 \n" +
                "[2020-08-03 14:31:22.553] [ERROR] [http-nio-8011-exec-1]--1f93e784-5ca2-4567-aa4e-32309bc3d295-cn.dfub.lbocj.biz.common.ErrInterceptor ==> ==>服务出现异常 ExceptionResult(code=0, message=null, data=null) null\n" +
                "[2020-08-03 14:31:22.688] [ERROR] [http-nio-8011-exec-5]--a97789ab-e129-4f01-a673-d1184f1943d0-cn.dfub.lbocj.biz.common.ErrInterceptor ==> ==>服务出现异常 \n" +
                "[2020-08-03 14:31:22.688] [ERROR] [http-nio-8011-exec-5]--a97789ab-e129-4f01-a673-d1184f1943d0-cn.dfub.lbocj.biz.common.ErrInterceptor ==> ==>服务出现异常 ExceptionResult(code=0, message=null, data=null) null\n" +
                "[2020-08-03 14:31:22.911] [ERROR] [http-nio-8011-exec-9]--4d193907-431c-4967-99a5-84bc07e61be1-cn.dfub.lbocj.biz.common.ErrInterceptor ==> ==>服务出现异常 \n" +
                "[2020-08-03 14:31:22.912] [ERROR] [http-nio-8011-exec-9]--4d193907-431c-4967-99a5-84bc07e61be1-cn.dfub.lbocj.biz.common.ErrInterceptor ==> ==>服务出现异常 ExceptionResult(code=0, message=null, data=null) null\n" +
                "[2020-08-03 14:31:23.350] [ERROR] [http-nio-8011-exec-3]--3c39ec7f-c333-49c0-887e-42dd4baa14bd-cn.dfub.lbocj.biz.common.ErrInterceptor ==> ==>服务出现异常 \n" +
                "[2020-08-03 14:31:23.350] [ERROR] [http-nio-8011-exec-3]--3c39ec7f-c333-49c0-887e-42dd4baa14bd-cn.dfub.lbocj.biz.common.ErrInterceptor ==> ==>服务出现异常 ExceptionResult(code=0, message=null, data=null) null\n" +
                "[2020-08-03 14:31:23.839] [ERROR] [http-nio-8011-exec-8]--50d0dfcb-a2ae-485e-9d5a-2e7208c4efe9-cn.dfub.lbocj.biz.common.ErrInterceptor ==> ==>服务出现";
        a = URLEncoder.encode(a, "UTF-8");
        log.info("a的字节数:{}",a.getBytes().length);
    }

}
