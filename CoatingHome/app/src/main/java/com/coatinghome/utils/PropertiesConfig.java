package com.coatinghome.utils;

import android.content.Context;
import android.text.TextUtils;

import com.coatinghome.CHApplication;

import java.io.IOException;

/**
 * Created by wuyajun on 15-10-02.
 * <p>
 * 配置开关
 */
public class PropertiesConfig {

    private String TAG = "PropertiesConfig";
    private final String PROPERTIES_NAME = "config.properties";
    private final String TRUE = "true";

    private static PropertiesConfig moudleSwitchRWTool = null;
    private PropertiesRead2Write read2WriteProperties = null;

    public void LogI(String msg) {
        CHApplication.Logs.i(TAG, msg);
    }

    public PropertiesConfig(Context context) {
        read2WriteProperties = new PropertiesRead2Write(context, PROPERTIES_NAME);
    }

    public static PropertiesConfig getInstance(Context context) {
        if (moudleSwitchRWTool == null) {
            moudleSwitchRWTool = new PropertiesConfig(context);
        }
        return moudleSwitchRWTool;
    }

    /* 是否是开发模式 */
    public boolean isDeveloper() {
        String developerMode = null;
        try {
            developerMode = read2WriteProperties.getValue("developer.mode");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (TRUE.equals(developerMode)) {
            return true;
        }
        return false;
    }

    /* 获取包名 */
    public String getPackageName() {
        try {
            String packageName = read2WriteProperties.getValue("package.name");
            if (!TextUtils.isEmpty(packageName)) {
                return packageName;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
