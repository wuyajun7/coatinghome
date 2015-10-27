package com.coatinghome.activitys.account;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.coatinghome.CHApplication;
import com.coatinghome.R;
import com.coatinghome.activitys.CHBaseActivity;
import com.coatinghome.providers.CHContrat;
import com.coatinghome.utils.sharedpreference.SPUtils;

import roboguice.inject.InjectView;

/**
 * Created by wuyajun on 15/10/24.
 * Detail: 注册界面
 */
public class CHRegisterActivity extends CHBaseActivity {

    @InjectView(R.id.title_back)
    private ImageView mTitleBack;
    @InjectView(R.id.public_center_title)
    private TextView mCenterTitle;
    @InjectView(R.id.title_progress)
    private ProgressBar mTitleProgress;

    @InjectView(R.id.input_user_mobile)
    private EditText mUserMobile;
    @InjectView(R.id.btn_get_valid_code)
    private Button mGetValidCode;
    @InjectView(R.id.input_sms_code)
    private EditText mSmsCode;
    @InjectView(R.id.input_user_pw)
    private EditText mUserPw;
    @InjectView(R.id.input_user_name)
    private EditText mUserName;
    @InjectView(R.id.input_company_name)
    private EditText mCompanyName;
    @InjectView(R.id.agreement_checkbox)
    private CheckBox mAgreementCheckBox;
    @InjectView(R.id.service_agreement)
    private TextView mServiceAgreement;
    @InjectView(R.id.register_button)
    private Button mRegisterBtn;

    private CountDownTimer mTimer;
    private final long DURATION = 60000L;
    private final long INTERVAL = 1000L;

    private final int BACK_CODE_0X01 = 0X01;
    private final int BACK_CODE_0X02 = 0X02;

    private Handler backHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            CHContrat.hideView(mTitleProgress);

            switch (msg.arg1) {
                case BACK_CODE_0X01://检测账号是否被注册 - 结果
                    if (msg.what == CHContrat.ONREGISTERSRCCESS) {
                        onlineDataInfoProvider.apiSendSmsCode(CHRegisterActivity.this, backHandler, mobile, BACK_CODE_0X02);
                    } else if (msg.what == CHContrat.ONREGISTERFAILURE) {
                        ShowToast("该手机号码已经被注册!");
                    } else {
                        ShowToast("手机检测失败!");
                    }
                    break;
                case BACK_CODE_0X02://发送验证码 - 结果
                    if (msg.what == CHContrat.ONSRCCESS) {
                        mGetValidCode.setEnabled(false);
                        mSmsCode.requestFocus();
                        mTimer.start();
                    } else {
                        ShowToast("验证码发送失败,请稍后重试!");
                    }
                    break;
                default:
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();


        mTimer = new CountDownTimer(DURATION, INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                mGetValidCode.setText(String.valueOf(millisUntilFinished / INTERVAL) + "秒后重发");
            }

            @Override
            public void onFinish() {
                mGetValidCode.setEnabled(true);
                mGetValidCode.setText("获取验证码");
            }
        };
    }

    private void initViews() {
        mCenterTitle.setText("注册");
        CHContrat.showView(mTitleBack, mCenterTitle);

        mTitleBack.setOnClickListener(this);
        mGetValidCode.setOnClickListener(this);
        mRegisterBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finishActivity();
                break;
            case R.id.btn_get_valid_code:
                if (!checkRegisterContent()) return;
                CHContrat.showView(mTitleProgress);

                onlineDataInfoProvider.apiCheckAccount(CHRegisterActivity.this, backHandler, mobile, BACK_CODE_0X01);

                break;
            case R.id.register_button:
                onlineDataInfoProvider.apiCheckSmsCode(CHRegisterActivity.this, backHandler, "13122782686",653290, BACK_CODE_0X01);
                if (checkRegisterContent()) {

                }
                break;
        }
    }

    private String mobile;

    private boolean checkRegisterContent() {

        mobile = mUserMobile.getText().toString();

        if (TextUtils.isEmpty(mobile)) {
            mUserMobile.requestFocus();
            mUserMobile.setError("帐号不能为空哟！");
            return false;
        } else if (!CHContrat.isMobileNO(mobile)) {
            mUserMobile.requestFocus();
            mUserMobile.setError("帐号格式不正确！");
            return false;
        }

        return true;
    }
}
