package com.zeelearn.ekidzee.mlzs.fragments.bs;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.zeelearn.ekidzee.mlzs.BaseFragment;
import com.zeelearn.ekidzee.mlzs.BuildConfig;
import com.zeelearn.ekidzee.mlzs.R;
import com.zeelearn.news.retrofit.ZeeNewsSdk;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class NewsFragment extends BaseFragment {


    WebView mWebView;
    public NewsFragment() {
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
        mWebView = (WebView) view.findViewById(R.id.webview);

        if(BuildConfig.FLAVOR.equalsIgnoreCase("zica")){
            mWebView.loadUrl("file:///android_asset/news.html");
            mWebView.setWebViewClient(new WebViewClient() {

                public void onPageFinished(WebView view, String url) {
                    hideDialog();
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    view.loadUrl(request.getUrl().toString());

                    if (request.getUrl().toString().startsWith("http") || request.getUrl().toString().startsWith("https")) {
                        return true;
                    } else if(request.getUrl().toString().contains("mailto")){
                        Intent email = new Intent(Intent.ACTION_SEND);
                        email.putExtra(Intent.EXTRA_EMAIL, new String[]{ request.getUrl().toString().replaceAll("mailto:","")});
                        email.putExtra(Intent.EXTRA_SUBJECT, "");
                        email.putExtra(Intent.EXTRA_TEXT, "");

                        //need this to prompts email client only
                        email.setType("message/rfc822");

                        startActivity(Intent.createChooser(email, "Choose an Email client :"));
                        mWebView.stopLoading();
                        mWebView.goBack();
                    }else {
                        mWebView.stopLoading();
                        mWebView.goBack();
                        Toast.makeText(getActivity(), "Unknown Link, unable to handle ", Toast.LENGTH_SHORT).show();
                    }
                    return false;
                }
            });
        }else {
            mWebView.loadUrl(getString(R.string.news));
            mWebView.setWebViewClient(new WebViewClient() {

                public void onPageFinished(WebView view, String url) {
                    hideDialog();
                    mWebView.scrollTo(0,500);
                }
            });
        }
        // this will enable the javascipt.
        mWebView.getSettings().setJavaScriptEnabled(true);
        showProgressDialog("");
        // WebViewClient allows you to handle
        // onPageFinished and override Url loading.
        //mWebView.setWebViewClient(new WebViewClient());


        return view;
    }


}
