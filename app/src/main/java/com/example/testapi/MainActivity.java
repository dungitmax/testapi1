package com.example.testapi;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.testapi.adapter.MethodDetailAdapter;
import com.example.testapi.model.MethodDetail;
import com.example.testapi.until.ReflectionHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mApiListRcl;
    private MethodDetailAdapter mAdapter;
    private Button mAddMethodBtn, mClearAllMethodBtn, mLoadMethodBtn, mExecuteBtn;
    private TextView mResultTv;
    private ScrollView mResultScr;
    private List<MethodDetail> mMethodDetailList = new ArrayList<>();
    private String mApiName = "com.example.testapi.TestApi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        registerViewEvents();
        //Load MethodDetail list from data, then...
        loadApiList(mMethodDetailList);
    }

    private void initViews() {
        mApiListRcl = findViewById(R.id.rcl_list_api);
        mApiListRcl.setLayoutManager(new LinearLayoutManager(this));
        mAddMethodBtn = findViewById(R.id.btn_add);
        mClearAllMethodBtn = findViewById(R.id.btn_clear);
        mLoadMethodBtn = findViewById(R.id.btn_load);
        mExecuteBtn = findViewById(R.id.btn_execute);
        mResultTv = findViewById(R.id.tv_result);
        mResultScr = findViewById(R.id.scr_result);
    }

    private void registerViewEvents() {
        mAddMethodBtn.setOnClickListener(this);
        mClearAllMethodBtn.setOnClickListener(this);
        mLoadMethodBtn.setOnClickListener(this);
        mExecuteBtn.setOnClickListener(this);
    }

    private void loadApiList(List<MethodDetail> methodDetailList) {
        mAdapter = new MethodDetailAdapter(this, ReflectionHelper.getInstance().getMethodDetailList(mApiName), methodDetailList);
        mApiListRcl.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                addMethod();
                break;
            case R.id.btn_clear:
                clearMethod();
                break;
            case R.id.btn_load:
                break;
            case R.id.btn_execute:
                executeMethod();
                break;
            default:
                break;
        }
    }

    private void addMethod() {
        mMethodDetailList.add(new MethodDetail());
        mAdapter.notifyItemInserted(mMethodDetailList.size() - 1);
    }

    private void clearMethod() {
        mMethodDetailList.clear();
        mAdapter.notifyDataSetChanged();
    }

    private void executeMethod() {
        Date currentDate = new Date(System.currentTimeMillis());
        mResultTv.append(">>" + currentDate.toString() + "\n");
        for (MethodDetail md : mMethodDetailList) {
            Object ret = ReflectionHelper.getInstance().run(md);
            mResultTv.append(md.toString(true) + " = " + ret.toString() + "\n");
        }
        mResultTv.append("\n");
        Handler h = new Handler();
        //Must delay to scroll down the result log correctly.
        h.postDelayed(() -> {
            mResultScr.fullScroll(View.FOCUS_DOWN);
        }, 10L);
    }
}
