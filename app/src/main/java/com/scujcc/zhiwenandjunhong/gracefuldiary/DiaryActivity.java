package com.scujcc.zhiwenandjunhong.gracefuldiary;

import android.content.Intent;
import android.content.Context;

/**
 * Created by junhongren on 16/5/2018.
 */

public class DiaryActivity {
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, DiaryActivity.class);
        context.startActivity(intent);
    }
}
