package com.coatinghome.activitys;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.coatinghome.CHApplication;
import com.coatinghome.providers.CHContrat;
import com.coatinghome.services.online.OnlineDataInfoProvider;
import com.google.inject.Inject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import roboguice.activity.RoboActivity;

/**
 * Created by wuyajun on 15/10/22.
 * Detail: Activity 基类
 */
public abstract class CHBaseActivity extends RoboActivity implements View.OnClickListener {

    @Inject
    protected OnlineDataInfoProvider onlineDataInfoProvider;

    protected String activityTitle = "";

    /* -------------------------- 透明状态栏 START --------------------------- */
    protected Window window = null;
    protected int VERSION_CODES = 19;
    protected int CLEAR_LAYOUTPARAMS_FLAG = 67108864;
    protected int VISIBILITY_FLAG1 = 1024;
    protected int VISIBILITY_FLAG2 = 256;
    protected int ADD_LAYOUTPARAMS_FLAG = -2147483648;
    protected int STATYSVARCOLOR = Color.TRANSPARENT;

    protected boolean setTransparentNotification() {
        try {
            Class clazz1 = Class.forName("android.os.Build$VERSION_CODES");
            Field field1 = clazz1.getField("LOLLIPOP");
            VERSION_CODES = field1.getInt(int.class);

            Class clazz2 = Class.forName("android.view.WindowManager$LayoutParams");
            Field field2 = clazz2.getField("FLAG_TRANSLUCENT_STATUS");
            CLEAR_LAYOUTPARAMS_FLAG = field2.getInt(int.class);

            Class clazz3 = Class.forName("android.view.View");
            Field field3 = clazz3.getField("SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN");
            VISIBILITY_FLAG1 = field3.getInt(int.class);

            Class clazz4 = Class.forName("android.view.View");
            Field field4 = clazz4.getField("SYSTEM_UI_FLAG_LAYOUT_STABLE");
            VISIBILITY_FLAG2 = field4.getInt(int.class);

            Class clazz5 = Class.forName("android.view.WindowManager$LayoutParams");
            Field field5 = clazz5.getField("FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS");
            ADD_LAYOUTPARAMS_FLAG = field5.getInt(int.class);

            if (Build.VERSION.SDK_INT >= VERSION_CODES) {
                window.clearFlags(CLEAR_LAYOUTPARAMS_FLAG);
                window.getDecorView().setSystemUiVisibility(VISIBILITY_FLAG1 | VISIBILITY_FLAG2);
                window.addFlags(ADD_LAYOUTPARAMS_FLAG);
                /* 部分手机此方法会报异常 */
                Method method = window.getClass().getMethod("setStatusBarColor", int.class);
                method.invoke(window, STATYSVARCOLOR);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /* -------------------------- 透明状态栏 END --------------------------- */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (window == null) window = getWindow();

        CHApplication.getInstance().addActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        CHContrat.closeIme(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CHApplication.getInstance().removeActivity(this);
    }

    public void finishActivity() {
        this.finish();
    }

    /*---------------------- Toast START -------------------------*/
    protected Toast mToast;

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
    /*---------------------- Toast END -------------------------*/
}
