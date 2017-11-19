package com.lingxiao.webmastertool.seo;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.lingxiao.webmastertool.BaseActivity;
import com.lingxiao.webmastertool.R;
import com.lingxiao.webmastertool.globle.ContentValue;
import com.lingxiao.webmastertool.utils.SnackUtils;
import com.lingxiao.webmastertool.utils.SpUtils;
import com.lingxiao.webmastertool.utils.UIUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SeoInfoActivity extends BaseActivity {

    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;
    @BindView(R.id.seo_title)
    TextView seoTitle;
    @BindView(R.id.seo_keywords)
    TextView seoKeywords;
    @BindView(R.id.seo_desc)
    TextView seoDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seo_info);
        ButterKnife.bind(this);
        initView();
        getInfo();
    }

    private void initView() {
        toolbarTitle.setTitle("seo查询");
        setSupportActionBar(toolbarTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void getInfo(){
        final String url = ContentValue.SEOINFO +
                SpUtils.getString(this,
                        ContentValue.DOMAIN,"");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document document = Jsoup.connect(url).get();
                    Elements elements = document.
                            select("div.tcontent-seolist");

                    final String s = elements.select("p").text();
                    UIUtils.runOnUIThread(new Runnable() {
                        @Override
                        public void run() {
                            seoTitle.setText(s);
                            Toast.makeText(getApplicationContext()
                            ,s,Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
