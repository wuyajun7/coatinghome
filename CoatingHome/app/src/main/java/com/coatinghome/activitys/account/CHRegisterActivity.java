package com.coatinghome.activitys.account;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.coatinghome.R;
import com.coatinghome.activitys.CHBaseActivity;
import com.coatinghome.models.CHUserInfo;
import com.coatinghome.providers.CHContrat;

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
    private EditText mUserPwd;
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

    private final int BACK_CODE_CHECK_ACCOUNT = 0X01;
    private final int BACK_CODE_SEND_SMS_CODE = 0X02;
    private final int BACK_CODE_CHECK_SMS_CODE = 0X03;
    private final int BACK_CODE_REGISTER_STATE = 0X04;

    private CHUserInfo chUserInfo;

    private Handler backHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            CHContrat.hideView(mTitleProgress);

            switch (msg.arg1) {
                case BACK_CODE_CHECK_ACCOUNT://检测账号是否被注册 - 结果
                    if (msg.what == CHContrat.ONREGISTERSRCCESS) {
                        onlineDataInfoProvider.apiSendSmsCode(CHRegisterActivity.this, backHandler, mobile, BACK_CODE_SEND_SMS_CODE);
                    } else if (msg.what == CHContrat.ONREGISTERFAILURE) {
                        ShowToast("该手机号码已经被注册!");
                    } else {
                        ShowToast("手机检测失败!");
                    }
                    break;
                case BACK_CODE_SEND_SMS_CODE://发送验证码 - 结果
                    if (msg.what == CHContrat.ONSRCCESS) {
                        mGetValidCode.setEnabled(false);
                        mSmsCode.requestFocus();
                        mTimer.start();
                    } else {
                        ShowToast("验证码发送失败,请稍后重试!");
                    }
                    break;
                case BACK_CODE_CHECK_SMS_CODE://检查验证码 - 结果
                    if (msg.what == CHContrat.ONSRCCESS) {
                        onlineDataInfoProvider.apiRegisterAccount(CHRegisterActivity.this, backHandler, chUserInfo, BACK_CODE_REGISTER_STATE);
                    } else {
                        ShowToast("验证码已过期,请重新获取!");
                    }
                    break;
                case BACK_CODE_REGISTER_STATE://注册 - 结果
                    if (msg.what == CHContrat.ONSRCCESS) {
                        ShowToast("用户注册成功!");

                        Intent intent = new Intent();
                        intent.putExtra(CHContrat.RESULT_MOBILE, mobile);
                        CHRegisterActivity.this.setResult(RESULT_OK, intent);

                        finishActivity();
                    } else {
                        ShowToast("用户注册失败!");
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
    }

    private void initViews() {
        mCenterTitle.setText("注册");
        CHContrat.showView(mTitleBack, mCenterTitle);

        mTitleBack.setOnClickListener(this);
        mGetValidCode.setOnClickListener(this);
        mRegisterBtn.setOnClickListener(this);

        mAgreementCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mIsAcceptAgreement = isChecked;
            }
        });
        mAgreementCheckBox.setChecked(true);

        // 键盘“前往”登录
        mCompanyName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    if (checkRegisterContent(false)) {
                        CHContrat.showView(mTitleProgress);
                        onlineDataInfoProvider.apiCheckSmsCode(CHRegisterActivity.this, backHandler, mobile, smsCode, BACK_CODE_CHECK_SMS_CODE);
                    }
                    return true;
                }
                return false;
            }
        });

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finishActivity();
                break;
            case R.id.btn_get_valid_code:
                if (!checkRegisterContent(true)) return;
                CHContrat.showView(mTitleProgress);
                onlineDataInfoProvider.apiCheckAccount(CHRegisterActivity.this, backHandler, mobile, BACK_CODE_CHECK_ACCOUNT);
                break;
            case R.id.register_button:
                if (!checkRegisterContent(false)) return;
                CHContrat.showView(mTitleProgress);
                onlineDataInfoProvider.apiCheckSmsCode(CHRegisterActivity.this, backHandler, mobile, smsCode, BACK_CODE_CHECK_SMS_CODE);
                break;
        }
    }

    private String mobile;
    private String smsCode;
    private String pwd;
    private String userName;
    private String companyName;
    private boolean mIsAcceptAgreement;

    private boolean checkRegisterContent(boolean isOnlyCheckMobile) {

        mobile = mUserMobile.getText().toString();
        smsCode = mSmsCode.getText().toString();
        pwd = mUserPwd.getText().toString();
        userName = mUserName.getText().toString();
        companyName = mCompanyName.getText().toString();

        if (TextUtils.isEmpty(mobile)) {
            mUserMobile.requestFocus();
            mUserMobile.setError("帐号不能为空哟！");
            return false;
        } else if (!CHContrat.isMobileNO(mobile)) {
            mUserMobile.requestFocus();
            mUserMobile.setError("帐号格式不正确！");
            return false;
        }

        if (!isOnlyCheckMobile) {
            if (TextUtils.isEmpty(smsCode)) {
                mSmsCode.requestFocus();
                mSmsCode.setError("验证码不能为空哟！");
                return false;
            } else if (!CHContrat.isSmsCode(smsCode)) {
                mSmsCode.requestFocus();
                mSmsCode.setError("验证码格式不正确！");
                return false;
            }

            if (TextUtils.isEmpty(pwd)) {
                mUserPwd.requestFocus();
                mUserPwd.setError("密码不能为空哟！");
                return false;
            } else if (!CHContrat.isPwd(pwd)) {
                mUserPwd.requestFocus();
                mUserPwd.setError("密码格式不正确！请输入6-15位数字或字母密码");
                return false;
            }

            if (TextUtils.isEmpty(userName)) {
                mUserName.requestFocus();
                mUserName.setError("用户名不能为空哟！");
                return false;
            }

            if (TextUtils.isEmpty(companyName)) {
                mCompanyName.requestFocus();
                mCompanyName.setError("公司名不能为空哟！");
                return false;
            }

            if (!mIsAcceptAgreement) {
                ShowToast("请选择接受服务协议！");
                return false;
            }
            chUserInfo = new CHUserInfo();
            chUserInfo.setMobilePhoneNumber(mobile);
            chUserInfo.setUserPwd(pwd);
            chUserInfo.setUsername(userName);
            chUserInfo.setUserCompanyName(companyName);
        }
        return true;
    }
}
