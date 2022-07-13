package com.zeelearn.ekidzee.mlzs.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.zeelearn.ekidzee.mlzs.R


class PrivacyPolicyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)
        val webView = findViewById<WebView>(R.id.webview);
        webView.webViewClient = WebViewClient()

        // this will load the url of the website
        webView.loadUrl(resources.getString(R.string.url_privacy_policy))

        // this will enable the javascript settings
        webView.settings.javaScriptEnabled = true

        // if you want to enable zoom feature
        webView.settings.setSupportZoom(true)
    }
}