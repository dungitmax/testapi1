package com.example.testapi.until;

import android.util.Log;

import com.example.testapi.model.MethodDetail;
import com.example.testapi.model.Param;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static com.example.testapi.Const.TAG;

/**
 * Handles all the operations regarding Java Reflection.
 */
public class ReflectionHelper {

    private static final ReflectionHelper INSTANCE = new ReflectionHelper();

    private ReflectionHelper() {
    }

    public static ReflectionHelper getInstance() {
        return INSTANCE;
    }

    /**
     * List all methods of a given class name.
     * @param className: Full name of the class, example: java.util.String.
     * @return All methods in the form of a MethodDetail object list.
     */
    public List<MethodDetail> getMethodDetailList(String className) {
        Log.d(TAG, "ReflectionHelper#getMethodDetailList() called with: className = [" + className + "]");
        List<MethodDetail> methodDetailList = new ArrayList<>();

        Class clazz;
        Method[] methods;
        try {
            clazz = Class.forName(className);
            methods = clazz.getDeclaredMethods();
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "ReflectionHelper#getMethodDetailList: " + e.getMessage());
            e.printStackTrace();
            return methodDetailList;
        }
        Log.d(TAG, "ReflectionHelper#getMethodDetailList: methods.length = " + methods.length);
        MethodDetail md;
        for (Method m : methods) {
            Class[] params = m.getParameterTypes();
            List<Param> paramList = new ArrayList<>();
            Param p;
            for (Class c : params) {
                p = new Param(c.getSimpleName(), c, null);
                paramList.add(p);
            }
            md = new MethodDetail(clazz, m.getName(), paramList);
            methodDetailList.add(md);
            Log.d(TAG, "ReflectionHelper#getMethodDetailList: method = " + m.getName() + ", params num = " + paramList.size());
        }
        return methodDetailList;
    }

    /**
     * Run a method specified in a MethodDetail object.
     *
     * @param methodDetail: MethodDetail of the method you want to execute.
     * @return Return value of that method.
     */
    public Object run(MethodDetail methodDetail) {
        Log.d(TAG, "ReflectionHelper#run() called with: methodDetail = [" + methodDetail + "]");
        Class clazz = methodDetail.getClazz();
        String methodName = methodDetail.getMethodName();
        Class[] paramTypes = getParamsType(methodDetail.getParams());
        Object ret = null;
        try {
            Method method = clazz.getDeclaredMethod(methodName, paramTypes);
            Object receiver = clazz.newInstance();
            ret = run(method, receiver, methodDetail.getParams());
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            ret = e.getMessage();
        }
        Log.d(TAG, "ReflectionHelper#run: ret = " + ret);
        return ret;
    }

    private Object run(Method method, Object receiver, List<Param> paramList) {
        Log.d(TAG, "run() called with: method = [" + method + "], receiver = [" + receiver + "], paramList = [" + paramList + "]");
        Object ret;
        try {
            ret = method.invoke(receiver, paramsToVargs(paramList));
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            ret = e.getMessage();
        }
        Log.d(TAG, "ReflectionHelper#run: ret = " + ret);
        return ret;
    }

    private Object[] paramsToVargs(List<Param> paramList) {
        Object[] varArgs = new Object[paramList.size()];
        for (int i = 0; i < paramList.size(); i++) {
            varArgs[i] = paramList.get(i).getValue();
        }
        return varArgs;
    }

    private Class[] getParamsType(List<Param> paramList) {
        Class[] types = new Class[paramList.size()];
        for (int i = 0; i < paramList.size(); i++) {
            types[i] = paramList.get(i).getType();
        }
        return types;
    }

}