package com.zaomianbao.java8.stream;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import static com.zaomianbao.java8.stream.Cons.*;

/**
 * @Description Stream Demo
 * @Author zaomianbao
 * @Date 2019/6/6
 **/
@Slf4j
public class StreamDemo {

    /**
     * 一个流操作的示例
     */
    @Test
    public void test1(){

        List<Widget> widgets = Arrays.asList(
                Widget.builder().color("red").weight(100).build(),
                Widget.builder().color("yellow").weight(50).build(),
                Widget.builder().color("red").weight(25).build(),
                Widget.builder().color("red").weight(300).build());
        int sum = widgets.stream()
                .filter(w -> w.getColor().equals("red"))
                .mapToInt(Widget::getWeight)
                .sum();
        log.info("总重：{}",sum);
    }

    /**
     * 构造流的几种常见方法
     */
    @Test
    public void test2(){
        // 1. Individual values
        Stream stream = Stream.of("a", "b", "c");
        stream.count();
        // 2. Arrays
        String [] strArray = new String[] {"a", "b", "c"};
        stream = Stream.of(strArray);
        stream.count();
        stream = Arrays.stream(strArray);
        stream.count();
        // 3. Collections
        List<String> list = Arrays.asList(strArray);
        stream = list.stream();
        stream.count();
    }

    /**
     * 数值流的构造
     */
    @Test
    public void test3(){
        IntStream.of(new int[]{1, 2, 3}).forEach(System.out::println);
        IntStream.range(1, 3).forEach(System.out::println);
        IntStream.rangeClosed(1, 3).forEach(System.out::println);
    }

    /**
     * 流转换为其它数据结构
     * 一个 Stream 只可以使用一次，下面的代码为了简洁而重复使用了数次
     */
    @Test
    public void test4(){
        Stream<String> stream = Stream.of("a", "b", "c");
        // 1. Array
        String[] strArray1 = stream.toArray(String[]::new);
        // 2. Collection
        List<String> list1 = stream.collect(Collectors.toList());
        List<String> list2 = stream.collect(Collectors.toCollection(ArrayList::new));
        Set set1 = stream.collect(Collectors.toSet());
        Stack stack1 = stream.collect(Collectors.toCollection(Stack::new));
        // 3. String
        String str = stream.collect(Collectors.joining()).toString();
    }

    @Test
    public void testLimitAndSkip() {
        List<Person> persons = new ArrayList();
        for (int i = 1; i <= 10000; i++) {
            Person person = new Person(i, "name" + i);
            persons.add(person);
        }
        List<String> personList2 = persons.stream()
                .map(Person::getName)
                .limit(10)
                .skip(3)
                .peek(System.out::println)
                .collect(Collectors.toList());
        System.out.println(personList2);

        Stream.of(1,6,4,2,3,4,5)
                .peek(x->System.out.print("\nA"+x))
                .limit(3)
                .peek(x->System.out.print("B"+x))
                .forEach(x->System.out.print("C"+x));

        Stream.of(1,2,3,4,5)
                .peek(x->System.out.print("\nA"+x))
                .skip(3)
                .peek(x->System.out.print("B"+x))
                .forEach(x->System.out.print("C"+x));

        System.out.println();
        List<Person> persons2 = new ArrayList();
        for (int i = 1; i <= 5; i++) {
            Person person = new Person(i, "name" + i);
            persons2.add(person);
        }
        List<Person> personList = persons2.stream()
                .sorted(Comparator.comparing(p -> p.getName()))
                .peek(x->System.out.println("\nA"+x.getName()))
                .limit(2)
                .collect(Collectors.toList());
        System.out.println(personList);

    }

    @Test
    public void testStreamTheory(){
        List<Person> persons = new ArrayList<>();
        for (int i = 1; i <= 10000; i++) {
            Person person = new Person(i, "name" + i);
            persons.add(person);
        }
        Stream<Person> head = persons.stream();
        Stream<Person> stage1 = head.filter(one -> one.getNo() > 9990);
        Stream<String> stage2 = stage1.map(Person::getName);
        Stream<String> stage3 = stage2.sorted(Comparator.comparingInt(one -> Integer.parseInt(one.replace("name", ""))));
        Collector<String, ?, List<String>> collector = Collectors.toList();
        List<String> result = stage3.collect(collector);
        log.info("打印: {}",result);
    }

    @Test
    public void testStreamAllMethod(){

        List<Person> persons = new ArrayList<>();
        for (int i = 1; i <= 10000; i++) {
            Person person = new Person(i, "name" + i+",name"+(i+5000));
            persons.add(person);
        }
        List<String> list = persons.stream()
                .filter(one -> one.getNo() % 2 == 0)
                .map(Person::getName)
                .flatMap(one -> Stream.of(one.split(",")))
                .sorted(Comparator.comparingInt(one -> Integer.parseInt(one.replace("name", ""))))
                .distinct()
                .limit(10)
                .skip(3)
                .peek(one -> log.info("当前还剩:{}", one))
                .collect(Collectors.toList());
        log.info("打印:{}",list);

        Stream<Person> head = persons.stream();
        Stream<Person> stage1 = head.filter(one -> one.getNo()%2==0);
        Stream<String> stage2 = stage1.map(Person::getName);
        Stream<String> stage3 = stage2.flatMap(one -> Stream.of(one.split(",")));
        Stream<String> stage4 = stage3.sorted(Comparator.comparingInt(one -> Integer.parseInt(one.replace("name", ""))));
        Stream<String> stage5 = stage4.distinct();
        Stream<String> stage6 = stage5.limit(10);
        Stream<String> stage7 = stage6.skip(3);
        Stream<String> stage8 = stage7.peek(one -> log.info("当前还剩:{}", one));
        Collector<String, ?, List<String>> collector = Collectors.toList();
        List<String> result = stage8.collect(collector);
        log.info("打印:{}",result);
    }

    @Test
    public void testListPartition(){

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        Integer maxSend = 3;
        int limit = countStep(list.size(),maxSend);
        //方法一：使用流遍历操作
        List<List<Integer>> mglist = new ArrayList<>();
        List<Integer> collect = Stream.iterate(0, n -> n + 1).limit(limit).collect(Collectors.toList());
        System.out.println(collect);
        Stream.iterate(0, n -> n + 1)
                .limit(limit)
                .forEach(i -> mglist.add(list.stream().skip(i * maxSend).limit(maxSend).collect(Collectors.toList())));

        System.out.println(mglist);

        //方法二：获取分割后的集合
        List<List<Integer>> splitList =
                Stream.iterate(0, n -> n + 1)
                        .limit(limit).parallel()
                        .map(a -> list.stream().skip(a * maxSend).limit(maxSend).parallel().collect(Collectors.toList()))
                        .collect(Collectors.toList());

        System.out.println(splitList);

        //方法三：google guava工具类
        List<List<Integer>> parts = Lists.partition(list, 3);
        System.out.println(parts);

    }

    @Test
    public void testStreamIndex(){
        List<Person> persons = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Person person = new Person(null, "name" + i+",name"+(i+5000));
            persons.add(person);
        }
        AtomicInteger a = new AtomicInteger(1);
        persons = persons.stream().map(item -> {
            item.setNo(a.getAndIncrement());
            return item;
        }).collect(Collectors.toList());
        System.out.println(persons);

    }

    @Test
    public void test11(){
        List<Person1> persons = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            Cons no = A;
            switch (i%4){
                case 0:
                    no = A;
                    break;
                case 1:
                    no = B;
                    break;
                case 2:
                    no = C;
                    break;
                case 3:
                    no = D;
                    break;
            }
            Person1 person = new Person1(no, i);
            persons.add(person);
        }
        List<Person1> filterResult = persons.stream().filter(Person1::predicate).collect(Collectors.toList());
        log.info("属于A、B，同时年龄大于18的person1：{}",filterResult);
    }

    @Test
    public void test12(){

        List<Order> orders = Arrays.asList(Order.builder().addressList(Arrays.asList(Address.builder().ip("2").email("a").build(),Address.builder().ip("1").email("b").build())).name("liu").build(),
                Order.builder().addressList(Arrays.asList(Address.builder().ip("7").email("d").build(),Address.builder().ip("8").email("b").build())).name("wa").build(),
                Order.builder().addressList(Arrays.asList(Address.builder().ip("21").email("z").build(),Address.builder().ip("11").email("f").build())).name("li").build());
        List<Order> orderList = orders.stream().filter(one -> one.getName().equals("liu")).peek(one -> {
            one.setAddressList(one.getAddressList().stream().sorted(Comparator.comparing(Address::getIp)).collect(Collectors.toList()));
        }).collect(Collectors.toList());
        log.info(orderList.toString());
    }

    @Test
    public void test13(){
        log.info("使用 Java 7: ");

        // 计算空字符串
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
        log.info("列表: {}" , strings);
        long count = getCountEmptyStringUsingJava7(strings);

        log.info("空字符数量为: {}" ,  count);
        count = getCountLength3UsingJava7(strings);

        log.info("字符串长度为 3 的数量为: {}" ,  count);

        // 删除空字符串
        List<String> filtered = deleteEmptyStringsUsingJava7(strings);
        log.info("筛选后的列表: {}" ,  filtered);

        // 删除空字符串，并使用逗号把它们合并起来
        String mergedString = getMergedStringUsingJava7(strings, ", ");
        log.info("合并字符串: {}" ,  mergedString);
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);

        // 获取列表元素平方数
        List<Integer> squaresList = getSquares(numbers);
        log.info("平方数列表: {}" ,  squaresList);
        List<Integer> integers = Arrays.asList(1,2,13,4,15,6,17,8,19);

        log.info("列表: {}" , integers);
        log.info("列表中最大的数 : {}" ,  getMax(integers));
        log.info("列表中最小的数 : {}" ,  getMin(integers));
        log.info("所有数之和 : {}" ,  getSum(integers));
        log.info("平均数 : {}" ,  getAverage(integers));
        log.info("随机数: ");

        // 输出10个随机数
        Random random = new Random();

        for(int i=0; i < 10; i++){
            log.info(random.nextInt()+"");
        }

        log.info("使用 Java 8: ");
        log.info("列表: {}" , strings);

        count = strings.stream().filter(string->string.isEmpty()).count();
        log.info("空字符串数量为: {}" ,  count);

        count = strings.stream().filter(string -> string.length() == 3).count();
        log.info("字符串长度为 3 的数量为: {}" ,  count);

        filtered = strings.stream().filter(string ->!string.isEmpty()).collect(Collectors.toList());
        log.info("筛选后的列表: {}" ,  filtered);

        mergedString = strings.stream().filter(string ->!string.isEmpty()).collect(Collectors.joining(", "));
        log.info("合并字符串: {}" ,  mergedString);

        squaresList = numbers.stream().map( i ->i*i).distinct().collect(Collectors.toList());
        log.info("Squares List: {}" ,  squaresList);
        log.info("列表: {}" , integers);

        IntSummaryStatistics stats = integers.stream().mapToInt((x) ->x).summaryStatistics();

        log.info("列表中最大的数 : {}" ,  stats.getMax());
        log.info("列表中最小的数 : {}" ,  stats.getMin());
        log.info("所有数之和 : {}" ,  stats.getSum());
        log.info("平均数 : {}" ,  stats.getAverage());
        log.info("随机数: ");

        random.ints().limit(10).sorted().forEach(System.out::println);

        // 并行处理
        count = strings.parallelStream().filter(string -> string.isEmpty()).count();
        log.info("空字符串的数量为: {}" ,  count);
    }

    private static int getCountEmptyStringUsingJava7(List<String> strings){
        int count = 0;

        for(String string: strings){

            if(string.isEmpty()){
                count++;
            }
        }
        return count;
    }

    private static int getCountLength3UsingJava7(List<String> strings){
        int count = 0;

        for(String string: strings){

            if(string.length() == 3){
                count++;
            }
        }
        return count;
    }

    private static List<String> deleteEmptyStringsUsingJava7(List<String> strings){
        List<String> filteredList = new ArrayList<>();

        for(String string: strings){

            if(!string.isEmpty()){
                filteredList.add(string);
            }
        }
        return filteredList;
    }

    private static String getMergedStringUsingJava7(List<String> strings, String separator){
        StringBuilder stringBuilder = new StringBuilder();

        for(String string: strings){

            if(!string.isEmpty()){
                stringBuilder.append(string);
                stringBuilder.append(separator);
            }
        }
        String mergedString = stringBuilder.toString();
        return mergedString.substring(0, mergedString.length()-2);
    }

    private static List<Integer> getSquares(List<Integer> numbers){
        List<Integer> squaresList = new ArrayList<>();

        for(Integer number: numbers){
            Integer square = new Integer(number.intValue() * number.intValue());

            if(!squaresList.contains(square)){
                squaresList.add(square);
            }
        }
        return squaresList;
    }

    private static int getMax(List<Integer> numbers){
        int max = numbers.get(0);

        for(int i=1;i < numbers.size();i++){

            Integer number = numbers.get(i);

            if(number.intValue() > max){
                max = number.intValue();
            }
        }
        return max;
    }

    private static int getMin(List<Integer> numbers){
        int min = numbers.get(0);

        for(int i=1;i < numbers.size();i++){
            Integer number = numbers.get(i);

            if(number.intValue() < min){
                min = number.intValue();
            }
        }
        return min;
    }

    private static int getSum(List numbers){
        int sum = (int)(numbers.get(0));

        for(int i=1;i < numbers.size();i++){
            sum += (int)numbers.get(i);
        }
        return sum;
    }

    private static int getAverage(List<Integer> numbers){
        return getSum(numbers) / numbers.size();
    }

    /**
     * 计算切分次数
     */
    private static Integer countStep(Integer size,Integer maxSend) {
        return (size + maxSend - 1) / maxSend;
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Person1{
    public Cons no;
    private Integer age;

    public static boolean predicate(Person1 person1){
        if ((person1.getNo().equals(A)||person1.getNo().equals(B))&&person1.getAge()>18){
            return true;
        }
        return false;
    }
}

enum Cons{
    A,
    B,
    C,
    D;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class Widget{

    private String color;

    private Integer weight;


}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class Person {
    public Integer no;
    private String name;
    public String getName() {
        //System.out.println(name);
        return name;
    }
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class Order{
    private String name;
    private String desc;
    private List<Address> addressList;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class Address{
    private String ip;
    private String email;
}





