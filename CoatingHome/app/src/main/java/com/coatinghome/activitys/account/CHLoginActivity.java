package com.coatinghome.activitys.account;

import android.os.Bundle;
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
    @InjectView(R.id.public_center_title)
    private TextView mCenterTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
    }

    private void initViews() {
        mPubTitleView.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        mCenterTitle.setText("登录");
        mCenterTitle.setTextColor(getResources().getColor(R.color.colorGray1));
        CHContrat.showView( mCenterTitle);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }

    private boolean checkRegisterContent() {

        return false;
    }
}
