package com.debin.cbc.hook;

import android.app.Application;
import com.debin.cbc.hook.util.PrefUtil;

public class MyApplication extends Application {

    private static PrefUtil prefUtil;

    @Override
    public void onCreate() {
        super.onCreate();
        prefUtil = new PrefUtil(this);
    }

    public static PrefUtil getPrefUtil() {
        return prefUtil;
    }
}
