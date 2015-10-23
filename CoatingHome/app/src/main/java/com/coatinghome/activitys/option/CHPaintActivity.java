package com.coatinghome.activitys.option;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.coatinghome.R;
import com.coatinghome.activitys.CHBaseActivity;
import com.coatinghome.providers.CHContrat;

import roboguice.inject.InjectView;

/**
 * Created by wuyajun on 15/10/23.
 * Detail: 油漆 界面
 */
public class CHPaintActivity extends CHBaseActivity {

    @InjectView(R.id.title_back)
    private ImageView mTitleBack;
    @InjectView(R.id.public_center_title)
    private TextView mCenterTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_paint);

        activityTitle = getIntent().getStringExtra(CHContrat.ACTIVITY_TITLE_TEXT);

        initViews();
    }

    private void initViews() {
        CHContrat.showView(mTitleBack, mCenterTitle);
        mTitleBack.setOnClickListener(this);
        mCenterTitle.setText(activityTitle);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finishActivity();
                break;
            default:
                break;
        }
    }
}
