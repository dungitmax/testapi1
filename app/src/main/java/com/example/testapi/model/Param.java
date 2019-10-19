package com.example.testapi.model;

/**
 * An object to hold necessary information of a method's parameter.
 */
public class Param {
    private String mName;
    //Param type, example: String.class.
    private Class mType;
    private Object mValue;

    public Param(final String name, final Class type, final Object value) {
        this.mName = name;
        this.mType = type;
        this.mValue = value;
    }

    public Param() {}

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public Class getType() {
        return mType;
    }

    public void setType(Class type) {
        this.mType = type;
    }

    public Object getValue() {
        return mValue;
    }

    public void setValue(Object value) {
        this.mValue = value;
    }
}
