package com.example.han.coaching;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by han on 2015-11-24.
 */
public class GpsService extends Service {
    private LocationManager mLocationManager = null;
    private LocationListener mLocationListener = null;


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        unregisterRestartAlarm();
        mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = new MyLocationListener();
        try{
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,mLocationListener);


        }catch (Exception e){
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void unregisterRestartAlarm() {

        Log.d("FoodService", "unregisterRestartAlarm()");
        Intent intent = new Intent(GpsService.this, RestartService.class);
        intent.setAction("ACTION.RESTART.FoodGps");
        PendingIntent sender = PendingIntent.getBroadcast(GpsService.this, 0, intent, 0);

        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.cancel(sender);

    }
    public void registerRestartAlarm() {
        Log.d("FoodService", "registerRestartAlarm");
        Intent intent = new Intent(GpsService.this, RestartService.class);
        intent.setAction("ACTION.RESTART.FoodGps");
        PendingIntent sender = PendingIntent.getBroadcast(GpsService.this, 0, intent, 0);
        long firstTime = SystemClock.elapsedRealtime();
        firstTime += 10*1000;                                               // 10초 후에 알람이벤트 발생
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstTime, 10 * 1000, sender);
    }

    @Override
    public void onDestroy() {
        registerRestartAlarm();
        try{
            mLocationManager.removeUpdates(mLocationListener);
        }catch (Exception e){
        }
        super.onDestroy();
    }



    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
        }
        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(getApplicationContext(), "위치정보 사용불가능", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderEnabled(String provider) {
            Toast.makeText(getApplicationContext(),"위치정보 사용가능",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            String msg = "";

            switch (status) {

                case LocationProvider.OUT_OF_SERVICE:

                    msg = "서비스 지역이 아닙니다.";

                    break;

                case LocationProvider.TEMPORARILY_UNAVAILABLE:

                    msg = "일시적으로 위치 정보를 사용할 수 없습니다.";

                    break;

                case LocationProvider.AVAILABLE:

                    msg = "서비스 가능 지역입니다.";

                    break;

            }
            Toast.makeText(getApplicationContext(), "" + msg, Toast.LENGTH_SHORT).show();
        }
    }

}

