package com.example.testapi.model;

import java.util.List;

/**
 * An object to hold necessary information of a method.
 */
public class MethodDetail {

    //Class contains this method.
    private Class mClass;
    private String mMethodName;
    //Params of this method.
    private List<Param> mParams;

    public MethodDetail(final Class mClass, final String mMethodName, final List<Param> mParams) {
        this.mClass = mClass;
        this.mMethodName = mMethodName;
        this.mParams = mParams;
    }

    public MethodDetail() {
    }

    public Class getClazz() {
        return mClass;
    }

    public void setClazz(final Class mClass) {
        this.mClass = mClass;
    }

    public String getMethodName() {
        return mMethodName;
    }

    public void setMethodName(String mMethodName) {
        this.mMethodName = mMethodName;
    }

    public List<Param> getParams() {
        return mParams;
    }

    public void setParams(List<Param> mParams) {
        this.mParams = mParams;
    }

}
