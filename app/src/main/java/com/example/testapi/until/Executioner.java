package com.example.testapi.until;

import android.util.Log;

import com.example.testapi.model.Param;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author FPT.
 * @name Executioner.
 * @license Copyright (C) 2015 Pioneer Corporation. All Rights Reserved.
 */
public class Executioner {


    private static final Executioner INSTANCE = new Executioner();
    private Method[] mMethods;

    private Executioner() {
    }

    public static Executioner getInstance() {
        return INSTANCE;
    }

    public void setMethods(Method[] methods) {
        this.mMethods = methods;
    }

    public void run(int methodIndex, List<Param> params) {
        try {
            Object ret = mMethods[methodIndex].invoke(convertParam(params));
            Log.d("KDz", ret.toString());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private Object[] convertParam(List<Param> params) {
        Object[] objs = new Object[params.size()];
        for (int i = 0; i < params.size(); i++) {
            objs[i] = paramToObj(params.get(i));
        }
        return objs;
    }

    private Object paramToObj(Param p) {
        return p.getType().cast(p.getValue());
    }
}
