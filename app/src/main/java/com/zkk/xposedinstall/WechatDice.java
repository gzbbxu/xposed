package com.zkk.xposedinstall;

import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * @author zkk
 * 微信 hook
 */
public class WechatDice implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        try {
           /* XposedHelpers.findAndHookMethod("com.tencent.mm.sdk.platformtools.bh", loadPackageParam.classLoader,
                    "eE", int.class, int.class, new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            Log.i("zhouke", "wechat >> beforeHookedMethod call");
                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            Log.i("zhouke", "wechat >> afterHookedMethod call >> " +param.args[0]  + ">> " +param.args[1] + ">> " +param.getResult() );
                        }
                    });*/


            if (loadPackageParam.packageName.equals("com.tencent.mm")) {
                Log.e("zhouke", "com.tencent.mm1s");
                // hook random构造函数 Random(long s)
         /*       XposedHelpers.findAndHookConstructor("java.util.Random", loadPackageParam.classLoader, long.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        Log.e("zhouke","Random(long)");
                        Log.e("zhouke", Log.getStackTraceString(new Throwable()));
                        super.afterHookedMethod(param);
                    }
                });
                // hook Random()
                XposedHelpers.findAndHookConstructor("java.util.Random", loadPackageParam.classLoader, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        Log.e("zhouke","Random()");
                        Log.e("zhouke", Log.getStackTraceString(new Throwable()));
                        super.afterHookedMethod(param);
                    }
                });
            }*/
                //wechat 7.0.4 oppo R7t android 4.4.4
                XposedHelpers.findAndHookMethod("com.tencent.mm.sdk.platformtools.bo", loadPackageParam.classLoader, "gV", int.class, int.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        if ((int) param.args[0] == 2) {
                            //剪刀石头布
                            param.setResult(1); //永远锤子
                        } else if ((int) param.args[0] == 5) {
                            //骰子
                            param.setResult(5); //永远是6
                        }
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        // param.args[0] == 2 表示剪刀石头布  result 等于2 布  等于0 剪刀 等于 1 锤子
                        // param.args[0] == 5 表示骰子  result 等于 几 骰子就是几
                        Log.e("zhouke", "args: " + param.args[0] + " Result ----" + param.getResult());
                        super.afterHookedMethod(param);
                    }
                });
            }

        } catch (Exception e) {

        }

    }
}
