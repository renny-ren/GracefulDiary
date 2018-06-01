package com.scujcc.zhiwenandjunhong.gracefuldiary;

import android.app.ActivityManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import java.util.Stack;

/**
 * Created by junhongren on 14/5/2018.
 */

public class AppManager {
    private static Stack<AppCompatActivity> activityStack;
    private static AppManager instance;

    private AppManager() {}

    public static AppManager getAppManager() {
        if (instance == null) {
            synchronized (AppManager.class){
                if (instance == null) {
                    instance = new AppManager();
                }
            }
        }
        return instance;
    }

    public void addActivity(AppCompatActivity activity) {
        if(activityStack == null){
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    public void finishAllActivity(){
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    public void AppExit(Context context){
        try{
            finishAllActivity();
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
