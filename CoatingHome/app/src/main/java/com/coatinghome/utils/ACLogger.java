package com.coatinghome.utils;

import android.util.Log;

/**
 * Created by wuyajun on 15-10-02.
 */
public class ACLogger {

    //log 是否打开
    private final static boolean logFlag = true;
    //log 前可增加app名称 - 此时暂不增加
    public final static String tags = "";//[AppName]
    private final static int logLevel = Log.VERBOSE;

    private static ACLogger klog;

    /**
     * @return
     */
    public static ACLogger kLog() {
        if (klog == null) {
            klog = new ACLogger();
        }
        return klog;
    }

    /**
     * Get The Current Function Name
     *
     * @return
     */
    private String getFunctionName() {
        StackTraceElement[] sts = Thread.currentThread().getStackTrace();
        if (sts == null) {
            return null;
        }
        for (StackTraceElement st : sts) {
            if (st.isNativeMethod()) {
                continue;
            }
            if (st.getClassName().equals(Thread.class.getName())) {
                continue;
            }
            if (st.getClassName().equals(this.getClass().getName())) {
                continue;
            }
            return "[ " + Thread.currentThread().getName() + ": "
                    + st.getFileName() + ":" + st.getLineNumber() + " "
                    + st.getMethodName() + " ]";
        }
        return null;
    }

    /**
     * The Log Level:i
     *
     * @param str
     */
    public void i(String tag, Object str) {
        if (logFlag) {
            if (logLevel <= Log.INFO) {
                String name = getFunctionName();
                if (name != null) {
                    Log.i(tag, tags + " " + name + " - " + str);
                } else {
                    Log.i(tag, tags + " " + str.toString());
                }
            }
        }

    }

    /**
     * The Log Level:d
     *
     * @param str
     */
    public void d(String tag, Object str) {
        if (logFlag) {
            if (logLevel <= Log.DEBUG) {
                String name = getFunctionName();
                if (name != null) {
                    Log.d(tag, tags + " " + name + " - " + str);
                } else {
                    Log.d(tag, tags + " " + str.toString());
                }
            }
        }
    }

    /**
     * The Log Level:V
     *
     * @param str
     */
    public void v(String tag, Object str) {
        if (logFlag) {
            if (logLevel <= Log.VERBOSE) {
                String name = getFunctionName();
                if (name != null) {
                    Log.v(tag, tags + " " + name + " - " + str);
                } else {
                    Log.v(tag, tags + " " + str.toString());
                }
            }
        }
    }

    /**
     * The Log Level:w
     *
     * @param str
     */
    public void w(String tag, Object str) {
        if (logFlag) {
            if (logLevel <= Log.WARN) {
                String name = getFunctionName();
                if (name != null) {
                    Log.w(tag, tags + " " + name + " - " + str);
                } else {
                    Log.w(tag, tags + " " + str.toString());
                }
            }
        }
    }

    /**
     * The Log Level:e
     *
     * @param str
     */
    public void e(String tag, Object str) {
        if (logFlag) {
            if (logLevel <= Log.ERROR) {
                String name = getFunctionName();
                if (name != null) {
                    Log.e(tag, tags + " " + name + " - " + str);
                } else {
                    Log.e(tag, tags + " " + str.toString());
                }
            }
        }
    }

    /**
     * The Log Level:e
     *
     * @param ex
     */
    public void e(String tag, Exception ex) {
        if (logFlag) {
            if (logLevel <= Log.ERROR) {
                Log.e(tag, tags + " " + "error", ex);
            }
        }
    }

    /**
     * The Log Level:e
     *
     * @param log
     * @param tr
     */
    public void e(String tag, String log, Throwable tr) {
        if (logFlag) {
            String line = getFunctionName();
            Log.e(tag, tags + " " + "{Thread:" + Thread.currentThread().getName() + "}"
                    + "[" + line + ":] " + log + "\n", tr);
        }
    }
}