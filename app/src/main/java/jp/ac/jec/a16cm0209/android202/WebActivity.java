package jp.ac.jec.a16cm0209.android202;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebActivity extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        webView = (WebView)findViewById(R.id.webView);
        Intent intent = getIntent();
        String linkURL = intent.getStringExtra("link");
        webView.loadUrl(linkURL);
        webView.setWebViewClient(new WebViewClient());

    }
}
