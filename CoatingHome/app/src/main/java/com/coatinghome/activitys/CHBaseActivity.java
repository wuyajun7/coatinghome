package com.coatinghome.activitys;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.coatinghome.CHApplication;

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
        CHApplication.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CHApplication.getInstance().removeActivity(this);
    }

    public void finishActivity() {
        this.finish();
    }

    /*---------------------- Toast -------------------------*/
    private Toast mToast;

    public void ShowToast(String text) {
        if (!TextUtils.isEmpty(text)) {
            if (mToast == null) {
                mToast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
            } else {
                mToast.setText(text);
            }
            mToast.show();
        }
    }
}
