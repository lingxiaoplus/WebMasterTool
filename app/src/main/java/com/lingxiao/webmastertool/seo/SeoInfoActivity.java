package com.lingxiao.webmastertool.seo;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.lingxiao.webmastertool.BaseActivity;
import com.lingxiao.webmastertool.R;
import com.lingxiao.webmastertool.globle.ContentValue;
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
    @BindView(R.id.seo_swip)
    SwipeRefreshLayout seoSwip;
    @BindView(R.id.seo_age)
    TextView seoAge;
    @BindView(R.id.seo_server)
    TextView seoServer;

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

        seoSwip.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        seoSwip.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getInfo();
            }
        });
    }

    private void getInfo() {
        final String url = ContentValue.SEOINFO +
                SpUtils.getString(this,
                        ContentValue.DOMAIN, "");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document document = Jsoup.connect(url).get();
                    final Elements elements = document.
                            select("div.tcontent-seolist").select("p");

                    //final String s = elements.select("p").text();
                    UIUtils.runOnUIThread(new Runnable() {
                        @Override
                        public void run() {
                            seoTitle.setText(elements.get(0).text());
                            seoAge.setText(elements.get(1).text());
                            seoKeywords.setText(elements.get(3).text());
                            seoDesc.setText(elements.get(4).text());
                            seoServer.setText(elements.get(5).text());
                            if (seoSwip.isRefreshing()) {
                                seoSwip.setRefreshing(false);
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
