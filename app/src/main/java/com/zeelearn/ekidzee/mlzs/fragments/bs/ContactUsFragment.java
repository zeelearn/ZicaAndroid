package com.zeelearn.ekidzee.mlzs.fragments.bs;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.zeelearn.ekidzee.mlzs.BaseFragment;
import com.zeelearn.ekidzee.mlzs.BuildConfig;
import com.zeelearn.ekidzee.mlzs.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class ContactUsFragment extends BaseFragment implements View.OnClickListener {


    WebView mWebView;
    public ContactUsFragment() {
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
        View view = null;
        if(BuildConfig.FLAVOR.equalsIgnoreCase("zica")){
            view = inflater.inflate(R.layout.contact_us, container, false);
            TextView txtMessage = (TextView) view.findViewById(R.id.message);
            txtMessage.setMovementMethod(LinkMovementMethod.getInstance());
            ImageView imgFacebook = view.findViewById(R.id.action_facebook);
            ImageView imgTweeter = view.findViewById(R.id.action_tweeter);
            ImageView imgYoutube = view.findViewById(R.id.action_youtube);
            imgFacebook.setOnClickListener(this);
            imgTweeter.setOnClickListener(this);
            imgYoutube.setOnClickListener(this);


        }else {
           /* view = inflater.inflate(R.layout.fragment_estudio, container, false);
            mWebView = (WebView) view.findViewById(R.id.webview);
            mWebView.loadUrl(getString(R.string.contactus));
            // this will enable the javascipt.
            mWebView.getSettings().setJavaScriptEnabled(true);
            showProgressDialog("");
            // WebViewClient allows you to handle
            // onPageFinished and override Url loading.
            //mWebView.setWebViewClient(new WebViewClient());
            mWebView.setWebViewClient(new WebViewClient() {

                public void onPageFinished(WebView view, String url) {
                    hideDialog();
                    mWebView.scrollTo(0, 500);
                }
            });*/
        }

        return view;
    }

    public void openUrl(String url){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.action_facebook:
                openUrl(getString(R.string.facebook_url));
                break;
            case R.id.action_tweeter:
                openUrl(getString(R.string.tweeter_url));
                break;
            case R.id.action_youtube:
                openUrl(getString(R.string.youtube_url));
                break;
        }
    }
}
