package com.debin.cbc.hook.util;


import com.debin.cbc.hook.BuildConfig;
import de.robv.android.xposed.XSharedPreferences;

public class XPrefUtils {

    private static XSharedPreferences instance = null;

    private static XSharedPreferences getInstance() {
        if (instance == null) {
            instance = new XSharedPreferences(BuildConfig.APPLICATION_ID);
            instance.makeWorldReadable();
        } else {
            instance.reload();
        }
        return instance;
    }

    private XPrefUtils() {

    }

    public static boolean ignoreCBCWarnEnable() {
        return getInstance().getBoolean(PrefUtil.IGNORE_CBC_WARN, true);
    }
}
