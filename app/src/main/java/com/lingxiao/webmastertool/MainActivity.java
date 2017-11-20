package com.lingxiao.webmastertool;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.github.mzule.fantasyslide.FantasyDrawerLayout;
import com.github.mzule.fantasyslide.FantasyListener;
import com.github.mzule.fantasyslide.SideBar;
import com.lingxiao.webmastertool.seo.SeoInfoActivity;
import com.lingxiao.webmastertool.utils.SnackUtils;
import com.lingxiao.webmastertool.widget.CardLayout;
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
    }

    @OnClick({R.id.card_seo,R.id.card_ranking,
            R.id.card_whois,R.id.card_icp,
            R.id.card_ip,R.id.card_pr,
            R.id.card_blogroll,R.id.card_link})
    public void onCardClick(View view){
        switch (view.getId()){
            case R.id.card_seo:
                StartActivity(SeoInfoActivity.class,false);
                break;
            case R.id.card_ranking:
                SnackUtils.show("ranking",view);
                break;
            case R.id.card_whois:
                SnackUtils.show("whois",view);
                break;
            case R.id.card_icp:
                break;
            case R.id.card_ip:
                SnackUtils.show("ip",view);
                break;
            case R.id.card_pr:
                break;
            case R.id.card_blogroll:
                break;
            case R.id.card_link:
                break;
            default:
                break;
        }
    }

}
