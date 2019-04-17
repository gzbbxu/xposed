package com.zkk.xposedinstall;

import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class XposedEntry implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        try {
            XposedHelpers.findAndHookMethod("android.content.res.Resources",loadPackageParam.classLoader,
                    "getColor",int.class,new my_getColor());
        }catch (Exception e){

        }

    }
}
class my_getColor extends XC_MethodHook{
    @Override
    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
        super.beforeHookedMethod(param);
        Log.i("zhouke","before hook method");
    }

    @Override
    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
        super.afterHookedMethod(param);
        Log.i("zhouke","afterHookedMethod");
        Object result = param.getResult();
        result = (int)result &~0x0000ff00| 0x00ff0000;
        param.setResult(result);
    }
}
