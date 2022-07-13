package com.zeelearn.ekidzee.mlzs.fragments.bs;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.zeelearn.ekidzee.mlzs.R;
import com.zeelearn.ekidzee.mlzs.iface.OnDialogClickListener;
import com.zeelearn.ekidzee.mlzs.utils.LocalConstance;


public class AboutUsFragment extends BottomSheetDialogFragment {
    String message,title;

    OnDialogClickListener mListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            message = getArguments().getString(LocalConstance.CONST_MESSAGE);
            title = getArguments().getString(LocalConstance.CONST_TITLE);
            mListener = (OnDialogClickListener) getArguments().getSerializable(LocalConstance.CONST_LISTENER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.bs_aboutus, container, false);


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context!=null && context instanceof OnDialogClickListener){
            mListener = (OnDialogClickListener) context;
        }
    }
}