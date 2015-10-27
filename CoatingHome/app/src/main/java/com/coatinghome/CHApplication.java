package com.coatinghome;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;

import com.coatinghome.utils.ACLogger;
import com.coatinghome.utils.PropertiesConfig;
import com.coatinghome.utils.sharedpreference.AbstractSharedPreference;
import com.coatinghome.utils.sharedpreference.SPAppInner;
import com.coatinghome.utils.sharedpreference.SharedPreferenceFactory;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.util.LinkedList;
import java.util.List;

import cn.bmob.v3.Bmob;

/**
 * Created by wuyajun on 15/10/20.
 * Detail:
 */
public class CHApplication extends Application {

    public static ACLogger Logs = ACLogger.kLog();
    public static AbstractSharedPreference asp;
    private static CHApplication instance;

    private List<Activity> activityList = new LinkedList<Activity>();

    public static CHApplication getInstance() {
        if (null == instance) {
            instance = new CHApplication();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initImageLoader(this);

        //(严格模式):监视APP相关的运行情况 - 发布应用时关闭此处
        if (PropertiesConfig.getInstance(this).isDeveloper() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .penaltyDialog()
                    .detectNetwork()
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build());
        }

        initPlugIn();

    }

    /* 初始化 第三方 插件 */
    private void initPlugIn() {
        // 初始化 Bmob SDK - 第二个参数Application ID
        Bmob.initialize(this, PropertiesConfig.getInstance(this).getBmobApplicationId());

        /* 初始化偏好设置帮助类 */
        asp = SharedPreferenceFactory.getSharedPreference(this, SPAppInner.class);
    }

    /* 初始化 ImageLoader */
    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .memoryCacheExtraOptions(480, 800)
                .diskCacheExtraOptions(480, 800, null)
                .threadPoolSize(3)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(13)
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .imageDownloader(new BaseImageDownloader(context))
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .writeDebugLogs()
                .build();

        ImageLoader.getInstance().init(config);
    }

    //添加Activity到容器中
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public void removeActivity(Activity activity) {
        activityList.remove(activity);
    }

    public List<Activity> getActivityList() {
        return activityList;
    }

    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        //System.exit(0);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        ImageLoader.getInstance().clearMemoryCache();
    }
}
