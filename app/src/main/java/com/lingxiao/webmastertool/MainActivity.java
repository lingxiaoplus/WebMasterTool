package com.lingxiao.webmastertool;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.lingxiao.webmastertool.globle.ContentValue;
import com.lingxiao.webmastertool.seo.SeoInfoActivity;
import com.lingxiao.webmastertool.utils.SpUtils;
import com.lingxiao.webmastertool.utils.UIUtils;
import com.lingxiao.webmastertool.web.WebActivity;

import java.text.BreakIterator;

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

        View headLayout = leftNavigat.inflateHeaderView(R.layout.nav_header);
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
                    case R.id.tv_domain_status:
                        items = new String[]{"网站GZIP压缩", "网站历史记录", "竞争网站分析","网站安全检测"};
                        showDomainStatusDialog(items);
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

        TextView tvDomain = headLayout.findViewById(R.id.tv_menu_domain);
        tvDomain.setText(SpUtils.getString(this, ContentValue.DOMAIN, ""));
        CircleImageView circleImageView = headLayout.findViewById(R.id.circleImg);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog();
            }
        });
    }

    private void showDomainStatusDialog(String[] items) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请选择工具");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(MainActivity.this, WebActivity.class);
                if (i == 0) {
                    intent.putExtra("url", ContentValue.GZIPS);
                    intent.putExtra("isPC",true);
                    intent.putExtra("title", "网站GZIP压缩");
                    startActivity(intent);
                } else if (i == 1) {
                    intent.putExtra("url", ContentValue.HISTORY);
                    intent.putExtra("title", "网站历史记录");
                    startActivity(intent);
                } else if (i == 2) {
                    inputWebsitePk();

                }else if (i == 3){
                    intent.putExtra("url", ContentValue.WEBSAFE);
                    intent.putExtra("isPC",true);
                    intent.putExtra("title", "网站安全检测");
                    startActivity(intent);
                }

            }
        });
        builder.show();
    }

    /**
     *输入pk网站
     */
    private void inputWebsitePk() {
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(MainActivity.this);
        final AlertDialog dialog = inputDialog.create();
        View view = UIUtils.inflate(R.layout.dialog_website_pk);
        //dialog.setView(view,0,0,0,0);
        dialog.setView(view);
        dialog.show();
        final EditText pkHost = view.findViewById(R.id.et_website_pk);
        Button enSure = view.findViewById(R.id.bt_pk_ensure);
        Button canCle = view.findViewById(R.id.bt_pk_cancel);
        final TextInputLayout textInputLayout = dialog.findViewById(R.id.text_input_layout);
        enSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hostStr = pkHost.getText().toString();
                if (hostStr.isEmpty()){
                    textInputLayout.setErrorEnabled(true);
                    textInputLayout.setError("域名不能为空!");
                }else {
                    textInputLayout.setErrorEnabled(false);
                    Intent intent = new Intent(MainActivity.this, WebActivity.class);
                    intent.putExtra("url", ContentValue.WEBSITEPK+hostStr+"&host1=");
                    intent.putExtra("isPC",true);
                    intent.putExtra("title", "竞争网站分析");
                    startActivity(intent);
                }
            }
        });

        canCle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
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
                    intent.putExtra("isAddDomain",false);
                    intent.putExtra("title", "路由器追踪");
                    Toast.makeText(getApplicationContext(),"目前需要手动输入域名",
                            Toast.LENGTH_SHORT).show();
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
