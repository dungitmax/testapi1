package com.example.testapi.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
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

public class MethodDetailAdapter extends RecyclerView.Adapter<MethodDetailAdapter.MethodViewHolder> {

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
        for (MethodDetail md : mAllMethodDetailList) {
            allMethodNameList.add(md.toString(false));
        }
        allMethodNameList.add(mContext.getResources().getString(R.string.select_method));
        return allMethodNameList;
    }

    @NonNull
    @Override
    public MethodViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_recycle, null);
        return new MethodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MethodViewHolder holder, int position) {
        MethodDetail md = mMethodDetailList.get(position);
        holder.mPosition = position;
        if (md.isNotSpecified()) {
            holder.setSpinnerPos(mItemSelectPos);
        } else {
            holder.setSpinnerPos(mAllMethodDetailList.indexOf(md));
        }
    }

    @Override
    public int getItemCount() {
        return mMethodDetailList.size();
    }

    public class MethodViewHolder extends RecyclerView.ViewHolder {
        Spinner mMethodNameSpn;
        LinearLayout mParamContainerLn;
        int mPosition;

        private MethodViewHolder(@NonNull final View itemView) {
            super(itemView);
            mMethodNameSpn = itemView.findViewById(R.id.spn_method_name);
            mParamContainerLn = itemView.findViewById(R.id.ln_params);
            mMethodNameSpn.setAdapter(mAllMethodSpinnerAdapter);
            mMethodNameSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i == mItemSelectPos) {
                        //--Select-- is selected.
                        mParamContainerLn.removeAllViews();
                        mMethodDetailList.get(mPosition).unSpecify();
                    } else {
                        mMethodDetailList.set(mPosition, mAllMethodDetailList.get(i));
                        setParams(mMethodDetailList.get(mPosition));
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }

        void setSpinnerPos(int i) {
            mMethodNameSpn.setSelection(i);
        }

        void setParams(MethodDetail detail) {
            mParamContainerLn.removeAllViews();
            List<Param> paramList = detail.getParams();
            ParamView pv;
            String paramName;
            for (final Param p : paramList) {
                pv = new ParamView(mContext);
                paramName = p.getName();
                pv.setParamName(paramName + mContext.getResources().getString(R.string.colon));
                String paramVal;
                if (p.getValue() != null) {
                    paramVal = p.getValue().toString();
                    pv.setParamVal(paramVal);
                }
                mParamContainerLn.addView(pv);
                pv.getParamValEdt().addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        p.setValue(s.toString());
                    }
                });
            }
        }

    }

}
