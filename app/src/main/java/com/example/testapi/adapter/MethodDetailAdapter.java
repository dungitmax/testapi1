package com.example.testapi.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testapi.MainActivity;
import com.example.testapi.R;
import com.example.testapi.dragdrop.ItemTouchListener;
import com.example.testapi.model.MethodDetail;
import com.example.testapi.model.Param;
import com.example.testapi.view.ParamView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MethodDetailAdapter extends RecyclerView.Adapter<MethodDetailAdapter.APIViewHolder> implements ItemTouchListener {

    private Context mContext;
    private List<MethodDetail> mMethodDetailList;
    private List<String> listSpinner;
    private List<String> mArrayForRecycle;
    private ArrayAdapter<String> spinnerAdapter;
    private MethodDetail detail;
    private String[] valuesArray;

    public MethodDetailAdapter(Context mContext, List<MethodDetail> modelList, List<String> arrayForRecycle) {
        this.mContext = mContext;
        this.mMethodDetailList = modelList;
        this.mArrayForRecycle = arrayForRecycle;

        detail = new MethodDetail();
        listSpinner = new ArrayList<>();
        for (int i = 0; i < mMethodDetailList.size(); i++) {
            MethodDetail detail = mMethodDetailList.get(i);
            List<Param> paramList = detail.getParams();
            for (Param p : paramList) {
                String paramName = p.getName();
                String methodName = detail.getMethodName() + " (" + paramName + ")";
                listSpinner.add(methodName);
            }
        }

    }

    @NonNull
    @Override
    public APIViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_recycle, null);
        spinnerAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_dropdown_item, listSpinner);
        return new APIViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final APIViewHolder holder, int position) {
        holder.mNameApi.setText("API " + (position + 1));
    }

    @Override
    public int getItemCount() {
        return mArrayForRecycle.size();
    }

    @Override
    public void onMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mArrayForRecycle, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mArrayForRecycle, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    public class APIViewHolder extends RecyclerView.ViewHolder {
        TextView mNameApi;
        Spinner mApiSpinner;
        LinearLayout mParamContainerLn;

        private APIViewHolder(@NonNull final View itemView) {
            super(itemView);
            mNameApi = itemView.findViewById(R.id.name_number_api);
            mApiSpinner = itemView.findViewById(R.id.api_spin);
            mParamContainerLn = itemView.findViewById(R.id.addParams);
            mApiSpinner.setAdapter(spinnerAdapter);
            mApiSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    // Xóa hết param view cũ nếu có trước khi add lại.
                    mParamContainerLn.removeAllViews();
                    detail = mMethodDetailList.get(i);
                    List<Param> paramList = detail.getParams();
                    for (Param p : paramList) {
                        ParamView pv = new ParamView(mContext);
                        String paramName = p.getName();
                        pv.setParamName(paramName);
                        if (p.getValue() != null) {
                            String paramVal = p.getValue().toString();
                            pv.setParamVal(paramVal);
                        }
                        mParamContainerLn.addView(pv);
                        String nameInput = pv.getParamNameTv().getText().toString();
                        if (nameInput.equals("int")) {
                            pv.getParamValEdt().setInputType(InputType.TYPE_CLASS_NUMBER);
                        } else if (nameInput.contains("[]")) {

                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

    }

    public String[] getArrays() {
        return valuesArray;
    }
}
