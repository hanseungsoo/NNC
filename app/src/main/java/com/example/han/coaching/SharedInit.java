package com.example.han.coaching;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Calendar;

/**
 * Created by han on 2015-11-24.
 */
public class SharedInit {

    SharedPreferences SharedTrue,SharedTime;

    public SharedInit(Context context) {
        super();
        SharedTrue = context.getSharedPreferences("SharedTrue", context.MODE_PRIVATE);
        SharedTime = context.getSharedPreferences("SharedTime", context.MODE_PRIVATE);
    }
    public void setSharedTrue(String index,boolean FB){
        SharedPreferences.Editor editor = SharedTrue.edit();
        editor.putBoolean(index, FB);
        editor.commit();
    }
    public void Init(){
        setSharedTrue("isCreate", true);
        setSharedTrue("0", true);
        setSharedTrue("1", true);
        setSharedTrue("2", true);
        setSharedTrue("3", true);
        setSharedTrue("4", true);
        setSharedTrue("5", true);
        setSharedTrue("6", true);

        setSharedTime("0", 6, 30);
        setSharedTime("1", 9, 30);
        setSharedTime("2", 11, 30);
        setSharedTime("3", 15, 30);
        setSharedTime("4",18, 30);
        setSharedTime("5", 20, 36);
        setSharedTime("6", 12, 30);
    }
    public void setSharedTime(String index,int hour ,int minute){
        // current Time
        long atime = System.currentTimeMillis();

        Calendar curTime = Calendar.getInstance();
        curTime.set(Calendar.HOUR_OF_DAY, hour);
        curTime.set(Calendar.MINUTE, minute);
        curTime.set(Calendar.SECOND, 0);
        curTime.set(Calendar.MILLISECOND, 0);
        long btime = curTime.getTimeInMillis();
        long triggerTime = btime;
        if (atime > btime)
            triggerTime += 1000 * 60 * 60 * 24;


        SharedPreferences.Editor TimeEditor = SharedTime.edit();
        TimeEditor.putLong(index, triggerTime);
        TimeEditor.commit();
    }
    public void setSharedTimeLong(String index,long timeLong){
        // current Time
        long atime = System.currentTimeMillis();
        long triggerTime = timeLong;
        if (atime > timeLong)
            triggerTime += 1000 * 60 * 60 * 24;

        SharedPreferences.Editor TimeEditor = SharedTime.edit();
        TimeEditor.putLong(index, triggerTime);
        TimeEditor.commit();
    }
    public Boolean getSharedTrue(String index){
        return SharedTrue.getBoolean(index,false);
    }
    public Long getSharedTime(String index){
        return SharedTime.getLong(index,0);
    }

}

