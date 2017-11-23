package com.lingxiao.webmastertool.seo;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
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
    @BindView(R.id.seo_image)
    ImageView seoImage;
    @BindView(R.id.seo_link)
    TextView seoLink;
    @BindView(R.id.seo_out)
    TextView seoOut;
    @BindView(R.id.seo_in)
    TextView seoIn;

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
                    Elements elements = document.
                            select("div.tcontent-seolist");
                    //基本信息
                    final Elements baseInfos = elements.select("p");

                    //百度权重地址
                    final String url = elements
                            .select("img").attr("src");

                    //各种链接
                    final Elements fonts = elements.select("b.fontcolor02").select("font");
                    UIUtils.runOnUIThread(new Runnable() {
                        @Override
                        public void run() {
                            seoTitle.setText(baseInfos.get(0).text());
                            seoAge.setText(baseInfos.get(1).text());
                            seoKeywords.setText(baseInfos.get(3).text());
                            seoDesc.setText(baseInfos.get(4).text());
                            seoServer.setText(baseInfos.get(5).text());

                            seoLink.setText("反链数："+fonts.get(0).text());
                            seoOut.setText("出站链接："+fonts.get(1).text());
                            seoIn.setText("入站链接："+fonts.get(1).text());

                            if (seoSwip.isRefreshing()) {
                                seoSwip.setRefreshing(false);
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "错误:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }).start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
