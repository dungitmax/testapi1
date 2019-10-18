package com.example.testapi.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.testapi.R;

public class ParamView extends LinearLayout {

    private TextView mParamNameTv;
    private EditText mParamValEdt;

    public ParamView(Context context) {
        super(context);
        init(context);
    }

    public ParamView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ParamView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.param_view, this, true);
        mParamNameTv = findViewById(R.id.paramNameTv);
        mParamValEdt = findViewById(R.id.paramValEdt);
    }

    public void setParamName(String paramName) {
        mParamNameTv.setText(paramName);
    }

    public void setParamVal(String paramVal) {
        mParamValEdt.setText(paramVal);
    }

    public String getParamVal() {
        return mParamValEdt.getText().toString();
    }

    public EditText getParamValEdt() {
        return mParamValEdt;
    }

    public TextView getParamNameTv() {
        return mParamNameTv;
    }
}
