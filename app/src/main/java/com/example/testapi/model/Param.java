package com.example.testapi.model;

public class Param {
    private String mName;
    // Kiểu param, vd mType = String.class.
    private Class mType;
    // Giá trị param có thể là bất kì kiểu dữ liệu nào, nên để Object cho nó chung chung.
    private Object mValue;

    public Param(final String name, final Class type, final Object value) {
        this.mName = name;
        this.mType = type;
        this.mValue = value;
    }

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
