package com.debin.cbc.hook;

import android.content.Context;
import android.util.Log;
import com.debin.cbc.hook.util.LogUtil;
import com.debin.cbc.hook.util.XPrefUtils;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class CBCXposedHook implements IXposedHookLoadPackage {
    private static String TAG = CBCXposedHook.class.getSimpleName();

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpParam) throws Throwable {
        if (!XPrefUtils.ignoreCBCWarnEnable()) {
            return;
        }

        if ("com.chinamworld.main".equals(lpParam.packageName)) {
            getClassLoader(lpParam);
        }
    }

    private void getClassLoader(XC_LoadPackage.LoadPackageParam lpParam) {
        try {
            String className = "com.secneo.apkwrapper.ApplicationWrapper";
            Class clazz = lpParam.classLoader.loadClass(className);
            if (clazz != null) {
                XposedHelpers.findAndHookMethod(clazz,
                        "attachBaseContext", Context.class, new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                super.afterHookedMethod(param);
                                Context context = (Context) param.args[0];
                                ClassLoader classLoader = context.getClassLoader();
                                hookMainActivity(classLoader);
                            }
                        });
            } else {
                LogUtil.e(TAG, "not found class : " + className);
            }
        } catch (Exception e) {
            LogUtil.e(TAG, "getClassLoader error: " + e.getLocalizedMessage());
        }
    }

    private void hookMainActivity(ClassLoader classLoader) {
        try {
            String className = "com.ccb.start.MainActivity";
            Class loadClass = classLoader.loadClass(className);
            if (loadClass != null) {
                XposedHelpers.findAndHookMethod(loadClass, "checkRoot", Context.class, new XC_MethodReplacement() {
                    @Override
                    protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                        return null;
                    }
                });
            } else {
                LogUtil.e(TAG, "not found class : " + className);
            }
        } catch (Throwable e) {
            LogUtil.e(TAG, "hookMainActivity err:" + Log.getStackTraceString(e));
        }
    }
}
