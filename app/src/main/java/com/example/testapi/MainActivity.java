package com.example.testapi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.testapi.adapter.MethodDetailAdapter;
import com.example.testapi.model.MethodDetail;
import com.example.testapi.until.ReflectionHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mApiListRcl;
    private MethodDetailAdapter mAdapter;
    private Button mAddMethodBtn, mClearAllMethodBtn, mLoadMethodBtn;
    private ArrayList<MethodDetail> mMethodDetailList = new ArrayList<>();
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

    private void registerViewEvents() {
        mAddMethodBtn.setOnClickListener(this);
        mClearAllMethodBtn.setOnClickListener(this);
        mLoadMethodBtn.setOnClickListener(this);
    }

    private void loadApiList(List<MethodDetail> methodDetailList) {
        mAdapter = new MethodDetailAdapter(this, ReflectionHelper.getInstance().getMethodDetailList(mApiName), mMethodDetailList);
        mApiListRcl.setAdapter(mAdapter);
    }

    private void initViews() {
        mApiListRcl = findViewById(R.id.rcl_list_api);
        mApiListRcl.setLayoutManager(new LinearLayoutManager(this));
        mAddMethodBtn = findViewById(R.id.btn_add);
        mClearAllMethodBtn = findViewById(R.id.btn_clear);
        mLoadMethodBtn = findViewById(R.id.btn_load);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                mMethodDetailList.add(new MethodDetail());
                mAdapter.notifyItemInserted(mMethodDetailList.size() - 1);
                break;
            case R.id.btn_clear:
                int size = mMethodDetailList.size();
                if (size > 0) {
                    for (int i = 0; i < size; i++) {
                        mMethodDetailList.remove(0);
                    }
                    mAdapter.notifyItemRangeRemoved(0, size);
                }
                break;
            case R.id.btn_load:
                break;
            default:
                break;
        }
    }
}
