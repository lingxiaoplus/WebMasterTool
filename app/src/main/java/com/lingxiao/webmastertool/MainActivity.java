package com.lingxiao.webmastertool;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.mzule.fantasyslide.FantasyDrawerLayout;
import com.github.mzule.fantasyslide.FantasyListener;
import com.github.mzule.fantasyslide.SideBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;
    @BindView(R.id.et_domain)
    EditText etDomain;
    @BindView(R.id.input_domain)
    TextInputLayout inputDomain;
    @BindView(R.id.bt_commit)
    Button btCommit;
    @BindView(R.id.left_sidebar)
    SideBar leftSidebar;
    @BindView(R.id.menu_layout)
    FantasyDrawerLayout menuLayout;
    @BindView(R.id.rv_main)
    RecyclerView rvMain;
    private String domainName;
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
        mLayoutManager = new GridLayoutManager(this,3);
        rvMain.setLayoutManager(mLayoutManager);
        //添加间距
        rvMain.addItemDecoration(new SpaceItemDecoration(10));
        MainRecycleAdapter mAdapter = new MainRecycleAdapter();
        rvMain.setAdapter(mAdapter);
    }

    @OnClick(R.id.bt_commit)
    public void onCommit() {
        domainName = etDomain.getText().toString().trim();
        if (domainName.isEmpty()) {
            inputDomain.setErrorEnabled(true);
            inputDomain.setError("域名格式不正确!");
        } else {
            inputDomain.setErrorEnabled(false);
            inputDomain.setError("域名保存成功!");
        }
    }
}
