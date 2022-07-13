package com.zeelearn.ekidzee.mlzs.fragments.bs;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.zeelearn.ekidzee.mlzs.R;
import com.zeelearn.ekidzee.mlzs.iface.OnDialogClickListener;
import com.zeelearn.ekidzee.mlzs.model.GenericResponse;
import com.zeelearn.ekidzee.mlzs.utils.LocalConstance;

import static com.zeelearn.ekidzee.mlzs.utils.LocalConstance.IS_LOYOUT;


public class BSLogoutDialogFragment extends BottomSheetDialogFragment {
    String message, title;

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
        View view = inflater.inflate(R.layout.bs_logoutdialog, container, false);
        ((TextView) view.findViewById(R.id.dialog_title)).setText(title);
        ((TextView) view.findViewById(R.id.dialog_message)).setText(message);

        view.findViewById(R.id.action_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    GenericResponse response = new GenericResponse(IS_LOYOUT,"logout");
                    mListener.onOkClicked(response);
                }
                getDialog().dismiss();
            }
        });
        view.findViewById(R.id.action_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        return view;
    }


    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < 16) {
                    view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                BottomSheetDialog dialog = (BottomSheetDialog) getDialog();
                FrameLayout bottomSheet = dialog.findViewById(R.id.design_bottom_sheet);
                BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                //behavior.setPeekHeight(0); // Remove this line to hide a dark background if you manually hide the dialog.
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context != null && context instanceof OnDialogClickListener) {
            mListener = (OnDialogClickListener) context;
        }
    }
}