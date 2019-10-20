package com.example.testapi.model;

import java.util.List;

/**
 * An object to hold necessary information of a method.
 */
public class MethodDetail {

    //Class contains this method.
    private Class mClass;
    //Return type of this method.
    private Class mReturnType;
    private String mMethodName;
    //Params of this method.
    private List<Param> mParams;

    public MethodDetail(final Class mClass, final Class mReturnType, final String mMethodName, final List<Param> mParams) {
        this.mClass = mClass;
        this.mReturnType = mReturnType;
        this.mMethodName = mMethodName;
        this.mParams = mParams;
    }

    public MethodDetail() {
    }

    public Class getClazz() {
        return mClass;
    }

    public Class getReturnType() {
        return mReturnType;
    }

    public void setReturnType(Class mReturnType) {
        this.mReturnType = mReturnType;
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

    public void unSpecify() {
        mClass = null;
        mMethodName = null;
        mReturnType = null;
        mParams = null;
    }

    public boolean isNotSpecified() {
        return mClass == null;
    }

    public String toString(boolean showParamVal) {
        if (isNotSpecified()) {
            return "Method not specified!";
        }
        List<Param> paramList = getParams();
        StringBuilder description = new StringBuilder();
        description.append(getReturnType().getSimpleName()).append(" ");
        description.append(getMethodName()).append("(");
        Param p;
        for (int i = 0; i < paramList.size(); i++) {
            p = paramList.get(i);
            if (showParamVal && p.getValue() != null) {
                description.append(p.getValue().toString());
            } else {
                description.append(p.getType().getSimpleName());
            }
            if (i < paramList.size() - 1) {
                description.append(", ");
            }
        }
        description.append(")");
        return description.toString();
    }

}
