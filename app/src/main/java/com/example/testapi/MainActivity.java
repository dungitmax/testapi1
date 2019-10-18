package com.example.testapi;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.testapi.model.MethodDetail;
import com.example.testapi.model.Param;
import com.example.testapi.adapter.MethodDetailAdapter;
import com.example.testapi.dragdrop.ItemTouchListener;
import com.example.testapi.dragdrop.SimpleItemTouchHelperCallback;
import com.example.testapi.until.ReflectionHelper;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private MethodDetailAdapter adapter;
    private Button mAddMethod, mClearAllMethod, mLoadMethod;
    private ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        handleRecycle();
        handleAllButton();
    }

    /**
     * Handle all button.
     */
    private void handleAllButton() {
        mAddMethod.setOnClickListener(this);
        mClearAllMethod.setOnClickListener(this);
        mLoadMethod.setOnClickListener(this);
    }

    private void handleRecycle() {
        list.add("");
        adapter = new MethodDetailAdapter(this, getMethodDetailList(), list);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(adapter);
        //

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(new ItemTouchListener() {
            @Override
            public void onMove(int oldPosition, int newPosition) {
                adapter.onMove(oldPosition, newPosition);
            }

        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    private void init() {
        mRecyclerView = findViewById(R.id.list_api);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAddMethod = findViewById(R.id.add_button);
        mClearAllMethod = findViewById(R.id.clear_button);
        mLoadMethod = findViewById(R.id.load_button);
    }

    ItemTouchHelper.SimpleCallback simpleCallback =
            new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                    list.remove(viewHolder.getAdapterPosition());
                    adapter.notifyDataSetChanged();
                }
            };

    // Load các method từ class được chỉ định, chuyển thông tin method vào các đối tượng MethodDetail.
    private List<MethodDetail> getMethodDetailList() {
        List<MethodDetail> methodDetailList = new ArrayList<>();

        Method[] methods = ReflectionHelper.getInstance().getMethodList("java.lang.String");
        for (Method m : methods) {
            MethodDetail methodDetail = new MethodDetail();
            methodDetail.setMethodName(m.getName());
            Class[] params = m.getParameterTypes();
            List<Param> paramList = new ArrayList<>();
            for (Class c : params) {
                Param p = new Param(c.getSimpleName(), c, null);
                paramList.add(p);
            }
            methodDetail.setParams(paramList);
            methodDetailList.add(methodDetail);
        }

        return methodDetailList;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_button:
                try {
                    list.add("");
                    adapter.notifyItemInserted(list.size() - 1);
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "The field is empty",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.clear_button:
                int size = list.size();
                if (size > 0) {
                    for (int i = 0; i < size; i++) {
                        list.remove(0);
                    }
                    adapter.notifyItemRangeRemoved(0, size);
                }
                break;
            case R.id.load_button:
                break;
            default:
                break;
        }
    }
}
