package com.coatinghome.activitys.account;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Selection;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.coatinghome.R;
import com.coatinghome.activitys.CHBaseActivity;
import com.coatinghome.activitys.CHMainActivity;
import com.coatinghome.providers.CHContrat;

import roboguice.inject.InjectView;

/**
 * Created by wuyajun on 15/10/24.
 * Detail: 登陆界面
 */
public class CHLoginActivity extends CHBaseActivity {

    @InjectView(R.id.pub_title_view)
    private LinearLayout mPubTitleView;
    @InjectView(R.id.title_progress)
    private ProgressBar mTitleProgress;

    @InjectView(R.id.login_input_account)
    private EditText mLoginInputAccount;
    @InjectView(R.id.login_input_pw)
    private EditText mLoginInputPw;

    @InjectView(R.id.login_forget_pw_text)
    private TextView mLoginFogetPw;
    @InjectView(R.id.login_register_text)
    private TextView mLoginRegister;

    @InjectView(R.id.login_button)
    private Button mLoginButton;

    private Intent intent = new Intent();

    private Handler backHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            CHContrat.hideView(mTitleProgress);

            if (msg != null) {
                if (msg.what == CHContrat.ONSRCCESS) {
                    intent.setClass(CHLoginActivity.this, CHMainActivity.class);
                    startActivity(intent);
                    finishActivity();
                } else {
                    switch (msg.obj.toString()) {
                        case "101":
                            ShowToast("登录失败,账号或密码错误！");
                            break;
                        default:
                            ShowToast("登录失败,账号或密码错误！");
                            break;
                    }
                }
            } else {
                ShowToast("用户登录失败,请核对账号密码！");
            }
        }
    };

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

        // 键盘“前往”登录
        mLoginInputPw.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    if (checkRegisterContent()) {
                        CHContrat.showView(mTitleProgress);
                        onlineDataInfoProvider.apiLogin(CHLoginActivity.this, backHandler, account, pwd);
                    }
                    return true;
                }
                return false;
            }
        });

        mLoginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                if (!checkRegisterContent()) return;
                CHContrat.showView(mTitleProgress);
                onlineDataInfoProvider.apiLogin(CHLoginActivity.this, backHandler, account, pwd);
                break;
            case R.id.login_register_text:
                intent.setClass(CHLoginActivity.this, CHRegisterActivity.class);
                startActivityForResult(intent, CHContrat.R_CODE_LOGIN_TO_REGISTER);
                break;
            default:
                break;
        }
    }

    private String account;
    private String pwd;

    private boolean checkRegisterContent() {
        account = mLoginInputAccount.getText().toString();
        pwd = mLoginInputPw.getText().toString();

        if (TextUtils.isEmpty(account)) {
            mLoginInputAccount.requestFocus();
            mLoginInputAccount.setError("帐号不能为空哟！");
            return false;
        } else if (!CHContrat.isMobileNO(account)) {
            mLoginInputAccount.requestFocus();
            mLoginInputAccount.setError("帐号格式不正确！");
            return false;
        }
        if (TextUtils.isEmpty(pwd)) {
            mLoginInputPw.requestFocus();
            mLoginInputPw.setError("密码不能为空哟！");
            return false;
        } else if (pwd.length() < 6) {
            mLoginInputPw.requestFocus();
            mLoginInputPw.setError("密码错误！");
            return false;
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == CHContrat.R_CODE_LOGIN_TO_REGISTER) {
                String mobile = data.getStringExtra(CHContrat.RESULT_MOBILE);
                if (!TextUtils.isEmpty(mobile)) {
                    mLoginInputAccount.setText(mobile);
                    Selection.setSelection(mLoginInputAccount.getText(), mLoginInputAccount.getText().length());
                }
            }
        }
    }
}
