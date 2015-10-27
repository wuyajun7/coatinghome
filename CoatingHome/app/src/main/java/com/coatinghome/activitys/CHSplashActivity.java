package com.coatinghome.activitys;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.coatinghome.R;
import com.coatinghome.activitys.account.CHLoginActivity;
import com.coatinghome.models.CHUserInfo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import cn.bmob.v3.BmobUser;

/**
 * Created by wuyajun on 15/10/26.
 * Detail: 闪屏页面
 */
public class CHSplashActivity extends CHBaseActivity {

    private static final int HAVE_ACCOUNT = 0X01;
    private static final int NO_ACCOUNT = 0X02;

    private static final long SPLASH_DURATION = 1000L;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            goToActivity(msg.what);
        }
    };

    private void goToActivity(int what) {
        Intent intent = new Intent();
        switch (what) {
            case HAVE_ACCOUNT:
                intent.setClass(CHSplashActivity.this, CHMainActivity.class);
                startActivity(intent);
                break;
            case NO_ACCOUNT:
                intent.setClass(CHSplashActivity.this, CHLoginActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

        finishActivity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //透明状态栏
        if (!setTransparentNotification()) {
            if (Build.VERSION.SDK_INT >= 19/* Build.VERSION_CODES.KITKAT*/) {
                window.addFlags(0x04000000/*WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS*/);
            }
        }

        initAccount();
    }

    private void initAccount() {
        CHUserInfo userInfo = BmobUser.getCurrentUser(this, CHUserInfo.class);
        if (userInfo != null) {//如果用户已经登录
            long delay = SPLASH_DURATION;
            mHandler.sendEmptyMessageDelayed(HAVE_ACCOUNT, delay);
        } else {//如果用户没有登录
            long delay = SPLASH_DURATION;
            mHandler.sendEmptyMessageDelayed(NO_ACCOUNT, delay);
        }
    }

    @Override
    public void onClick(View v) {

    }
}
