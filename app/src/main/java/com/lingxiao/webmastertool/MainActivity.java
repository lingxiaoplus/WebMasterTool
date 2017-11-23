package com.lingxiao.webmastertool;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mzule.fantasyslide.FantasyDrawerLayout;
import com.github.mzule.fantasyslide.FantasyListener;
import com.github.mzule.fantasyslide.SideBar;
import com.lingxiao.webmastertool.globle.ContentValue;
import com.lingxiao.webmastertool.seo.SeoInfoActivity;
import com.lingxiao.webmastertool.utils.SnackUtils;
import com.lingxiao.webmastertool.utils.SpUtils;
import com.lingxiao.webmastertool.utils.UIUtils;
import com.lingxiao.webmastertool.web.WebActivity;
import com.lingxiao.webmastertool.widget.SpaceItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;
    @BindView(R.id.left_sidebar)
    SideBar leftSidebar;
    @BindView(R.id.menu_layout)
    FantasyDrawerLayout menuLayout;
    @BindView(R.id.rv_main)
    RecyclerView rvMain;
    @BindView(R.id.ll_redomain)
    LinearLayout llRedomain;
    @BindView(R.id.tv_domain)
    TextView tvDomain;

    private GridLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        toolbarTitle.setTitle("站长工具");
        setSupportActionBar(toolbarTitle);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //创建返回键，并实现打开关/闭监听
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this, menuLayout, toolbarTitle,
                R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        leftSidebar.setFantasyListener(new FantasyListener() {
            @Override
            public boolean onHover(@Nullable View view) {
                return false;
            }

            @Override
            public boolean onSelect(View view) {
                return false;
            }

            @Override
            public void onCancel() {

            }
        });
        mDrawerToggle.syncState();
        menuLayout.setDrawerListener(mDrawerToggle);
        mLayoutManager = new GridLayoutManager(this, 3);
        rvMain.setLayoutManager(mLayoutManager);
        //添加间距
        rvMain.addItemDecoration(new SpaceItemDecoration(10));
        MainRecycleAdapter mAdapter = new MainRecycleAdapter();
        rvMain.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new MainRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View View, int position) {
                Toast.makeText(getApplicationContext(),
                        "" + position, Toast.LENGTH_LONG)
                        .show();
            }
        });

        tvDomain.setText(SpUtils.getString(this,ContentValue.DOMAIN,""));
        llRedomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog();
            }
        });
    }

    @OnClick({R.id.card_seo, R.id.card_ranking,
            R.id.card_whois, R.id.card_icp,
            R.id.card_ip, R.id.card_pr,
            R.id.card_blogroll, R.id.card_link})
    public void onCardClick(View view) {
        Intent intent = new Intent(getApplicationContext(), WebActivity.class);
        switch (view.getId()) {
            case R.id.card_seo:
                StartActivity(SeoInfoActivity.class, false);
                break;
            case R.id.card_ranking:
                intent.putExtra("url", ContentValue.RANKING);
                intent.putExtra("title","百度排名");
                startActivity(intent);
                break;
            case R.id.card_whois:
                intent.putExtra("title","whois查询");
                SnackUtils.show("whois", view);
                break;
            case R.id.card_icp:
                intent.putExtra("url", ContentValue.ICPINFO);
                intent.putExtra("title","网站备案");
                startActivity(intent);
                break;
            case R.id.card_ip:
                intent.putExtra("url", ContentValue.IPINFO);
                intent.putExtra("title","IP查询");
                startActivity(intent);
                break;
            case R.id.card_pr:
                intent.putExtra("url", ContentValue.PRINFO);
                intent.putExtra("title","PR查询");
                startActivity(intent);
                break;
            case R.id.card_blogroll:
                intent.putExtra("url", ContentValue.BLOGINFO);
                intent.putExtra("title","友情链接");
                startActivity(intent);
                break;
            case R.id.card_link:
                intent.putExtra("url", ContentValue.LINKINFO);
                intent.putExtra("title","反链查询");
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @OnClick({R.id.tv_info_find,R.id.tv_domain_find,R.id.tv_domain_other})
    public void onSlideClick(View view){
        String[] items;
        switch (view.getId()){
            case R.id.tv_info_find:
                items = new String[]{"死链检测", "关键词密度", "META信息","PR输出值"};
                showDialog(items);
                break;
            case R.id.tv_domain_find:
                items = new String[]{"域名删除", "同IP网站", "DNS查询","NsLookup","子域名查询"};
                showDialog(items);
                break;
            case R.id.tv_domain_other:
                items = new String[]{"超级ping", "路由器追踪", "HTTP状态","端口扫描"};
                showDialog(items);
                break;
            default:
                break;
        }
    }
    private void showInputDialog() {
    /*@setView 装入一个EditView
     */
        final EditText editText = new EditText(MainActivity.this);
        editText.setHint("请输入域名");
        editText.setText(SpUtils.getString(this,ContentValue.DOMAIN,""));
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(MainActivity.this);
        inputDialog.setTitle("请输入域名").setView(editText);
        inputDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this,
                                "修改域名："+editText.getText().toString(),
                                Toast.LENGTH_SHORT).show();
                        SpUtils.putString(UIUtils.getContext(),ContentValue.DOMAIN
                        ,editText.getText().toString());
                    }
                }).show();
        inputDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
    }
    private void showDialog(String[] items){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请选择工具");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }
}
