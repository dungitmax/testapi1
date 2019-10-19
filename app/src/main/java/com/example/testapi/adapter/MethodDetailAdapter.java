package com.example.testapi.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.testapi.R;
import com.example.testapi.model.MethodDetail;
import com.example.testapi.model.Param;
import com.example.testapi.view.ParamView;

import java.util.ArrayList;
import java.util.List;

public class MethodDetailAdapter extends RecyclerView.Adapter<MethodDetailAdapter.APIViewHolder> {

    private Context mContext;
    //All methods in the class.
    private List<MethodDetail> mAllMethodDetailList;
    //Methods added on the test screen.
    private List<MethodDetail> mMethodDetailList;
    //Method name list on the Spinner.
    private List<String> mAllMethodNameList;
    //Position of text --Select method-- in the Spinner.
    private int mItemSelectPos;
    private ArrayAdapter<String> mAllMethodSpinnerAdapter;

    public MethodDetailAdapter(Context mContext, List<MethodDetail> mAllMethodDetailList, List<MethodDetail> mMethodDetailList) {
        this.mContext = mContext;
        this.mAllMethodDetailList = mAllMethodDetailList;
        this.mMethodDetailList = mMethodDetailList;
        mAllMethodNameList = detailToStringList(mAllMethodDetailList);
        mItemSelectPos = mAllMethodNameList.size() - 1;
        mAllMethodSpinnerAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_dropdown_item, mAllMethodNameList);
    }

    private List<String> detailToStringList(List<MethodDetail> allMethodDetailList) {
        List<String> allMethodNameList = new ArrayList<>();
        MethodDetail detail;
        for (int i = 0; i < allMethodDetailList.size(); i++) {
            detail = allMethodDetailList.get(i);
            List<Param> paramList = detail.getParams();
            StringBuilder methodName = new StringBuilder();
            methodName.append(detail.getMethodName()).append("(");
            Param p;
            for (int j = 0; j < paramList.size(); j++) {
                p = paramList.get(j);
                methodName.append(p.getType().getSimpleName());
                if (j < paramList.size() - 1) {
                    methodName.append(", ");
                }
            }
            methodName.append(")");
            allMethodNameList.add(methodName.toString());
        }
        allMethodNameList.add(mContext.getResources().getString(R.string.select_method));
        return allMethodNameList;
    }

    @NonNull
    @Override
    public APIViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_recycle, null);
        return new APIViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final APIViewHolder holder, int position) {
        MethodDetail md = mMethodDetailList.get(position);
        if (md.getClazz() == null) {
            //Method not specified.
            holder.setSpinnerPos(mItemSelectPos);
        } else {
            holder.setSpinnerPos(mAllMethodDetailList.indexOf(md));
        }
    }

    @Override
    public int getItemCount() {
        return mMethodDetailList.size();
    }

    public class APIViewHolder extends RecyclerView.ViewHolder {
        Spinner mApiNameSpn;
        LinearLayout mParamContainerLn;

        private APIViewHolder(@NonNull final View itemView) {
            super(itemView);
            mApiNameSpn = itemView.findViewById(R.id.spn_api_name);
            mParamContainerLn = itemView.findViewById(R.id.ln_params);
            mApiNameSpn.setAdapter(mAllMethodSpinnerAdapter);
            mApiNameSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i == mItemSelectPos) {
                        //--Select-- is selected.
                        mParamContainerLn.removeAllViews();
                    } else {
                        setParams(mAllMethodDetailList.get(i));
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }

        void setSpinnerPos(int i) {
            mApiNameSpn.setSelection(i);
        }

        void setParams(MethodDetail detail) {
            mParamContainerLn.removeAllViews();
            List<Param> paramList = detail.getParams();
            ParamView pv;
            String paramName;
            for (Param p : paramList) {
                pv = new ParamView(mContext);
                paramName = p.getName();
                pv.setParamName(paramName + mContext.getResources().getString(R.string.colon));
                String paramVal;
                if (p.getValue() != null) {
                    paramVal = p.getValue().toString();
                    pv.setParamVal(paramVal);
                }
                mParamContainerLn.addView(pv);
            }
        }

    }

}
