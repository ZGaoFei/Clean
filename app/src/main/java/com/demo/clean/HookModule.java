package com.demo.clean;

import android.telephony.TelephonyManager;
import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookModule implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
//        if (!lpparam.packageName.equals("com.demo.clean.HookModule")) {
//            return;
//        }
//
//        XposedHelpers.findAndHookMethod(TelephonyManager.class, "getDeviceId", new XC_MethodHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                super.beforeHookedMethod(param);
//
//                Log.e("test", "=====beforeHookedMethod=====");
//            }
//
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                super.afterHookedMethod(param);
//
//                param.setResult("hook success");
//
//                Log.e("test", "=====afterHookedMethod=====");
//            }
//        });


//        String className = "com.demo.clean.MainActivity";
//        Class<?> aClass = lpparam.classLoader.loadClass(className);
//        XposedHelpers.findAndHookMethod(aClass, "testGetIMEI", new XC_MethodHook() {
//
//            protected void afterHookedMethod(MethodHookParam methodhookparam) throws Throwable {
//                super.afterHookedMethod(methodhookparam);
//
//                methodhookparam.args[0] = "1234567890123";
//            }
//
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                super.beforeHookedMethod(param);
//
//                param.setResult("9876543210");
//            }
//        });

        String className = "com.demo.clean.MainActivity";
        Class<?> aClass = lpparam.classLoader.loadClass(className);
        XposedHelpers.findAndHookMethod(aClass, "testGetIMEI", new XC_MethodReplacement() {
            @Override
            protected Object replaceHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                return "1234567890hahaha";
            }
        });

//        XposedHelpers.findAndHookMethod(aClass, "testGetIMEI", new XC_MethodHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                super.beforeHookedMethod(param);
//
//                param.setResult("before 111111");
//            }
//
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                super.afterHookedMethod(param);
//
//                param.setResult("after 222222");
//            }
//        });
    }
}
