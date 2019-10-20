package com.example.testapi.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.testapi.R;
import com.example.testapi.adapter.MethodDetailAdapter;
import com.example.testapi.model.MethodDetail;
import com.example.testapi.until.ReflectionHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.testapi.Const.EXTRA_CLASSNAME;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mMethodListListRcl;
    private MethodDetailAdapter mAdapter;
    private Button mAddMethodBtn, mClearAllMethodBtn, mLoadMethodBtn, mExecuteMethodBtn, mSaveResultBtn, mClearResultBtn;
    private TextView mResultTv;
    private ScrollView mResultScr;
    private List<MethodDetail> mMethodDetailList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initViews();
        registerViewEvents();
        //Load MethodDetail list from data, then...
        mMethodDetailList.add(new MethodDetail());
        loadApiList(mMethodDetailList);
    }

    private void initViews() {
        mMethodListListRcl = findViewById(R.id.rcl_list_method);
        mMethodListListRcl.setLayoutManager(new LinearLayoutManager(this));
        mAddMethodBtn = findViewById(R.id.btn_add_method);
        mClearAllMethodBtn = findViewById(R.id.btn_clear_method);
        mLoadMethodBtn = findViewById(R.id.btn_load_method);
        mExecuteMethodBtn = findViewById(R.id.btn_execute_method);
        mResultTv = findViewById(R.id.tv_result);
        mResultScr = findViewById(R.id.scr_result);
        mSaveResultBtn = findViewById(R.id.btn_save_result);
        mClearResultBtn = findViewById(R.id.btn_clear_result);
    }

    private void registerViewEvents() {
        mAddMethodBtn.setOnClickListener(this);
        mClearAllMethodBtn.setOnClickListener(this);
        mLoadMethodBtn.setOnClickListener(this);
        mExecuteMethodBtn.setOnClickListener(this);
        mSaveResultBtn.setOnClickListener(this);
        mClearResultBtn.setOnClickListener(this);
    }

    private void loadApiList(List<MethodDetail> methodDetailList) {
        String className = getIntent().getStringExtra(EXTRA_CLASSNAME);
        mAdapter = new MethodDetailAdapter(this, ReflectionHelper.getInstance().getMethodDetailList(className), methodDetailList);
        mMethodListListRcl.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_method:
                addMethod();
                break;
            case R.id.btn_clear_method:
                clearMethod();
                break;
            case R.id.btn_load_method:
                break;
            case R.id.btn_execute_method:
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
