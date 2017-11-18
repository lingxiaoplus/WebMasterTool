package com.lingxiao.webmastertool;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.lingxiao.webmastertool.globle.ContentValue;
import com.lingxiao.webmastertool.utils.SpUtils;
import com.lingxiao.webmastertool.utils.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends BaseActivity {
    @BindView(R.id.et_domain)
    EditText etDomain;
    @BindView(R.id.input_domain)
    TextInputLayout inputDomain;
    @BindView(R.id.bt_commit)
    Button btCommit;
    private String domainName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        String domain = SpUtils
                .getString(this,ContentValue.DOMAIN,"");
        if (!domain.isEmpty()){
            StartActivity(MainActivity.class,true);
        }
    }

    @OnClick(R.id.bt_commit)
    public void onCommit() {
        domainName = etDomain.getText().toString().trim();
        if (domainName.isEmpty()) {
            inputDomain.setErrorEnabled(true);
            inputDomain.setError("域名格式不正确!");
        } else {
            SpUtils.putString(getApplicationContext(),
                    ContentValue.DOMAIN,domainName);
            StartActivity(MainActivity.class,true);
        }
    }
}
