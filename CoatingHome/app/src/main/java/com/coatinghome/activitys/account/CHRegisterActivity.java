package com.coatinghome.activitys.account;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.coatinghome.R;
import com.coatinghome.activitys.CHBaseActivity;
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
        mRegisterBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finishActivity();
                break;
            case R.id.register_button:
                if (checkRegisterContent()) {

                }
                break;
        }
    }

    private boolean checkRegisterContent() {

        return false;
    }
}
