package com.scujcc.zhiwenandjunhong.gracefuldiary;

/**
 * Created by junhongren on 12/5/2018.
 */

public class DiaryBean {
    private String title;
    private String content;
    private String mood;
    private String weather;
    private String date;
    private String tag;

    public DiaryBean(String title, String content, String mood, String weather, String date, String tag) {
        this.title = title;
        this.content = content;
        this.mood = mood;
        this.weather = weather;
        this.date = date;
        this.tag = tag;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent() { this.content = content; }

    public String getMood() { return mood; }
    public void setMood() { this.tag = mood; }

    public String getWeather() { return weather; }
    public void setWeather() { this.tag = weather; }

    public String getDate() { return date; }
    public void setDate() { this.date = date; }

    public String getTag() { return tag; }
    public void setTag() { this.tag = tag; }


}
