package com.scujcc.zhiwenandjunhong.gracefuldiary;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by junhongren on 26/5/2018.
 */

public class DiaryApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if(LeakCanary.isInAnalyzerProcess(this)){
            return;
        }
        LeakCanary.install(this);
    }
}
