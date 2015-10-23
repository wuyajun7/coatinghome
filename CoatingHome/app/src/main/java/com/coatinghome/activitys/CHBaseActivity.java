package com.coatinghome.activitys;

import android.os.Bundle;
import android.view.View;

import roboguice.activity.RoboActivity;

/**
 * Created by wuyajun on 15/10/22.
 * Detail: Activity 基类
 */
public abstract class CHBaseActivity extends RoboActivity implements View.OnClickListener {

    public String activityTitle = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void finishActivity() {
        this.finish();
    }
}
