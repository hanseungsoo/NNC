package com.example.han.coaching;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by han on 2015-11-24.
 */
public class GetFood extends BroadcastReceiver {

    Context context;
    Runnable mRunnable;
    Handler mHandler;

    public void getItem(Intent intent,String index,String what,int id){

        String query ="";
        String today_S,today_L,nowDate,weather;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        nowDate = sdf.format(date);
        LunarCalendar lunar = new LunarCalendar();
        today_S = nowDate.substring(4, 8);
        today_L = lunar.toLunar(nowDate).toString().substring(4, 8);
        weather = staticMerge.temp;
        try{
            query = URLEncoder.encode(what, "utf-8");
        }catch (Exception e){}
        Log.i("aaaa", "main onCreate : foodDbJson");
        String param = "tbName=noon_food&col=food_name&food_wea="+weather+"&food_time="+query;
        String param2 = "tbName=anniv&col=food_name&SoL=S&date="+today_S;
        String param3 =  "tbName=anniv&col=food_name&SoL=L&date="+today_L;

        foodDbJson fD = new foodDbJson();
        fD.execute(param,param2,param3);

        try {
            Log.i("aaaa", "-----------------------------" + staticMerge.temp);


        } catch (Exception e) {

            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsStrting = sw.toString();
            Log.e("aaaa", exceptionAsStrting);
        }
        registerAlarm rA = new registerAlarm(context);
        rA.registerAM(intent.getAction(),index,id);
    }
    public void getItem(String what){

        String query ="";
        String today_S,today_L,nowDate,weather;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        nowDate = sdf.format(date);
        LunarCalendar lunar = new LunarCalendar();
        today_S = nowDate.substring(4, 8);
        today_L = lunar.toLunar(nowDate).toString().substring(4, 8);
        weather = staticMerge.temp;
        try{
            query = URLEncoder.encode(what, "utf-8");
        }catch (Exception e){}
        Log.i("aaaa", "main onCreate : foodDbJson");
        String param = "tbName=noon_food&col=food_name&food_wea="+weather+"&food_time="+query;
        String param2 = "tbName=anniv&col=food_name&SoL=S&date="+today_S;
        String param3 =  "tbName=anniv&col=food_name&SoL=L&date="+today_L;

        foodDbJson fD = new foodDbJson();
        fD.execute(param,param2,param3);

        try {
            Log.i("aaaa", "-----------------------------" + staticMerge.temp);


        } catch (Exception e) {

            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsStrting = sw.toString();
            Log.e("aaaa", exceptionAsStrting);
        }
    }
    @Override
    public void onReceive(final Context context, Intent intent) {
        this.context = context;

        if(intent.getAction().equals("ACTION.GET.ONE")){
            Log.i("aaaa","OOOOOOOOOOOOOOOOOOOOONNNNNNNNNNNNNNNNEEEEEEEE");
            staticMerge.what = "아침";
            getItem(intent,"0",staticMerge.what,1);
        }
        if(intent.getAction().equals("ACTION.GET.TWO")){
            staticMerge.what = "아점";
            getItem(intent,"1",staticMerge.what,2);
        }
        if(intent.getAction().equals("ACTION.GET.THREE")){
            staticMerge.what = "점심";
            getItem(intent,"2",staticMerge.what,3);

        }
        if(intent.getAction().equals("ACTION.GET.FOUR")){
            staticMerge.what = "점저";
            getItem(intent,"3",staticMerge.what,4);
        }
        if(intent.getAction().equals("ACTION.GET.FIVE")){
            staticMerge.what = "저녁";
            getItem(intent,"4",staticMerge.what,5);

        }
        if(intent.getAction().equals("ACTION.GET.SIX")){
            staticMerge.what="야식";
            getItem(intent,"5",staticMerge.what,6);
        }
        if(intent.getAction().equals("ACTION.GET.SEVEN")){
            staticMerge.what = "후식";
            getItem(intent,"6",staticMerge.what,7);

        }
        if(intent.getAction().equals("ACTION.GET.NORMAL")){
            staticMerge.what = "수동";
            getItem(staticMerge.what);
        }
        if(intent.getAction().substring(0, 10).equals("ACTION.GET"))
        {
            noonWidget.contentValue="content1";
            MainActivity.mmmm();
            Log.i("widget","receive:action.get ---> widget update");

        }


    }
}
