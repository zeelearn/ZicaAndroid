package com.zeelearn.ekidzee.mlzs.fragments.bs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.zeelearn.ekidzee.mlzs.BaseFragment;
import com.zeelearn.ekidzee.mlzs.R;
import com.zeelearn.ekidzee.mlzs.utils.ZeePref;

public class ProfileFragment extends BaseFragment {

    public ProfileFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.user_profile, container, false);
        ((TextView) view.findViewById(R.id.username)).setText(ZeePref.getDisplayName(getContext()));
        ((TextView) view.findViewById(R.id.usercode)).setText(ZeePref.getUserTypeName(getContext()));
        ((TextView) view.findViewById(R.id.mail)).setText(ZeePref.getEmailId(getContext()));
        ((TextView) view.findViewById(R.id.mobile)).setText(ZeePref.getMobileNo(getContext()));

        return view;
    }
}
