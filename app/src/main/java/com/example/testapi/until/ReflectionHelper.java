package com.example.testapi.until;

import java.lang.reflect.Method;

/**
 * @author FPT.
 * @name ReflectionHelper.
 * @license Copyright (C) 2015 Pioneer Corporation. All Rights Reserved.
 */
public class ReflectionHelper {

    private static final ReflectionHelper INSTANCE = new ReflectionHelper();

    private ReflectionHelper() {
    }

    public static ReflectionHelper getInstance() {
        return INSTANCE;
    }

    public Method[] getMethodList(String className) {
        Class cls;
        try {
            cls = Class.forName(className);
            return cls.getDeclaredMethods();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}