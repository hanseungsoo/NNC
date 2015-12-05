package com.example.han.coaching;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by han on 2015-11-24.
 */
public class settingBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("ACTION.SET.PLACE")){
            // 30분
            Log.i("ra place","action call.");
            DBHandler dbHandler = DBHandler.open(context);
            dbHandler.abode_insert();
            dbHandler.close();

        }
        if(intent.getAction().equals("ACTION.SET.PATTERN")){
            // 1일
            Log.i("ra pattern","action call.");
            DBHandler dbHandler = DBHandler.open(context);
            dbHandler.food_pattern_insert();
            dbHandler.abode_clean();
            dbHandler.close();

        }
        if(intent.getAction().equals("ACTION.SET.ONEWEEK")){
            // 1주
            Log.i("ra oneweek","action call");
            DBHandler dbHandler = DBHandler.open(context);
            dbHandler.food_pattern_clean2();
            dbHandler.close();
        }

        if(intent.getAction().equals("ACTION.SET.NEWS")) {
            GetNewsData GND = new GetNewsData();
            GND.execute();
        }
    }
}
