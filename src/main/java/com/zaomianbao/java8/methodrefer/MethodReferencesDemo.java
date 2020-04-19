package com.zaomianbao.java8.methodrefer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.*;
import java.util.function.Supplier;

/**
 * @Description Java 8 Method References

    方法引用通过方法的名字来指向一个方法。
    方法引用可以使语言的构造更紧凑简洁，减少冗余代码。
    方法引用使用一对冒号 ::
    方法引用本质上可以属于lambda表达式的范畴

 * @Author zaomianbao
 * @Date 2019/5/25
 **/

@Slf4j
public class MethodReferencesDemo {

    @Test
    public void test1(){

        List<Car> cars = new ArrayList<>();
        cars.add(Car.builder().brand("宝马").registerDate("2019-05-25").seats(4).build());
        cars.add(Car.builder().brand("奔驰").registerDate("2019-05-24").seats(6).build());
        cars.add(Car.builder().brand("奥迪").registerDate("2019-05-26").seats(2).build());
        List<Car> copyCars = new ArrayList<>(10);
        copyCars.addAll(cars);

        //使用匿名内部类进行排序
        Collections.sort(copyCars, new Comparator<Car>() {
            @Override
            public int compare(Car o1, Car o2) {
                return o1.getSeats().compareTo(o2.getSeats());
            }
        });
        copyCars.forEach(x -> log.info("匿名内部类进行排序:{}",x.toString()));
        copyCars.removeAll(cars);
        copyCars.addAll(cars);

        //使用lambda进行排序
        Collections.sort(copyCars, (o1,o2) -> o1.getSeats().compareTo(o2.getSeats()));
        copyCars.forEach(x -> log.info("lambda进行排序:{}",x.toString()));
        copyCars.removeAll(cars);
        copyCars.addAll(cars);

        //使用方法引用进行排序
        Collections.sort(copyCars,Car::compareBySeats);
        copyCars.forEach(x -> log.info("方法引用进行排序:{}",x.toString()));
    }

    @Test
    public void test2(){

        //构造器引用：它的语法是ClassName::new
        Car car = Car.create( Car::new );
        List<Car> cars = Arrays.asList( car );

        //静态方法引用：它的语法是ClassName::static_method
        cars.forEach( Car::collide );

        //特定类的任意对象的方法引用：它的语法是ClassName::method
        cars.forEach( Car::repair );

        Car police = Car.create( Car::new );
        //特定对象的方法引用：它的语法是instance::method
        cars.forEach( police::follow );

        Integer[] intArr = {1, 2, 3, 4, 2, 3, 4, 4, 5};
        String[] strArr = {"One", "Two", "Three", "Two"};
        int count;
        //携带泛型的静态方法引用，格式为在::之后和方法名之前指定方法参数的泛型，当然并非必须显示指定类型参数，因为类型参数会被自动推断得出。
        count=myOperation(MyArrayOperation::<Integer>countMatching, intArr, 4);
        log.info("intArr contains {} 4s" , count);
        count=myOperation(MyArrayOperation::<String>countMatching, strArr, "Two");
        log.info("strArr contains {} Twos" , count);

    }

    @Test
    public void test3(){

        List<String> names = new ArrayList<>();

        names.add("zao");
        names.add("mian");
        names.add("bao");

        //将 Logger对象的info方法
        names.forEach(log::info);
        //将 PrintStream对象的println 方法作为静态方法来引用
        names.forEach(System.out::println);
    }

    public static <T> int myOperation(MyFunctionInterface<T> f, T[] t, T v) {
        return f.operation(t, v);
    }

}

/**
 * 车辆对象
 */
@Slf4j
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class Car {

    /**
     * 注册日期
     */
    private String registerDate;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 座位数
     */
    private Integer seats;

    //Supplier是jdk1.8的函数式接口，这里和lambda一起使用了
    public static Car create(final Supplier<Car> supplier) {
        return supplier.get();
    }

    public static void collide(final Car car) {
        log.info("Collided {}" , car.toString());
    }

    public void follow(final Car another) {
        log.info("Following the {}" , another.toString());
    }

    public void repair() {
        log.info("Repaired {}" , this.toString());
    }

    public static int compareBySeats(Car o1,Car o2){
        return o1.getSeats().compareTo(o2.getSeats());
    }

}

/**
 * 带泛型的自定义的函数式接口
 * @param <T>
 */
interface MyFunctionInterface<T> {

    int operation(T[] t, T v);

}

/**
 * 数组操作类
 */
class MyArrayOperation {

    public static <T> int countMatching(T[] t, T v) {
        int count = 0;
        for (int i = 0; i < t.length; i++) {
            if (t[i] == v) count++;
        }
        return count;
    }

}