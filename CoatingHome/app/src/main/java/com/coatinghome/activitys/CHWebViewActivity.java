package com.coatinghome.activitys;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.coatinghome.R;
import com.coatinghome.providers.CHContrat;

import roboguice.inject.InjectView;

/**
 * Created by wuyajun on 15/6/23.
 * <p>
 * 公共Web加载界面
 * <p>
 * mCHWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
 * //LOAD_CACHE_ONLY:  不使用网络，只读取本地缓存数据
 * //LOAD_DEFAULT:  根据cache-control决定是否从网络上取数据。
 * //LOAD_CACHE_NORMAL: API level 17中已经废弃, 从API level 11开始作用同LOAD_DEFAULT模式
 * //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
 * //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
 */
public class CHWebViewActivity extends CHBaseActivity {

    @InjectView(R.id.title_back)
    private ImageView mTitleBack;
    @InjectView(R.id.public_center_title)
    private TextView mCenterTitle;
    @InjectView(R.id.title_progress)
    private ProgressBar mTitleProgress;

    private String title = "";
    private String webUrl = "";

    private WebView mWebView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        title = getIntent().getStringExtra(CHContrat.ACTIVITY_TITLE_TEXT);
        webUrl = getIntent().getStringExtra(CHContrat.WEB_URL_PATH);

        initViews();
    }

    public void initViews() {
        CHContrat.showView(mTitleBack, mCenterTitle);
        mTitleBack.setOnClickListener(this);
        mCenterTitle.setText(title);

        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setJavaScriptEnabled(true);

        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100) {
                    if (mTitleProgress.getVisibility() != View.VISIBLE)
                        CHContrat.showView(mTitleProgress);
                } else {
                    if (mTitleProgress.getVisibility() != View.GONE)
                        CHContrat.hideView(mTitleProgress);
                }
            }
        });

        // 设置 webview 文件下载监听 （webview 默认没有开启文件下载功能）
        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                if (url != null) {
                    if (url.startsWith("http://")) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    }
                }
            }
        });

        mWebView.setWebViewClient(new WebViewClient() {
            /*此处能拦截超链接的url,即拦截href请求的内容.*/
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                    startActivity(intent);
                } else {
                    view.loadUrl(url);
                }
                return true;
            }
        });

        mWebView.loadUrl(webUrl);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.clearHistory();
        mWebView.destroy();
        mWebView = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finishActivity();
                break;
        }
    }
}