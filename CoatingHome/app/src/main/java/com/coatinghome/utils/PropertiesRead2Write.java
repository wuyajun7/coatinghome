package com.coatinghome.utils;

/**
 * Created by wuyajun on 15-10-02.
 */

import android.content.Context;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

//    import org.slf4j.Logger;

/**
 * <pre>
 * 用于读取.properties文件
 * </pre>
 * <hr Color="green" >
 * </hr>
 * 2012 Qingshan Group 版权所有
 * <hr Color="green" >
 * </hr>
 *
 * @author thetopofqingshan
 * @version 1.0.0
 * @date 2012-4-8
 * @since JDK 1.5
 */
public class PropertiesRead2Write {

    private String TAG = "PropertiesRead2Write";
    private ACLogger Logs = ACLogger.kLog();

    public void LogI(String msg) {
        Logs.i(TAG, msg);
    }

    public PropertiesRead2Write() {
    }

    public PropertiesRead2Write(Context context, String propertiesName) {
        mContext = context;
        this.propertiesName = propertiesName;
    }

    /*
     * 属性文件名:一般是工程src目录下
     */
    private String propertiesName;
    private Context mContext = null;

    public String getPropertiesName() {
        return propertiesName;
    }

    public void setPropertiesName(String propertiesName) {
        this.propertiesName = propertiesName;
    }

    /**
     * <pre>
     * 根据属性文件key键, 得到相应的值
     * </pre>
     *
     * @param key key键
     * @return key键对应的值
     * @throws IOException 读取文件发生错误
     */
    public String getValue(String key) throws IOException {
        InputStream is = null;

        try {
            if (propertiesName == null || propertiesName.length() == 0) {
                LogI("属性文件不存在");
                return null;
            } else {
                is = mContext.getResources().getAssets().open(propertiesName);
            }

            if (is == null) {
                throw new FileNotFoundException(propertiesName + " 找不到...");
            }

            Properties p = new Properties();
            p.load(is);
            return p.getProperty(key, "没有找到相应的值");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            is.close();
        }

        return null;
    }

    public boolean setValue(String key, String value) throws IOException {
        OutputStream os = null;
        if (propertiesName == null || propertiesName.length() == 0) {
            LogI(propertiesName + " 属性文件不存在");
            System.out.println();
        } else {
            os = new FileOutputStream(mContext.getResources().getAssets().list(propertiesName)[0]);
        }

        if (os == null) {
            return false;
        }

        try {
            Properties p = new Properties();
            p.load(getClass().getClassLoader().getResourceAsStream(propertiesName));

            p.setProperty(key, value);//插入配置信息
            p.store(os, "my name is the top of qingshan");
            System.out.println(p);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            os.close();
        }
        return true;
    }
}
