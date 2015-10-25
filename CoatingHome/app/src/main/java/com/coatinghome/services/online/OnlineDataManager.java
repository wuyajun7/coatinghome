package com.coatinghome.services.online;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.format.Time;

import com.coatinghome.CHApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wuyajun on 15/10/22.
 * Detail: 在线数据缓存管理类
 */
public class OnlineDataManager {

    /**
     * 缓存的文件名字
     */
    public static String MAI_CHANG = "mai_chang";

    /**
     * 缓存 menu菜单中的广告数据  文件名字
     */
    public static String MENU_ADMOB = "menu_admob";

    /**
     * 缓存 启动页中的广告数据  文件名字
     */
    public static String START_ADMOB = "start_admob";

    /**
     * 缓存 主界面中的广告数据  文件名字
     */
    public static String MAIN_ADMOB = "main_admob";

    /**
     * 缓存 主界面中的list数据  文件名字
     */
    public static String MAIN_LIST_SONG = "main_list_song";

    /**
     * 缓存 麦唱金曲 数据的文件名字
     */
    public static String MC_GOLD_SONG = "mc_gold_song";

    /**
     * 缓存 猜你喜欢 数据的文件名字
     */
    public static String GUESS_YOU_LIKE = "guess_you_like";

    /**
     * 缓存的文件夹名称
     */
    String CACHE_FILE_FOLDER = "OnlineCache";

    /**
     * 在文件写入成功的末尾加入这个字串用于校验
     */
    private final String CHECK_NUM = "//CHECK";

    private Date lastUpdateResDate = null;
    private final int AFTERNOOT_UPDATE_HOUR = 18;
    private final int MORNING_UPDATE_HOUR = 0;

    private boolean DEBUG_MODE = true;
    private final String TAG = "ONLINE_RESOURCE_UPDATE";

    private String fileContent = null;

    private void LogI(String msg) {
        if (DEBUG_MODE) {
            CHApplication.Logs.i(TAG, msg);
        }
    }

    private String getFileContent(Context context, File file) {
        LogI("getFileContent");
        String contentStr = "";
        FileInputStream inputStream = null;
        Reader reader = null;
        try {
            int tempChar = -1;
            char[] buffer = new char[256];
            char temp;
            inputStream = context.openFileInput(file.getName());
            reader = new InputStreamReader(inputStream);
            if (reader != null) {
                while ((tempChar = reader.read(buffer)) != -1) {
                    if (tempChar == buffer.length) {
                        contentStr += String.valueOf(buffer);
                    } else {
                        for (int i = 0; i < tempChar; i++) {
                            contentStr += String.valueOf(buffer[i]);
                        }
                    }
                }
                LogI("contentStr:" + contentStr);
                return contentStr;
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogI("IOException e");
            return null;
        } finally {
            try {
                inputStream.close();
                reader.close();
            } catch (IOException e) {
                LogI("finally IOException e");
                e.printStackTrace();
                return null;
            }

        }
        return null;
    }

    public boolean isCacheUseFull(Context context, String fileName) {
        /**
         * 1.文件是否存在
         * 2.文件内容是否合法，校验通过
         * 3.是否符合更新策略（时间：每天更新一次？）
         */
        String absolutFileName = "";
        LogI("isCacheUseFull");
        absolutFileName = "data/data/" + context.getPackageName() + "/files/" + fileName;
        File file = new File(absolutFileName);
        if (!(file != null && file.exists() && file.canRead())) {
            LogI("文件存在不合法");
            return false;
        }

        String content = getFileContent(context, file);
        if (content == null) {
            LogI("文件内容为null");
            return false;
        }

        if (!content.endsWith(CHECK_NUM)) {
            LogI("文件校验和不合法");
            return false;
        }

        if (isMatchToUpdateStrategy(context, fileName)) {
            LogI("更新策略符合，要更新");
            return false;
        }

        fileContent = content;
        LogI("fileContent：" + fileContent);
        return true;
    }

    /**
     * 清除缓存
     *
     * @param context
     */
    public void clearCacheFile(Context context) {
        String cacheFilePath = "data/data/" + context.getPackageName() + "/files/";
        File file = new File(cacheFilePath);
        if (file.exists()) {
            try {
                file.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void addContentToCacheFile(Context context, String fileName, String content) {
        //1.文件不存在则创建文件
        //2。content写入
        //3. 加入写入完成标志用于做校验
        LogI("addContentToCacheFile");
        String absolutFileName = "";
        content += CHECK_NUM;

        LogI("写入文件的内容：" + content);
        absolutFileName = "data/data/" + context.getPackageName() + "/files/" + fileName;
        File file = new File(absolutFileName);
        file.delete();

        if (!file.exists()) {
            LogI("创建文件路径:" + absolutFileName);
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileOutputStream outputStream = null;
        OutputStreamWriter writer = null;

        try {
            outputStream = context.openFileOutput(fileName, 0);
            writer = new OutputStreamWriter(outputStream);
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
            LogI("文件写入异常:" + fileName);
        } finally {
            try {
                outputStream.flush();
                writer.flush();
                outputStream.close();
                writer.close();
            } catch (IOException e) {
                LogI("finally 文件写入异常:" + fileName);
                e.printStackTrace();
            }
        }

        saveLastUpdateTime(context, fileName);
    }

    private void saveLastUpdateTime(Context context, String fileName) {
        LogI("saveLastUpdateTime：" + fileName);
        Date curDate = new Date(System.currentTimeMillis());
        LogI("curDate：" + curDate);

        String DATE_FORMAT = "yyyy-MM-dd HH-mm-ss";
        String curDateStr = "";
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        curDateStr = format.format(curDate);
        LogI("存入sp的时间：" + curDateStr);
        SystemSettingsInfoProvider systemSettingsInfoProvider = new SystemSettingsInfoProvider(context);
        systemSettingsInfoProvider.setLastUpdateTime(fileName, curDateStr);
    }

    public String getCacheFileContent(Context context, String fileName) {
        //1.取出文件中存储的内容
        //2. 去掉用于校验的结尾标志
        //3. 返回
        LogI("getCacheFileContent：" + fileName);
        File file = new File(fileName);
        if (fileContent == null) {
            fileContent = getFileContent(context, file);
        }
        if (fileContent != null) {
            int checkIndex = fileContent.indexOf(CHECK_NUM);
            return fileContent.substring(0, checkIndex);
        }
        return null;
    }

    /**
     * 是否符合更新策略
     *
     * @return
     */
    private boolean isMatchToUpdateStrategy(Context context, String fileName) {
        LogI("isMatchToUpdateStrategy：" + fileName);
        boolean need = needUpdateOnlineData(context, fileName);
        return need;
    }


    /**
     * 判断两个日期是否为同一天
     *
     * @param curDate
     * @param lastDate
     * @return
     */
    private boolean isSameDay(Date curDate, Date lastDate) {
        LogI("isSameDay：");
        LogI("curDate：" + curDate);
        LogI("lastDate：" + lastDate);

        String DATE_FORMAT = "yyyy-MM-dd";
        String curDateStr = "";
        String lastDateStr = "";
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        curDateStr = format.format(curDate);
        lastDateStr = format.format(lastDate);
        return curDateStr.equals(lastDateStr);
    }


    /**
     * 时间是否符合更新策略
     *
     * @return
     */
    private boolean needUpdateOnlineData(Context context, String fileName) {
        LogI("needUpdateOnlineData：" + fileName);
        Time curTime = new Time();
        curTime.setToNow();
        LogI("curTime：" + curTime.toString());

        Date curDate = new Date(System.currentTimeMillis());
        LogI("curDate：" + curDate.toString());

        Time afternoonUpdateTime = new Time();
        afternoonUpdateTime.set(0, 0, AFTERNOOT_UPDATE_HOUR, curTime.monthDay, curTime.month, curTime.year);
        LogI("afternoonUpdateTime：" + afternoonUpdateTime.toString());

        Date afternoonUpdateDate = new Date(afternoonUpdateTime.toMillis(true));
        LogI("afternoonUpdateDate：" + afternoonUpdateDate.toString());

        SystemSettingsInfoProvider systemSettingsInfoProvider = new SystemSettingsInfoProvider(context);
        lastUpdateResDate = new Date();
        String lastUpdateTimeStr = systemSettingsInfoProvider.getLastUpdateTime(fileName);
        LogI("lastUpdateTimeStr：" + lastUpdateTimeStr);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");

        try {
            lastUpdateResDate = formatter.parse(lastUpdateTimeStr);
            LogI("lastUpdateResDate：" + lastUpdateResDate.toString());
        } catch (ParseException e) {
            LogI("ParseException：");
            e.printStackTrace();
        }

        if (!isSameDay(curDate, lastUpdateResDate)) {
            LogI("!isSameDay return true");
            lastUpdateResDate = curDate;
            return true;
        } else if ((lastUpdateResDate.before(afternoonUpdateDate)) && (curDate.after(afternoonUpdateDate))) {//afternoon didn,t update
            LogI("isSameDay return true");
            lastUpdateResDate = curDate;
            return true;
        }

        LogI(" return false");
        return false;
    }

    public class SystemSettingsInfoProvider {
        private SystemSettingsInfoProvider systemSettingsInfoProvider = null;
        private SharedPreferences sp = null;
        private SharedPreferences.Editor editor = null;
        private final String SHARED_PREFERENCES_NAME = "onlineCache";

        private SystemSettingsInfoProvider(Context context) {
            if (context != null) {
                sp = context.getApplicationContext().getSharedPreferences(SHARED_PREFERENCES_NAME, Activity.MODE_PRIVATE);
                editor = sp.edit();
            }
        }

        public String getLastUpdateTime(String fileName) {
            return sp.getString(fileName, "");
        }

        public void setLastUpdateTime(String fileName, String updateTime) {
            editor.putString(fileName, updateTime);
            editor.commit();
        }
    }
}
