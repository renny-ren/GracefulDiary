package com.scujcc.zhiwenandjunhong.gracefuldiary;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;



import com.scujcc.zhiwenandjunhong.gracefuldiary.db.DiaryDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends AppCompatActivity {
    TextView mMainDate;
    TextView mDefaultTitle;
    ImageView mDefaultBack;
    ImageView mDefaultTest;
    RecyclerView mShowDiary;
    LinearLayout mMainLinearLayout;
    LinearLayout mFirstItem;


    private List<DiaryBean> mDiaryBeanList;
    private DiaryDatabaseHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppManager.getAppManager().addActivity(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        EventBus.getDefault().register(this);

        mHelper = new DiaryDatabaseHelper(this, "Diary.db", null, 1);
        mShowDiary.setLayoutManager(new LinearLayoutManager(this));
        mShowDiary.setAdapter(new DiaryAdapter(this, mDiaryBeanList));

        getDiaryBeanList();
        initTitle();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initTitle() {
        mMainDate.setText("今天是" + GetDate.getDate());
        mDefaultTitle.setText("日记");
        mDefaultBack.setVisibility(View.INVISIBLE);
        mDefaultTest.setVisibility(View.INVISIBLE);
    }

    private List<DiaryBean> getDiaryBeanList() {
        mDiaryBeanList = new ArrayList<>();
        List<DiaryBean> diaryList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = mHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query("query", null, null, null, null,null, null);

        if (cursor.moveToFirst()) {
            do {
                String current_date = cursor.getString(cursor.getColumnIndex("date"));
                String date = GetDate.getDate().toString();
                if (current_date.equals(date)) {
                    mMainLinearLayout.removeView(mFirstItem);
                    break;
                }
            } while (cursor.moveToNext());
        }

        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String mood = cursor.getString(cursor.getColumnIndex("mood"));
                String weather = cursor.getString(cursor.getColumnIndex("weather"));
                String date = cursor.getString(cursor.getColumnIndex("date"));
                String tag = cursor.getString(cursor.getColumnIndex("tag"));
                mDiaryBeanList.add(new DiaryBean(title, content, mood, weather, date, tag));
            } while (cursor.moveToNext());
        }
        cursor.close();

        for (int i = mDiaryBeanList.size() - 1; i >= 0; i--) {
            diaryList.add(mDiaryBeanList.get(i));
        }

        mDiaryBeanList = diaryList;
        return mDiaryBeanList;
    }
}
