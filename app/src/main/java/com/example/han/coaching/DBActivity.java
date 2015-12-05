package com.example.han.coaching;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by han on 2015-11-24.
 */
public class DBActivity extends Activity {
    DBHandler dbHandler;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);
        tv = (TextView)findViewById(R.id.dbtv);
    }

    @Override
    protected void onStart() {
        dbHandler = DBHandler.open(MainActivity.mContext);
        super.onStart();
    }
    @Override
    protected void onStop() {
        dbHandler.close();
        super.onStop();
    }

    public void foodPatternViewClicked(View v) {
        //음식패턴 보기
        Cursor cursor = dbHandler.select_food_pattern();
        String str = "";
        String str1 = "패턴1:";
        for(int j =0; j<cursor.getCount(); j++) {
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                str += str1 + String.valueOf(cursor.getInt(i));
                str1=", ";
            }
            str1="패턴"+(j+2)+":";
            cursor.moveToNext();
            str += "\n";
        }
        tv.setText(str);
    }

    public void abodeCleanCLicked(View v) {
        //음식패턴 정리
        dbHandler.abode_clean();
    }

    public void abodeViewClicked(View v) {
        //거주지 보기
        Cursor cursor = dbHandler.select_abode();
        String str = "";
        String str1 = "거주지1:";
        for(int j =0; j<cursor.getCount(); j++) {
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                str += str1 + cursor.getString(i);
                str1=", ";
            }
            str1="거주지"+(j+2)+":";
            cursor.moveToNext();
            str += "\n";
        }
        tv.setText(str);
    }

    public void foodFavoriteViewClicked(View v) {
        Cursor cursor = dbHandler.food_favorite_select();
        String str = "";
        String str1 = "선호음식:";
        for(int j =0; j<cursor.getCount(); j++) {
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                str += str1 + cursor.getString(i);
                str1=", ";
            }
            str1="선호음식"+(j+2)+":";
            cursor.moveToNext();
            str += "\n";
        }
        tv.setText(str);
    }

    public void TimeChecked(View v) {
        String timestr = "";
        SharedInit si = new SharedInit(MainActivity.mContext);
        Long tt = si.getSharedTime("0");
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        String str = df.format(tt);
        String[] click_str = str.split(":");
        timestr += "아침 - " + click_str[0] +":" + click_str[1] + "\n";
        tt = si.getSharedTime("1");
        df = new SimpleDateFormat("HH:mm:ss");
        str = df.format(tt);
        click_str = str.split(":");
        timestr += "아점 - " + click_str[0] +":" + click_str[1] + "\n";
        tt = si.getSharedTime("2");
        df = new SimpleDateFormat("HH:mm:ss");
        str = df.format(tt);
        click_str = str.split(":");
        timestr += "점심 - " + click_str[0] +":" + click_str[1] + "\n";
        tt = si.getSharedTime("3");
        df = new SimpleDateFormat("HH:mm:ss");
        str = df.format(tt);
        click_str = str.split(":");
        timestr += "점저 - " + click_str[0] +":" + click_str[1] + "\n";
        tt = si.getSharedTime("4");
        df = new SimpleDateFormat("HH:mm:ss");
        str = df.format(tt);
        click_str = str.split(":");
        timestr += "저녁 - " + click_str[0] +":" + click_str[1] + "\n";
        tt = si.getSharedTime("5");
        df = new SimpleDateFormat("HH:mm:ss");
        str = df.format(tt);
        click_str = str.split(":");
        timestr += "야식 - " + click_str[0] +":" + click_str[1] + "\n";
        tt = si.getSharedTime("6");
        df = new SimpleDateFormat("HH:mm:ss");
        str = df.format(tt);
        click_str = str.split(":");
        timestr += "후식 - " + click_str[0] +":" + click_str[1];

        tv.setText(timestr);

    }
}
