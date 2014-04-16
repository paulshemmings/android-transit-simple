package com.razor.transit.helpers;

import android.util.Log;

public class LogHelper {

    private static boolean isLoggingOff = false;

    public static void turnLoggingOff(boolean isLoggingOff){
        LogHelper.isLoggingOff = isLoggingOff;
    }

    public static void w(String tag, String msg){
        if(!isLoggingOff) Log.w(tag, msg);
    }

    public static void e(String tag, String msg){
        if(!isLoggingOff) Log.e(tag, msg);
    }

    public static void d(String tag, String msg){
        if(!isLoggingOff) Log.d(tag, msg);
    }

    public static void v(String tag, String msg){
        if(!isLoggingOff) Log.v(tag, msg);
    }

    public static void i(String tag, String msg){
        if(!isLoggingOff) Log.i(tag, msg);
    }

    // even helpier...

    public static void w(String tag, String format, Object ... args){
        if(!isLoggingOff) Log.w(tag, String.format(format, args));
    }

    public static void e(String tag, String format, Object ... args){
        if(!isLoggingOff) Log.e(tag, String.format(format, args));
    }

    public static void d(String tag, String format, Object ... args){
        if(!isLoggingOff) Log.d(tag, String.format(format, args));
    }

    public static void v(String tag, String format, Object ... args){
        if(!isLoggingOff) Log.v(tag, String.format(format, args));
    }

    public static void i(String tag, String format, Object ... args){
        if(!isLoggingOff) Log.i(tag, String.format(format, args));
    }
}
