package com.zys.utils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class CommonUtils {

    /**
     * 获取实体类的值
     *
     * @param list 实体类集合
     * @throws IllegalAccessException
     */
    public static void getModelValue(List<?> list) throws IllegalAccessException {
        for (Object o : list) {
            for (Field declaredField : o.getClass().getDeclaredFields()) {
                declaredField.setAccessible(true);
                System.out.println(declaredField.get(o));
            }
        }
    }

    public static void stream(List<String> list) {
        //获取list中不为空的元素到collect集合中
        List<String> collect = list.stream()
                .filter(str -> !"".equals(str))
                .collect(Collectors.toList());
    }
}

