package com.coatinghome.utils.sharedpreference;

import android.content.Context;

/**
 * 应用内部设置 工具类
 */
public class SPAppInner extends AbstractSharedPreference {

    private static final String STORE_NAME = "nnqc_share_data";

    //不要手动进行new
    public SPAppInner(Context context) {
        super(context, STORE_NAME);
    }

    //TAG

    /* 短信验证码 */
    public static final String TAG_SMS_CODE = "TAG_SMS_CODE";

}
