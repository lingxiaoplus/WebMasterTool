package com.lingxiao.webmastertool.web;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.lingxiao.webmastertool.BaseActivity;
import com.lingxiao.webmastertool.R;
import com.lingxiao.webmastertool.globle.ContentValue;
import com.lingxiao.webmastertool.utils.SpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebActivity extends BaseActivity {

    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;
    @BindView(R.id.pb_web)
    ProgressBar pbWeb;
    @BindView(R.id.webview)
    MyWebView webview;
    @BindView(R.id.web_swip)
    SwipeRefreshLayout webSwip;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        initWebView();
        initView();
        webview.loadUrl(url);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                pbWeb.setVisibility(View.INVISIBLE);
            }
        });
        webview.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && webview.canGoBack()) {
                        //表示按返回键时的操作
                        webview.goBack();   //后退
                        //webview.goForward();//前进
                        return true;    //已处理
                    }
                }
                return false;
            }
        });
    }

    private void initView() throws NullPointerException{
        String title = getIntent().getStringExtra("title");
        toolbarTitle.setTitle(title);
        setSupportActionBar(toolbarTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        url = intent.getStringExtra("url")+
                SpUtils.getString(this, ContentValue.DOMAIN,"");

        webSwip.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pbWeb.setVisibility(View.VISIBLE);
                webview.loadUrl(url);
            }
        });
    }

    private void initWebView() {
        //声明WebSettings子类
        WebSettings webSettings = webview.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        // 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
        // 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可
        //支持插件
        //webSettings.setPluginsEnabled(true);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

        webview.setOnScrollChangedCallback(new MyWebView.OnScrollChangedCallback() {
            @Override
            public void onScroll(int l, int t) {
                if (t == 0) {//webView在顶部
                    webSwip.setEnabled(true);
                } else {//webView不是顶部
                    webSwip.setEnabled(false);
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
