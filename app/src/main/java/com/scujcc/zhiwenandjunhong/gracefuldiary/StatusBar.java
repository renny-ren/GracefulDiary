package com.scujcc.zhiwenandjunhong.gracefuldiary;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by junhongren on 16/5/2018.
 */

public class StatusBar {
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void compat(Activity activity, int statusColor)
    {
        if (statusColor != -1)
        {
            activity.getWindow().setStatusBarColor(statusColor);
            return;
        }

        int color = Color.parseColor("#20000000");
        ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
        if (statusColor != -1)
        {
            color = statusColor;
        }
        View statusBarView = contentView.getChildAt(0);

        if (statusBarView != null && statusBarView.getMeasuredHeight() == getStatusBarHeight(activity))   // 避免改变颜色时重复添加 statusBarView
        {
            statusBarView.setBackgroundColor(color);
            return;
        }
        statusBarView = new View(activity);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                getStatusBarHeight(activity));
        statusBarView.setBackgroundColor(color);
        contentView.addView(statusBarView, lp);

    }

    public static int getStatusBarHeight(Context context)
    {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
        {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
