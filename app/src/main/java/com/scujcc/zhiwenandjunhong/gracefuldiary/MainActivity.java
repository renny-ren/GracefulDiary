package com.scujcc.zhiwenandjunhong.gracefuldiary;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;

import com.scujcc.zhiwenandjunhong.gracefuldiary.db.DiaryDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cc.trity.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {
    @Bind(R.id.common_iv_back)
    ImageView mCommonIvBack;
    @Bind(R.id.common_tv_title)
    TextView mCommonTvTitle;
    @Bind(R.id.common_iv_test)
    ImageView mCommonIvTest;
    @Bind(R.id.common_title_ll)
    LinearLayout mCommonTitleLl;
    @Bind(R.id.main_iv_circle)
    ImageView mMainIvCircle;
    @Bind(R.id.main_tv_date)
    TextView mMainTvDate;
    @Bind(R.id.main_tv_content)
    TextView mMainTvContent;
    @Bind(R.id.item_ll_control)
    LinearLayout mItemLlControl;

    @Bind(R.id.main_rv_show_diary)
    RecyclerView mMainRvShowDiary;
    @Bind(R.id.main_fab_enter_edit)
    android.support.design.widget.FloatingActionButton mMainFabEnterEdit;
    @Bind(R.id.main_rl_main)
    RelativeLayout mMainRlMain;
    @Bind(R.id.item_first)
    LinearLayout mItemFirst;
    @Bind(R.id.main_ll_main)
    LinearLayout mMainLlMain;

    private List<DiaryBean> mDiaryBeanList;
    private DiaryDatabaseHelper mHelper;
    private static TextView mText;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppManager.getAppManager().addActivity(this);
        ButterKnife.bind(this);

        StatusBar.compat(this, Color.parseColor("#161414"));

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        EventBus.getDefault().register(this);

        mHelper = new DiaryDatabaseHelper(this, "Diary.db", null, 1);
        mMainRvShowDiary.setLayoutManager(new LinearLayoutManager(this));
        mMainRvShowDiary.setAdapter(new DiaryAdapter(this, mDiaryBeanList));
        mText = new TextView(this);
        mText.setText("hello, world!");

        getDiaryBeanList();
        initTitle();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AppManager.getAppManager().AppExit(this);
    }

    @OnClick(R.id.main_fab_enter_edit)
    public void onClick() {
        AddDiaryActivity.startActivity(this);
    }

    private void initTitle() {
        mMainTvDate.setText("今天是" + GetDate.getDate());
        mCommonTvTitle.setText("日记");
        mCommonIvBack.setVisibility(View.INVISIBLE);
        mCommonIvTest.setVisibility(View.INVISIBLE);
    }

    private List<DiaryBean> getDiaryBeanList() {
        mDiaryBeanList = new ArrayList<>();
        List<DiaryBean> diaryList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = mHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query("diary", null, null, null, null,null, null);

        if (cursor.moveToFirst()) {
            do {
                String current_date = cursor.getString(cursor.getColumnIndex("date"));
                String date = GetDate.getDate().toString();
                if (current_date.equals(date)) {
                    mMainLlMain.removeView(mItemFirst);
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

    @Subscribe
    public void startUpdateDiaryActivity(UpdateDiary event) {
        String title = mDiaryBeanList.get(event.getPosition()).getTitle();
        String content = mDiaryBeanList.get(event.getPosition()).getContent();
        String tag = mDiaryBeanList.get(event.getPosition()).getTag();
        UpdateDiaryActivity.startActivity(this, title, content, tag);
    }

}
