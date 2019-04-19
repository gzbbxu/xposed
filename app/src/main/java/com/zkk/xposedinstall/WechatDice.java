package com.zkk.xposedinstall;

import android.content.ContentValues;
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


                XposedHelpers.findAndHookMethod("com.tencent.wcdb.database.SQLiteDatabase", loadPackageParam.classLoader,
                        "insertWithOnConflict", String.class, String.class, ContentValues.class, int.class, new XC_MethodHook() {
                            @Override
                            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                                Log.e("zhouke", ">> beforeHookedMethod insertWithOnConflict");
                                super.beforeHookedMethod(param);
                            }

                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                super.afterHookedMethod(param);
                                Log.e("zhouke", ">> afterHookedMethod insertWithOnConflict >> " + param.args.length);
                                for (Object obj : param.args) {
                                    Log.e("zhouke", "obj = " + obj);
                                }
                                String tabName = (String) param.args[0];
                                if (tabName.equals("message")) {
                                    Log.i("zhouke", "message tabname = " + tabName + ">>");
                                    ContentValues contentV = (ContentValues) param.args[2];
                                    String _content = (String) contentV.get("content");
                                    int _type = (int) contentV.get("type");
                                    int isSend = (int) contentV.get("isSend");


                                    int _paramInt = (int) param.args[3];
                                    if (_type == 1) {
                                        Log.i("zhouke", "_contnet : " + _content + ": type = " + _type + ":isSend : " + isSend + "普通文本 :" + (isSend==0?"接受":"发送"));
                                    } else if (_type == 34) {
                                        Log.i("zhouke", "_contnet : " + _content + ": type = " + _type + ":isSend : " + isSend + " 语音:" + (isSend==0?"接受":"发送"));
                                    }
                                    Log.i("zhouke", "paramInt : " + _paramInt);
                                } else {
                                    Log.i("zhouke", "其它 tabname = " + tabName + ">>");
                                }


                           /*     if (param.args != null) {
                                    String tabName = (String) param.args[0];
                                    Log.i("zhouke", "tabname = " + tabName + ">>");
                                    if (null != tabName && tabName.trim().equals("message")) {
                                        ContentValues contentValues = (ContentValues) param.args[2];
                                        String contnet = (String) contentValues.get("content");
                                        String type = (String) contentValues.get("type");
                                        int paramInt = (int) param.args[3];
                                        Log.e("zhouke", "contnet : " + contnet + ":type = " + type + "paramInt : = " + paramInt);
                                    }

                                }*/

                                   /*     04-19 16:49:14.783 18244-18647/? E/zhouke: >> message
                                        04-19 16:49:14.783 18244-18647/? E/zhouke: >> msgId
                                        04-19 16:49:14.784 18244-18647/? E/zhouke: >> lvbuffer=[B@43af81c0 bizClientMsgId= createTime=1555663738000 status=3 msgSeq=615870677 type=1 talkerId=33 content=aaa msgSvrId=7745313153899760405 flag=0 bizChatId=-1 msgId=48 isSend=0 talker=wxid_4iiag2f59pbj21
                                        04-19 16:49:14.784 18244-18647/? E/zhouke: >> 0*/


                            }
                        });
            }

        } catch (Exception e) {

        }

    }
}
