package com.coatinghome.activitys.account;

import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coatinghome.R;
import com.coatinghome.activitys.CHBaseActivity;
import com.coatinghome.providers.CHContrat;

import roboguice.inject.InjectView;

/**
 * Created by wuyajun on 15/10/24.
 * Detail: 登陆界面
 */
public class CHLoginActivity extends CHBaseActivity {

    @InjectView(R.id.pub_title_view)
    private LinearLayout mPubTitleView;

    @InjectView(R.id.login_input_account)
    private EditText mLoginInputAccount;
    @InjectView(R.id.login_input_pw)
    private EditText mLoginInputPw;

    @InjectView(R.id.login_forget_pw_text)
    private TextView mLoginFogetPw;
    @InjectView(R.id.login_register_text)
    private TextView mLoginRegister;

    @InjectView(R.id.login_button)
    private TextView mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
    }

    private void initViews() {
        mPubTitleView.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        mLoginFogetPw.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        mLoginFogetPw.getPaint().setAntiAlias(true);//抗锯齿
        mLoginFogetPw.setOnClickListener(this);
        mLoginRegister.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        mLoginRegister.getPaint().setAntiAlias(true);//抗锯齿
        mLoginRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                if (checkRegisterContent()) return;
                break;
            default:
                break;
        }
    }

    private String account;
    private String pwd;

    private boolean checkRegisterContent() {
        account = mLoginInputAccount.getText().toString();
        pwd = mLoginFogetPw.getText().toString();
        if (TextUtils.isEmpty(account)) {
            mLoginInputAccount.setError("帐号不能为空哟！");
            return false;
        } else if (!CHContrat.isMobileNO(account)) {
            mLoginInputAccount.setError("帐号格式不正确！");
            return false;
        }
        if (TextUtils.isEmpty(pwd)) {
            mLoginFogetPw.setError("密码不能为空哟！");
            return false;
        } else if (pwd.length() < 6) {
            mLoginFogetPw.setError("密码错误！");
            return false;
        }

        return true;
    }
}
