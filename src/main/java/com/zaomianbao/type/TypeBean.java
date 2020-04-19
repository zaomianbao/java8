package com.zaomianbao.type;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Description TypeBean
 * @Author zaomianbao
 * @Date 2020/4/19
 **/
@Data
public class TypeBean<T> {
    private T t;
    private String string;
    private Map<String,String> map;
    private List<T> list;
    private int aint;
    private T[] tArray;
    private int[] intAarry;
    private Integer[] integers;
    private List<String>[] lists;
    private Class<?> clazz;
    private EnumTest enumTest;
    private InterfaceTest interfaceTest;
    private AnnotationTest annotationTest;
}
