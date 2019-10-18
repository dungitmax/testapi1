package com.example.testapi.model;

import java.util.List;

/**
 *
 */
public class MethodDetail {

    private String mMethodName;
    private List<Param> mParams;

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
