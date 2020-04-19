package com.zaomianbao.type;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.*;

/**
 * @Description TypeBeanTest
 * @Author zaomianbao
 * @Date 2020/4/19
 **/
@Slf4j
public class TypeBeanTest {

    public static void main(String[] args) {
        Field[] fields = TypeBean.class.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            Type type = field.getGenericType();
            log.info("变量名称：{}",field.getName());
            log.info("变量类型：{}",type.getTypeName());
            if (type instanceof Class) {
                log.info("变量类型为Class");
            }else if (type instanceof ParameterizedType) {
                log.info("变量类型为ParameterizedType");
            }else if (type instanceof WildcardType) {
                log.info("变量类型为WildcardType");
            }else if (type instanceof TypeVariable) {
                log.info("变量类型为TypeVariable");
            }else if (type instanceof GenericArrayType){
                log.info("变量类型为GenericArrayType");
            }
            log.info("========");
        }
    }

}
