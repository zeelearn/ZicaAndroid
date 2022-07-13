package com.zeelearn.ekidzee.mlzs.fragments.bs;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.zeelearn.ekidzee.mlzs.BaseFragment;
import com.zeelearn.ekidzee.mlzs.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class EStudioFragment extends BaseFragment {


    WebView mWebView;
    public EStudioFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_estudio, container, false);
        TextView txtMessage = (TextView) view.findViewById(R.id.message);
        txtMessage.setMovementMethod(LinkMovementMethod.getInstance());
        /*mWebView = (WebView) view.findViewById(R.id.webview);
        mWebView.loadUrl(getString(R.string.homeurl));
        // this will enable the javascipt.
        mWebView.getSettings().setJavaScriptEnabled(true);
        showProgressDialog("");
        // WebViewClient allows you to handle
        // onPageFinished and override Url loading.
        mWebView.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                hideDialog();
                mWebView.scrollTo(0,500);
            }
        });*/
        return view;
    }


}
