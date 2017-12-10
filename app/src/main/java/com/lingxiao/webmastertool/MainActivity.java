package com.lingxiao.webmastertool;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.lingxiao.webmastertool.globle.ContentValue;
import com.lingxiao.webmastertool.seo.SeoInfoActivity;
import com.lingxiao.webmastertool.utils.SpUtils;
import com.lingxiao.webmastertool.utils.UIUtils;
import com.lingxiao.webmastertool.web.WebActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity{

    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;
    @BindView(R.id.menu_layout)
    DrawerLayout menuLayout;
    @BindView(R.id.left_navigat)
    NavigationView leftNavigat;

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
        mDrawerToggle.syncState();
        menuLayout.setDrawerListener(mDrawerToggle);

        leftNavigat.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                String[] items;
                switch (item.getItemId()) {
                    case R.id.tv_info_find:
                        items = new String[]{"死链检测", "关键词密度", "META信息", "PR输出值"};
                        showDomainInfoDialog(items);
                        break;
                    case R.id.tv_domain_find:
                        items = new String[]{"域名删除", "同IP网站", "子域名查询"};
                        showIPDialog(items);
                        break;
                    case R.id.tv_domain_other:
                        items = new String[]{"超级ping", "路由器追踪", "HTTP状态", "端口扫描"};
                        showOtherDialog(items);
                        break;
                    default:
                        break;
                }
                menuLayout.closeDrawers();
                return true;
            }
        });
        /*TextView tvDomain = leftNavigat.findViewById(R.id.tv_menu_domain);
        CircleImageView circleImageView = leftNavigat.findViewById(R.id.circleImg);
        tvDomain.setText(SpUtils.getString(this, ContentValue.DOMAIN, ""));
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog();
            }
        });*/
    }

    @OnClick({R.id.card_seo, R.id.card_ranking,
            R.id.card_whois, R.id.card_icp,
            R.id.card_ip, R.id.card_pr,
            R.id.card_blogroll, R.id.card_link})
    public void onCardClick(View view) {
        Intent intent = new Intent(getApplicationContext(), WebActivity.class);
        switch (view.getId()) {
            case R.id.card_seo:
                intent.putExtra("url", ContentValue.SEOINFO);
                intent.putExtra("title", "seo查询");
                startActivity(intent);
                //StartActivity(SeoInfoActivity.class, false);
                break;
            case R.id.card_ranking:
                intent.putExtra("url", ContentValue.RANKING);
                intent.putExtra("title", "百度排名");
                startActivity(intent);
                break;
            case R.id.card_whois:
                intent.putExtra("url", ContentValue.WHOISINFO);
                intent.putExtra("title", "whois查询");
                startActivity(intent);
                break;
            case R.id.card_icp:
                intent.putExtra("url", ContentValue.ICPINFO);
                intent.putExtra("title", "网站备案");
                startActivity(intent);
                break;
            case R.id.card_ip:
                intent.putExtra("url", ContentValue.IPINFO);
                intent.putExtra("title", "IP查询");
                startActivity(intent);
                break;
            case R.id.card_pr:
                intent.putExtra("url", ContentValue.PRINFO);
                intent.putExtra("title", "PR查询");
                startActivity(intent);
                break;
            case R.id.card_blogroll:
                intent.putExtra("url", ContentValue.BLOGINFO);
                intent.putExtra("title", "友情链接");
                startActivity(intent);
                break;
            case R.id.card_link:
                intent.putExtra("url", ContentValue.LINKINFO);
                intent.putExtra("title", "反链查询");
                startActivity(intent);
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
        editText.setText(SpUtils.getString(this, ContentValue.DOMAIN, ""));
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(MainActivity.this);
        inputDialog.setTitle("请输入域名").setView(editText);
        inputDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this,
                                "修改域名：" + editText.getText().toString(),
                                Toast.LENGTH_SHORT).show();
                        SpUtils.putString(UIUtils.getContext(), ContentValue.DOMAIN
                                , editText.getText().toString());
                    }
                }).show();
        inputDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
    }

    private void showDialog(String[] items) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请选择工具");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }

    private void showIPDialog(String[] items) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请选择工具");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(MainActivity.this, WebActivity.class);
                if (i == 0) {
                    intent.putExtra("url", ContentValue.DOMAINDEL);
                    intent.putExtra("title", "域名删除");
                } else if (i == 1) {
                    intent.putExtra("url", ContentValue.SAME);
                    intent.putExtra("title", "同ip网站");
                } else if (i == 2) {
                    intent.putExtra("url", ContentValue.SUBDOMAIN);
                    intent.putExtra("title", "子域名查询");
                }
                startActivity(intent);
            }
        });
        builder.show();
    }

    private void showOtherDialog(String[] items) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请选择工具");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(MainActivity.this, WebActivity.class);
                if (i == 0) {
                    intent.putExtra("url", ContentValue.PING);
                    intent.putExtra("title", "超级ping");
                } else if (i == 1) {
                    intent.putExtra("url", ContentValue.TRACERT);
                    intent.putExtra("title", "路由器追踪");
                } else if (i == 2) {
                    intent.putExtra("url", ContentValue.PAGESTATUS);
                    intent.putExtra("title", "HTTP状态");
                } else if (i == 3) {
                    intent.putExtra("url", ContentValue.PORT);
                    intent.putExtra("title", "端口扫描");
                }
                startActivity(intent);
            }
        });
        builder.show();
    }

    private void showDomainInfoDialog(String[] items) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请选择工具");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(MainActivity.this, WebActivity.class);
                if (i == 0) {
                    intent.putExtra("url", ContentValue.DEADLINKINFO);
                    intent.putExtra("title", "死链检测");
                    startActivity(intent);
                } else if (i == 1) {
                    showKeyWorldsDia();
                } else if (i == 2) {
                    intent.putExtra("url", ContentValue.METACHECKINFO);
                    intent.putExtra("title", "META信息");
                    startActivity(intent);
                } else if (i == 3) {
                    intent.putExtra("url", ContentValue.EXPORTPRINFO);
                    intent.putExtra("title", "PR输出值");
                    startActivity(intent);
                }
            }
        });
        builder.show();
    }

    private void showKeyWorldsDia() {
        final EditText editText = new EditText(MainActivity.this);
        editText.setHint("请输入要查询的关键字");
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(MainActivity.this);
        inputDialog.setTitle("请输入关键字").setView(editText);
        inputDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, WebActivity.class);
                        intent.putExtra("url", ContentValue.
                                DENSITYKEYWORLD +
                                editText.getText().toString().trim() +
                                ContentValue.DENSITYKURL);
                        intent.putExtra("title", "关键词密度");
                        startActivity(intent);
                    }
                }).show();
    }

}
